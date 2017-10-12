/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jgeniselli
 * @param <T>
 */
public class FormGenerator<T> {
    
    private T model;

    public FormGenerator(T model) {
        this.model = model;
    }

    /**
     *
     * @param model
     */
    public void generateForm(T model) {
        JsonUtils<T> jsonUtils = new JsonUtils<T>();
        
        String json;
        HashMap map;
        try {
            json = jsonUtils.jsonStringFromObject(model);
            map = jsonUtils.mapFromJsonString(json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FormGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
