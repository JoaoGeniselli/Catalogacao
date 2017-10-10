/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import br.com.jgeniselli.catalogacaoWS.model.Rest.RestUser;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jgeniselli
 */
@Controller
public class SiteLoginController extends BaseSiteController {
    
    @Autowired
    UserRepository userRepository;
    
    @RequestMapping(path = "/login.html")
    public String login(Model model) {
        model.addAttribute("user", new RestUser());
        return "login";
    }
    
//    @PostMapping(path = "/login/validateUser")
    public String validateSiteUser(@ModelAttribute RestUser user, Model model) {
        
        ArrayList<User> users = (ArrayList) userRepository
                .findByUsername(user.getUserId());
        
        String targetHtml = "home";
        try {
            if (users.isEmpty()) {
                throw new Exception();
            }
            
            User fetchedUser = users.get(0);

            if (!user.getPassword().equals(fetchedUser.getPassword())) {
                throw new Exception();
            }
            // TODO: ADICIONAR ATRIBUTOS DA HOME AQUI
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Usuário ou senha inválidos");
            targetHtml = login(model);
            // TODO: ADICIONAR RECURSOS DE VALIDAÇÃO POR CAPTCHA
        }
        return targetHtml;
    }
    
    @RequestMapping(path = "/register.html")
    public String register(Model model) {
        return "register";
    }
    
    
    
}
