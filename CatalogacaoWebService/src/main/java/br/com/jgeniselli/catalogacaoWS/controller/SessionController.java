/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author joaog
 */
@RestController
public class SessionController extends BaseController {
    
    @Autowired
    public UserRepository userRepository;
    
    @RequestMapping(path = "/validateUser", method=POST)
    public ResponseEntity<?> validateUser(@RequestBody User user) {

        ArrayList<User> users = (ArrayList) userRepository.findByUserId(user.getUserId());
        
        if (users.isEmpty()) {
            String message = "Usuário não cadastrado";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        
        User fetchedUser = users.get(0);
        
        if (user.getPassword().equals(fetchedUser.getPassword())) {
            String token = generateToken(user.getUserId());
            HashMap<String, String> map = new HashMap<>();
            map.put("token", token);
            return new ResponseEntity<>(map, HttpStatus.OK) ;
        } else {
            String message = "Senha inválida";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @GetMapping(path="/registerUser")
    public String addNewUser(
            @RequestParam(name="name", required=true) String username,
            @RequestParam(name="password", required=true) String password) {
        User user = new User();
        user.setUserId(username);
        user.setPassword(password);
        userRepository.save(user);
        return username + ": Salvo";
    }
    
    public String generateToken(String userId) {
        Date now = new Date();
        String systemKey = "c4t4l0g4c40";
        String hashSpec = systemKey + userId + now.toString();
                
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException exception) {
            return "";
        }
        m.update(hashSpec.getBytes(),0,hashSpec.length());
        return "" + new BigInteger(1,m.digest()).toString(16);
    }
}
