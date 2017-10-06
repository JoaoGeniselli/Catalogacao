/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jgeniselli
 * @param <T>
 */
public class JsonUtils<T> {
    
    public String jsonStringFromObject(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(object);
        return jsonInString;
    }
    
    public HashMap mapFromJsonString(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = mapper.readValue(json, HashMap.class);
        return map;
    }
    
    public List<HashMap> arrayFromJsonString(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<HashMap> map = mapper.readValue(json, ArrayList.class);
        return map;
    }
    
}
