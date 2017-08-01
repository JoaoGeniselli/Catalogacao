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
    
    @JsonProperty(value = "cityId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    private City city;
    
    private String name;
    
    @JsonProperty(value = "collectorId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    private User collector;
    
    private String vegetation;
    
    private Coordinate beginingPoint;
    private Coordinate endingPoint;
    
    public RestAntNest() {
        
    }

    public City getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public User getCollector() {
        return collector;
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
    
    
}
