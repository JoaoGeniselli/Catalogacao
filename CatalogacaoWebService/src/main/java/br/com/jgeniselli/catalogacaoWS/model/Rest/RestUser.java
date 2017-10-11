/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

/**
 *
 * @author jgeniselli
 */
public class RestUser {
    
    private String userId;
    private String password;
    private String name;

    public RestUser() {
        
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean valid() {
        return (userId != null && userId.length() > 0) &&
                (password != null && password.length() > 0) &&
                (name != null && name.length() > 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
