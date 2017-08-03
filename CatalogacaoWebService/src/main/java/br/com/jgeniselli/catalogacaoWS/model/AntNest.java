package br.com.jgeniselli.catalogacaoWS.model;

import br.com.jgeniselli.catalogacaoWS.model.location.City;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 * Created by joaog on 26/05/2017.
 */

@Entity
public class AntNest implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long nestId;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne
    private City city;
    
    private String name;
    private String vegetation;
    
    @OneToMany(mappedBy = "nest")
    private Set<DataUpdateVisit> dataUpdateVisits;

    @OneToMany(mappedBy = "antNest")
    private Set<Ant> ants;
    
    @OneToMany
    private List<Photo> photos;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date registerDate; 
   
    public AntNest() {
        
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public Set<DataUpdateVisit> getDataUpdateVisits() {
        return dataUpdateVisits;
    }

    public void setDataUpdateVisits(Set<DataUpdateVisit> dataUpdateVisits) {
        this.dataUpdateVisits = dataUpdateVisits;
    }

    public Set<Ant> getAnts() {
        return ants;
    }

    public void setAnts(Set<Ant> ants) {
        this.ants = ants;
    }    

    public Long getNestId() {
        return nestId;
    }

    
    
    
}
