/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import br.com.jgeniselli.catalogacaoWS.model.Ant;
import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.AntNestRepository;
import br.com.jgeniselli.catalogacaoWS.model.AntReportFilter;
import br.com.jgeniselli.catalogacaoWS.model.AntRepository;
import br.com.jgeniselli.catalogacaoWS.model.CoordinateRepository;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisit;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisitRepository;
import br.com.jgeniselli.catalogacaoWS.model.NestReportFilter;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.location.CityRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.CountryStateRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jgeniselli
 */
@Controller
public class SiteNestsController extends BaseSiteController {
    
    private static final String GOOGLE_MAPS_API_URL_MASK = "https://maps.googleapis.com/maps/api/js?key=%s&callback=initMap";
    
    @Value("${server.googleMapsAPIKey}")
    private String googleMapsAPIKey;
    
    @Value("${server.imagesPath}")
    private String imagePath;
    
    @Value("${server.compiledReportPath}")
    private String compiledReportPath;
    
    @Autowired
    private ApplicationContext appContext;
    
    @Autowired
    AntNestRepository antNestRepository;
    
    @Autowired
    ServletContext servletContext; 
    
    @Autowired
    CountryStateRepository countryStateRepository;
    
    @Autowired
    CityRepository cityRepository;
    
    @Autowired
    DataUpdateVisitRepository dataUpdateVisitRepository; 
    
    @Autowired
    AntRepository antRepository; 
    
    @Autowired
    CoordinateRepository coordinateRepository; 
    
    @Autowired
    DataSource dataSource; 
    
    @RequestMapping("/home.html")
    public String home(Model model) {
        ArrayList<AntNest> nests = (ArrayList<AntNest>) antNestRepository
                .findAll();
        model.addAttribute("nests", nests);
        return "home";
    }
    
    @RequestMapping("blank")
    public String teste() {
        return "header";
    }
    
    @RequestMapping("/nestDetails.html")
    public String nestDetails(@RequestParam(name = "nestId") Long nestId, Model model) {

        AntNest nest = antNestRepository.findOne(nestId);
        if (nest != null) {
            model.addAttribute("nest", nest);
        }
        model.addAttribute(
                "maps_api_url", 
                String.format(GOOGLE_MAPS_API_URL_MASK, googleMapsAPIKey)
        );
        
        return "details";
    }
    
    @RequestMapping("/nestReports.html")
    public String nestReports(Model model) {
        model.addAttribute("filter", new NestReportFilter());
        model.addAttribute("formFragment", "form-nest-filter");
        model.addAttribute("destinationPath", "/web/generateNestReport");
        model.addAttribute("title", "Gerar Relatório de Ninhos");

        return "reportForm";
    }

