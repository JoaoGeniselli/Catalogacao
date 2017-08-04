/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

/**
 *
 * @author joaog
 */
public class IndexAnswer {
    
    private final Long id;
    private final String message;

    public IndexAnswer(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }
}
