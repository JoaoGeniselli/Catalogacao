/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import java.util.List;
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
        
        br.com.jgeniselli.catalogacaoWS.model.User user = null;
        try {
            org.springframework.security.core.userdetails.User springUser = 
                (org.springframework.security.core.userdetails.User) 
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
            List users = userRepository.findByUsername(springUser.getUsername());
            
            if (users.isEmpty()) {
                throw new Exception();
            }
            user = (br.com.jgeniselli.catalogacaoWS.model.User) users.get(0);
            
        } catch (Exception e) {
            throw new UserNotLoggedException();
        }
        return user;
    }
    
    public static class UserNotLoggedException extends Exception {

        @Override
        public String getMessage() {
            return "Usuário não autenticado";
        }
    }
    
}
