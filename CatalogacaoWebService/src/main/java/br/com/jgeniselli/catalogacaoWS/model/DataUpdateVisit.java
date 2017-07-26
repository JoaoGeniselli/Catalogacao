package br.com.jgeniselli.catalogacaoWS.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by joaog on 26/05/2017.
 */
@Entity
public class DataUpdateVisit implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private Date collectionDate; // data
    
//    @ManyToOne
//    private User collector;
//
//    @ManyToOne
//    private Coordinate newBeginingPoint;
//    
//    @ManyToOne
//    private Coordinate newEndingPoint;

    private String observation;
//    
//    @ManyToOne
//    private AntNest nest;
//    
//    @OneToMany
//    private ArrayList<Photo> photos;

    public DataUpdateVisit() {
        
    }

    public DataUpdateVisit(Date collectionDate, User collector, AntNest nest) {
        this.collectionDate = collectionDate;
//        this.collector = collector;
//        this.nest = nest;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

//    public void setCollector(User collector) {
//        this.collector = collector;
//    }
//
//    public void setNewBeginingPoint(Coordinate newBeginingPoint) {
//        this.newBeginingPoint = newBeginingPoint;
//    }
//
//    public void setNewEndingPoint(Coordinate newEndingPoint) {
//        this.newEndingPoint = newEndingPoint;
//    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

//    public void setNest(AntNest nest) {
//        this.nest = nest;
//        nest.setLastVisit(this);
//    }

    public Date getCollectionDate() {
        return collectionDate;
    }

//    public User getCollector() {
//        return collector;
//    }
//
//    public Coordinate getNewBeginingPoint() {
//        return newBeginingPoint;
//    }
//
//    public Coordinate getNewEndingPoint() {
//        return newEndingPoint;
//    }

    public String getObservation() {
        return observation;
    }

//    public AntNest getNest() {
//        return nest;
//    }
}
