/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model;

/**
 *
 * @author joaog
 */
public class User {
    private String userId;
    private String password;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
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
    
    
    
    
}
