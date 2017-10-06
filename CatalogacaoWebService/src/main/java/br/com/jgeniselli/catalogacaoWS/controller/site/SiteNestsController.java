/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.AntNestRepository;
import br.com.jgeniselli.catalogacaoWS.model.form.AntReportForm;
import br.com.jgeniselli.catalogacaoWS.model.form.NestReportForm;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @Autowired
    private ApplicationContext appContext;
    
    @Autowired
    AntNestRepository antNestRepository;
    
    @Autowired
    ServletContext servletContext; 
    
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
        model.addAttribute("title", "Gerar Relatório de Ninhos");
        NestReportForm form = new NestReportForm(null);
        model.addAttribute("form", form);
        return "reportForm";
    }
    
    @RequestMapping("/antReports.html")
    public String antReports(Model model) {
        model.addAttribute("title", "Gerar Relatório de Formigas");
        AntReportForm form = new AntReportForm(null);
        model.addAttribute("form", form);
        return "reportForm";
    }
    
    @RequestMapping("/mockReport")
    public String mock() {

        InputStream employeeReportStream
            = getClass().getResourceAsStream("/reports/antReport.jrxml");
        try {
            JasperReport jasperReport
                    = JasperCompileManager.compileReport(employeeReportStream);
            
            JRSaver.saveObject(jasperReport, "antReport.jasper");
            
            DataSource ds = (DataSource)appContext.getBean("dataSource");
            Connection connection = ds.getConnection();
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, null, connection);
            
            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        
            exporter.setExporterOutput(
              new SimpleHtmlExporterOutput("/Users/jgeniselli/Desktop/antReport.html"));

            try {
                exporter.exportReport();
            } catch (JRException ex) {
                Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JRException | SQLException ex) {
            Logger.getLogger(SiteNestsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    @RequestMapping("/editDataUpdate/{dataUpdateId}")
    public void editDataUpdate(@PathVariable String dataUpdateId) {
    
    }
}
