/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.MobileToken;
import br.com.jgeniselli.catalogacaoWS.model.MobileTokenRepository;
import br.com.jgeniselli.catalogacaoWS.model.Rest.AuthenticatedRestModel;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author joaog
 */
@RequestMapping(path = "api/v1")
public class BaseController {
    
    @Autowired
    protected MobileTokenRepository mobileTokenRepository;
    
    @Autowired
    protected UserRepository userRepository;
   
    protected void validateTokenString(AuthenticatedRestModel model) throws InvalidTokenException {
        List<MobileToken> tokens = mobileTokenRepository.findByToken(model.getToken());
        if (tokens.isEmpty()) {
            throw new InvalidTokenException();
        }
    }
    
    protected User getUser(Long userId) throws InvalidUserException {
        User user = userRepository.findOne(userId);
        if (user == null) {
           throw new InvalidUserException();
        }
        return user;
    }
    
    public class InvalidTokenException extends Exception {
        @Override
        public String getMessage() {
            return "Usuário não autenticado";
        }
    }
    
    public class InvalidUserException extends Exception {
        @Override
        public String getMessage() {
            return "Usuário inválido";
        }
    }
}
