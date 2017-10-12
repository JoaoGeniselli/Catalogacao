package br.com.jgeniselli.catalogacaoWS.model;

import br.com.jgeniselli.catalogacaoWS.model.annotation.FormIgnore;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormTitle;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @FormIgnore
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date collectionDate; 
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @FormIgnore
    private Date registerDate;
    
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @FormIgnore
    private User collector;

    @OneToOne
    private Coordinate newBeginingPoint;
    
    @OneToOne
    private Coordinate newEndingPoint;

    private String notes;

    @ManyToOne
    @JsonIgnore
    @FormIgnore
    private AntNest nest;

    @OneToMany(mappedBy = "visit")
    @FormIgnore
    private List<Ant> ants;
    
    @OneToMany
    @FormIgnore
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

    public Date getCollectionDate() {
        return collectionDate;
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

    public Long getId() {
        return id;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
    
    public void getFormatedAntNames() {
        ArrayList<Ant> antList = (ArrayList<Ant>) this.getAnts();
        String antNames = "";

        for (Ant ant : antList) {
            if (ant.getName() != null) {
                antNames = antNames.concat(ant.getName() + "; ");
            }
        }
    }
}
