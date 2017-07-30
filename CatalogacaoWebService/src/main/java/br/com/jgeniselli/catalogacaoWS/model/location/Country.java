/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author jgeniselli
 */
@Entity
public class Country implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;   

    @OneToMany(mappedBy = "country")
    private Set<CountryState> states;
    
    private String name;

    public Country() {
        this.states = new HashSet<>();
    }

    public Country(String name) {
        this.name = name;
        this.states = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }   

    public Set<CountryState> getStates() {
        return states;
    }

    public void setStates(Set<CountryState> states) {
        this.states = states;
    }    
}
