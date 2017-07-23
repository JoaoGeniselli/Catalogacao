/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS;

import br.com.jgeniselli.catalogacaoWS.model.User;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @RequestMapping(path = "/validateUser", method=POST)
    public ResponseEntity<?> validateUser(@RequestBody User user) {
        
        if (user.getUserId().equals("joao") && user.getPassword().equals("senha")) {
            String token = generateToken(user.getUserId());
            HashMap<String, String> map = new HashMap<>();
            map.put("token", token);
            return new ResponseEntity<>(map, HttpStatus.OK) ;
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
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
