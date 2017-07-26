package br.com.jgeniselli.catalogacaoWS.model;

import br.com.jgeniselli.catalogacaoWS.model.location.City;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by joaog on 26/05/2017.
 */

@Entity
public class AntNest implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer nestId;
    
//    @ManyToOne
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
//    private City city;
    
    private String name;
    private String vegetation;
    
//    @ManyToOne
//    private DataUpdateVisit lastVisit;
//    
    private ArrayList<Ant> ants;

    public AntNest() {
        
    }

    public Integer getNestId() {
        return nestId;
    }

    public void setNestId(Integer nestId) {
        this.nestId = nestId;
    }

//    public City getCity() {
//        return city;
//    }
//
//    public void setCity(City city) {
//        this.city = city;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVegetation() {
        return vegetation;
    }

    public void setVegetation(String vegetation) {
        this.vegetation = vegetation;
    }
//
//    public DataUpdateVisit getLastVisit() {
//        return lastVisit;
//    }
//
//    public void setLastVisit(DataUpdateVisit lastVisit) {
//        this.lastVisit = lastVisit;
//    }
}
