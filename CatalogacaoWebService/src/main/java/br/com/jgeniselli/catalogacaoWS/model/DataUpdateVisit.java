package br.com.jgeniselli.catalogacaoWS.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class DataUpdateVisit implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date collectionDate; // data
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date registerDate; // data
    
    @ManyToOne
    private User collector;

    @OneToOne
    private Coordinate newBeginingPoint;
    
    @OneToOne
    private Coordinate newEndingPoint;

    private String notes;

    @ManyToOne
    private AntNest nest;

    @OneToMany(mappedBy = "visit")
    private List<Ant> ants;
    
    @OneToMany
    private List<Photo> photos;

    public DataUpdateVisit() {
        
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setObservation(String observation) {
        this.notes = observation;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public String getObservation() {
        return notes;
    }
    
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public List<Ant> getAnts() {
        return ants;
    }

    public User getCollector() {
        return collector;
    }

    public void setCollector(User collector) {
        this.collector = collector;
    }

    public Coordinate getNewBeginingPoint() {
        return newBeginingPoint;
    }

    public void setNewBeginingPoint(Coordinate newBeginingPoint) {
        this.newBeginingPoint = newBeginingPoint;
    }

    public Coordinate getNewEndingPoint() {
        return newEndingPoint;
    }

    public void setNewEndingPoint(Coordinate newEndingPoint) {
        this.newEndingPoint = newEndingPoint;
    }

    public AntNest getNest() {
        return nest;
    }

    public void setNest(AntNest nest) {
        this.nest = nest;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
    
    
}
