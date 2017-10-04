/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.AntNestRepository;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
