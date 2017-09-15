/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

import br.com.jgeniselli.catalogacaoWS.model.Coordinate;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.location.City;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;

/**
 *
 * @author joaog
 */
public class RestAntNest implements Serializable {

    private Long cityId;
    private String name;
    private Long collectorId;
    private String address;
    
    private String token;
    
    private String vegetation;
    
    private Coordinate beginingPoint;
    private Coordinate endingPoint;
    
    public RestAntNest() {
        
    }

    public String getName() {
        return name;
    }

    public String getVegetation() {
        return vegetation;
    }

    public Coordinate getBeginingPoint() {
        return beginingPoint;
    }

    public Coordinate getEndingPoint() {
        return endingPoint;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectorId) {
        this.collectorId = collectorId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
