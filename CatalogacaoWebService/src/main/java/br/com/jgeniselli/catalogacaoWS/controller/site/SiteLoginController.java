/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller.site;

import br.com.jgeniselli.catalogacaoWS.model.Rest.RestUser;
import br.com.jgeniselli.catalogacaoWS.model.Role;
import br.com.jgeniselli.catalogacaoWS.model.RoleRepository;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jgeniselli
 */
@Controller
public class SiteLoginController extends BaseSiteController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    
    @RequestMapping(path = "/login.html")
    public String login(Model model) {
        model.addAttribute("user", new RestUser());
        return "login";
    }
    
    @RequestMapping(path = "/forbidden.html")
    public String forbidden(Model model) {
        return "forbidden";
    }
    
    @RequestMapping(path = "/register")
    public String register(Model model) {
        
        if (!model.asMap().containsKey("user")) {
            model.addAttribute("user", new RestUser());
        }
        model.addAttribute("formFragment", "form-user-register");
        model.addAttribute("destinationPath", "/web/registerUser");
        model.addAttribute("title", "Cadastrar Usuário");
        model.addAttribute("roles", roleRepository.findAll());

        return "reportForm";
    }
    
    @RequestMapping(path = "/registerUser")
    public String addNewUser(@ModelAttribute RestUser userInfo, Model model) {
        
        if (!userInfo.valid()) {
            model.addAttribute("errorMessage", "Campos inválidos");
            model.addAttribute("user", userInfo);
            return register(model);
        }
        
        List<User> users = userRepository.findByUsername(userInfo.getUsername());
        if(!users.isEmpty()) {
            model.addAttribute("errorMessage", "Id de usuário já cadastrado");
            model.addAttribute("user", userInfo);
            return register(model);
        }

        User user = new User();
        user.setUsername(userInfo.getUsername());
        user.setName(userInfo.getName());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setEnable(Boolean.TRUE);
        
        Role role = roleRepository.findOne(userInfo.getRole());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        
        user.setRoles(roles);
        
        userRepository.save(user);
        model.addAttribute("message", "Usuário " + user.getName() + " cadastrado com sucesso!");
        return register(model);
    }
}
