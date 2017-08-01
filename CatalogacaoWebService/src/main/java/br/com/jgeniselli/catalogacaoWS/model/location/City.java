/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author joaog
 */
@Entity
public class City implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne
    @JsonIgnore
    private CountryState state;
    
    public City() {
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(String name, CountryState state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public CountryState getState() {
        return state;
    }

    public Integer getId() {
        return id;
    }

    public void setState(CountryState state) {
        this.state = state;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
