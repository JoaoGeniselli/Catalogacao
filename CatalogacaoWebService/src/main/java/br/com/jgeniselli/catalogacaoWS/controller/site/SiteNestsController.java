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
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import br.com.jgeniselli.catalogacaoWS.model.form.AntReportForm;
import br.com.jgeniselli.catalogacaoWS.model.form.Form;
import br.com.jgeniselli.catalogacaoWS.model.form.NestReportForm;
import br.com.jgeniselli.catalogacaoWS.model.location.CityRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.CountryState;
import br.com.jgeniselli.catalogacaoWS.model.location.CountryStateRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
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
    CoordinateRepository coordinateRepository; 
    
    @Autowired
    DataSource dataSource; 
    
    @Autowired
    AntRepository antRepository; 
    
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
            
            JRSaver.saveObject(jasperReport, "nestReport.jasper");
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
            
            JRSaver.saveObject(jasperReport, "antReport.jasper");
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
    
    @RequestMapping("/mockReport")
    public void mock(HttpServletResponse response) {

        InputStream employeeReportStream
            = getClass().getResourceAsStream("/reports/nestReport.jrxml");
        try {
            JasperReport jasperReport
                    = JasperCompileManager.compileReport(employeeReportStream);
            
            JRSaver.saveObject(jasperReport, "nestReport.jasper");
            
            DataSource ds = (DataSource)appContext.getBean("dataSource");
            Connection connection = ds.getConnection();
            
            HashMap params = new HashMap();
            params.put("IMAGE_PATH", imagePath);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, params, connection);
            
            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        
            exporter.setExporterOutput(
              new SimpleHtmlExporterOutput("/Users/jgeniselli/Desktop/nestReport.html"));

            try {
                exporter.exportReport();
            } catch (JRException ex) {
                Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            response.setContentType("text/html");
            File imgPath = new File("/Users/jgeniselli/Desktop/nestReport.html");
            InputStream in = new FileInputStream(imgPath);
            IOUtils.copy(in, response.getOutputStream());

        } catch (JRException | SQLException | IOException ex) {
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
            model.addAttribute("destinationPath", "/web/saveDataUpdate");
            model.addAttribute("title", "Gerar Relatório de Formigas");
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
    
    public static class EditionNotAllowedException extends Exception {
        @Override
        public String getMessage() {
            return "Operação não autorizada para esse usuário";
        }
    }
    
    
    @RequestMapping("/saveAnt")
    public void saveAntEdition(@ModelAttribute Form<Ant> form, Model model) {
//        try {
//            form.applyChanges();
//            antRepository.save(form.getCommandObject());
//        } catch (Form.FormValidationException ex) {
//            Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    @RequestMapping("/saveDataUpdate")
    public String saveDataUpdateEdition(@ModelAttribute @Valid DataUpdateVisit dataUpdate, BindingResult bindingResult, Model model) {
        try {
            
            DataUpdateVisit realVisit = dataUpdateVisitRepository.findOne(dataUpdate.getId());
            
            realVisit.setNotes(dataUpdate.getNotes());
            realVisit.getNewBeginingPoint().setLatitude(dataUpdate.getNewBeginingPoint().getLatitude());
            realVisit.getNewBeginingPoint().setLongitude(dataUpdate.getNewBeginingPoint().getLongitude());
            
            realVisit.getNewEndingPoint().setLatitude(dataUpdate.getNewBeginingPoint().getLatitude());
            realVisit.getNewEndingPoint().setLongitude(dataUpdate.getNewBeginingPoint().getLongitude());

            dataUpdateVisitRepository.save(realVisit);
            coordinateRepository.save(realVisit.getNewBeginingPoint());
            coordinateRepository.save(realVisit.getNewEndingPoint());
            
            model.addAttribute("message", "Atualização salva com sucesso");
            
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Ocorreu um erro ao salvar a atualização");
            Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nestDetails(new Long(1), model);
    }
}
