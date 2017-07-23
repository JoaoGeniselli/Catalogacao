/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author joaog
 */
@Entity
@Table(name = "CIDADE")
public class City implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String name;
//    private String state;
    private String country;

    public City() {
        
    }

    public City(String name, String state, String country) {
        this.name = name;
//        this.state = state;
        this.country = country;
    }

    public String getName() {
        return name;
    }

//    public String getState() {
//        return state;
//    }

    public String getCountry() {
        return country;
    }
}
