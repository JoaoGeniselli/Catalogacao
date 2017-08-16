/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author joaog
 */
public class CitiesListRequest implements Serializable {
    
   public ArrayList<Long> cities;

    public CitiesListRequest() {
    
    }
}
