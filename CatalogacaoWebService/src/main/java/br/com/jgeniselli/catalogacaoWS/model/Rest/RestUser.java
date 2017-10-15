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
    
    private String username;
    private String password;
    private String confirmPassword;
    private String name;
    
    private Long role;

    public RestUser() {
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean valid() {
        return (username != null && username.length() > 0) &&
                (password != null && password.length() > 0) &&
                (confirmPassword != null && confirmPassword.length() > 0) &&
                (confirmPassword.equals(password)) &&
                (name != null && name.length() > 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}
