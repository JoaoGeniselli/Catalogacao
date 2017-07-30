/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author jgeniselli
 */
@Entity
public class CountryState implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;   
    
    @ManyToOne
    private Country country;
    
    @OneToMany(mappedBy = "state")
    private List<City> cities;
    
    private String initials;
    
    private String name;

    public CountryState() {
    }

    public CountryState(Country country, String name) {
        this.country = country;
        this.name = name;
        this.cities = new ArrayList<>();
    }

    public void setCountry(Country country) {
        this.country = country;
        this.cities = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
    
    

}
