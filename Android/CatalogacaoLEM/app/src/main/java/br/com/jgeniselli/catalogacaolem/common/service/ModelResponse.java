package br.com.jgeniselli.catalogacaolem.common.service;

/**
 * Created by jgeniselli on 14/09/17.
 */

public class ModelResponse {

    private long id;
    private String message;

    public ModelResponse() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
