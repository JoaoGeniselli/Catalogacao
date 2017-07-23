/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author joaog
 */
@RequestMapping(path = "catalogacao")
public class BaseController {
    
    public boolean tokenIsValid(String token) {
        return true;
    }    
}
