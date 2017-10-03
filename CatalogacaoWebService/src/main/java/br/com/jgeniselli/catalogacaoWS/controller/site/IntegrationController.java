/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jgeniselli
 */
@Controller
public class IntegrationController {
    
    @Value("${server.googleMapsAPIKey}")
    private String googleMapsAPIKey;
    
    @RequestMapping("/integration/googleMapsApiSrc")
    public String googleMapsApiSource() {
        return "https://maps.googleapis.com/maps/api/js?key=" + googleMapsAPIKey + "&callback=initMap";
    }
    
}
