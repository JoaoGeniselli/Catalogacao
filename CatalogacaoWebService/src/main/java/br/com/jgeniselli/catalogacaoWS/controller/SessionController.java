/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.Rest.RestUser;
import br.com.jgeniselli.catalogacaoWS.model.Role;
import br.com.jgeniselli.catalogacaoWS.model.RoleRepository;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author joaog
 */
@RestController
public class SessionController extends BaseController {
    
    @Value("${server.bCryptEncoderStrength}")
    private Integer bCryptEncoderStrength;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    public UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @RequestMapping(path = "/validateUser", method=POST)
    public ResponseEntity<?> validateUser(@RequestBody User user) {

        ArrayList<User> users = (ArrayList) userRepository.findByUsername(user.getUsername());
        
        if (users.isEmpty()) {
            String message = "Usuário não cadastrado";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        
        User fetchedUser = users.get(0);
        
        if (user.getPassword().equals(fetchedUser.getPassword())) {
            String token = generateToken(user.getUsername());
            HashMap<String, String> map = new HashMap<>();
            map.put("token", token);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            String message = "Senha inválida";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping(path="/registerUser")
    public ResponseEntity<String> addNewUser(@RequestBody RestUser userInfo) {
        
        if (!userInfo.valid()) {
            String message = "Campos inválidos";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        
        List<User> users = userRepository.findByUsername(userInfo.getUserId());
        if(!users.isEmpty()) {
            String message = "id de usuário já cadastrado";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(userInfo.getUserId());
        user.setName(userInfo.getName());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setEnable(Boolean.TRUE);
        
        Role role = roleRepository.findByRole("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        
        user.setRoles(roles);
        
        userRepository.save(user);
        return new ResponseEntity<>(user.getName() + ": Salvo", HttpStatus.CREATED);
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
