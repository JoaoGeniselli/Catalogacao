/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jgeniselli
 */
@RequestMapping(path = "web")
public class BaseSiteController {
    
    @Autowired
    protected UserRepository userRepository;
    
    public br.com.jgeniselli.catalogacaoWS.model.User getSessionUser() throws UserNotLoggedException {
        try {
            org.springframework.security.core.userdetails.User springUser = 
                (org.springframework.security.core.userdetails.User) 
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
            br.com.jgeniselli.catalogacaoWS.model.User user = 
                    (br.com.jgeniselli.catalogacaoWS.model.User) 
                    userRepository.findByUsername(springUser.getUsername());
        } catch (Exception e) {
            throw new UserNotLoggedException();
        }
        return null;
    }
    
    public static class UserNotLoggedException extends Exception {

        @Override
        public String getMessage() {
            return "Usuário não autenticado";
        }
    }
    
}