    @RequestMapping(path = "/generateNestReport", method = RequestMethod.POST)
    public void generateNestReport(@ModelAttribute("filter") @Valid NestReportFilter filter, BindingResult bindingResult, HttpServletResponse response) {
        
        String query = filter.getQuery();
        
        InputStream reportInputStream
            = getClass().getResourceAsStream("/reports/nestReport.jrxml");
        try {
            JasperReport jasperReport
                    = JasperCompileManager.compileReport(reportInputStream);
            
            JRSaver.saveObject(jasperReport, compiledReportPath + "/nestReport.jasper");
            Connection connection = dataSource.getConnection();
            
            HashMap params = new HashMap();
            params.put("IMAGE_PATH", imagePath);
            params.put("query", query);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, params, connection);
            
            User user = getSessionUser();

            File pdf = File.createTempFile("output." + user.getUsername(), ".pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
           
            response.setContentType("application/pdf");
            InputStream in = new FileInputStream(pdf);
            IOUtils.copy(in, response.getOutputStream());

        } catch (JRException | SQLException | IOException | UserNotLoggedException ex) {
            Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @RequestMapping("/antReports.html")
    public String antReports(Model model) {
        model.addAttribute("filter", new AntReportFilter());
        model.addAttribute("formFragment", "form-ant-filter");
        model.addAttribute("destinationPath", "/web/generateAntReport");
        model.addAttribute("title", "Gerar Relatório de Formigas");

        return "reportForm";
    }
    
    @RequestMapping(path = "/generateAntReport", method = RequestMethod.POST)
    public void generateAntReport(@ModelAttribute("filter") @Valid AntReportFilter filter, BindingResult bindingResult, HttpServletResponse response) {
        
        String query = filter.getQuery();
        
        InputStream reportInputStream
            = getClass().getResourceAsStream("/reports/antReport.jrxml");
        try {
            JasperReport jasperReport
                    = JasperCompileManager.compileReport(reportInputStream);
            
            JRSaver.saveObject(jasperReport, compiledReportPath + "/antReport.jasper");
            Connection connection = dataSource.getConnection();
            
            HashMap params = new HashMap();
            params.put("IMAGE_PATH", imagePath);
            params.put("query", query);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, params, connection);
            
            User user = getSessionUser();

            File pdf = File.createTempFile("output." + user.getUsername(), ".pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
           
            response.setContentType("application/pdf");
            InputStream in = new FileInputStream(pdf);
            IOUtils.copy(in, response.getOutputStream());

        } catch (JRException | SQLException | IOException | UserNotLoggedException ex) {
            Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @RequestMapping("/editDataUpdate")
    public String editDataUpdate(@RequestParam(name = "dataUpdateId") Long dataUpdateId, Model model) {
        
        DataUpdateVisit visit = dataUpdateVisitRepository.findOne(dataUpdateId);
        User user;

        String errorMessage = null;
        try {
            user = super.getSessionUser();
            if (!visit.getCollector().getId().equals(user.getId())) {
                throw new EditionNotAllowedException();
            }
        } catch (UserNotLoggedException | EditionNotAllowedException ex) {
            errorMessage = ex.getMessage();
        } finally {
            model.addAttribute("destinationPath", "/web/saveDataUpdate/" + visit.getNest().getNestId());
            model.addAttribute("title", "Edição de Atualização de Dados");
            if (errorMessage == null) {
                model.addAttribute("formFragment", "form-data-update");
                model.addAttribute("dataUpdate", visit);
            } else {
                model.addAttribute("formFragment", "form-empty");
                model.addAttribute("errorMessage", errorMessage);
            }
        }
        return "reportForm";
    }
    
    @RequestMapping("/saveDataUpdate/{nest}")
    public String saveDataUpdateEdition(
            @PathVariable(name = "nest") Long nest, 
            @ModelAttribute @Valid DataUpdateVisit dataUpdate, 
            BindingResult bindingResult, 
            Model model
    ) {
        
        try {
            
            DataUpdateVisit realVisit = dataUpdateVisitRepository.findOne(dataUpdate.getId());
            
            realVisit.setNotes(dataUpdate.getNotes());
            realVisit.getNewBeginingPoint().setLatitude(dataUpdate.getNewBeginingPoint().getLatitude());
            realVisit.getNewBeginingPoint().setLongitude(dataUpdate.getNewBeginingPoint().getLongitude());
            
            realVisit.getNewEndingPoint().setLatitude(dataUpdate.getNewEndingPoint().getLatitude());
            realVisit.getNewEndingPoint().setLongitude(dataUpdate.getNewEndingPoint().getLongitude());

            dataUpdateVisitRepository.save(realVisit);
            coordinateRepository.save(realVisit.getNewBeginingPoint());
            coordinateRepository.save(realVisit.getNewEndingPoint());
            
            model.addAttribute("message", "Atualização salva com sucesso");
            
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Ocorreu um erro ao salvar a atualização");
            Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nestDetails(nest, model);
    }
    
    @RequestMapping("/editAnt")
    public String editAnt(@RequestParam(name = "antId") Long antId, Model model) {

        Ant ant = antRepository.findOne(antId);
        User user;

        String errorMessage = null;
        try {
            user = super.getSessionUser();
            if (!ant.getVisit().getCollector().getId().equals(user.getId())) {
                throw new EditionNotAllowedException();
            }
        } catch (UserNotLoggedException | EditionNotAllowedException ex) {
            errorMessage = ex.getMessage();
        } finally {
            model.addAttribute("destinationPath", "/web/saveAnt");
            model.addAttribute("title", "Edição de Amostra de Formiga");
            if (errorMessage == null) {
                model.addAttribute("formFragment", "form-ant");
                model.addAttribute("ant", ant);
            } else {
                model.addAttribute("formFragment", "form-empty");
                model.addAttribute("errorMessage", errorMessage);
            }
        }
        return "reportForm";
    }
    
    @RequestMapping(path="/saveAnt", method=RequestMethod.POST)
    public String saveAnt(@ModelAttribute @Valid Ant ant, BindingResult bindingResult, Model model) {
        try {
            Ant realAnt = antRepository.findOne(ant.getId());
            
            realAnt.setName(ant.getName());
            realAnt.setAntOrder(ant.getAntOrder());
            realAnt.setAntFamily(ant.getAntFamily());
            realAnt.setAntSubfamily(ant.getAntSubfamily());
            realAnt.setAntGenus(ant.getAntGenus());
            realAnt.setAntSubgenus(ant.getAntSubgenus());
            realAnt.setAntSpecies(ant.getAntSpecies());
            realAnt.setNotes(ant.getNotes());

            antRepository.save(realAnt);
            model.addAttribute("message", "Amostra de formiga salva com sucesso");
            
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Ocorreu um erro ao salvar os dados da amostra");
            Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nestDetails(new Long(1), model);
    }
    
    
    
    
    public static class EditionNotAllowedException extends Exception {
        @Override
        public String getMessage() {
            return "Operação não autorizada para esse usuário";
        }
    }
}
