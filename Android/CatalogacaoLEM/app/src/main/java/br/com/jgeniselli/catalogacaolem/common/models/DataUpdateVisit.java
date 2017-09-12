package br.com.jgeniselli.catalogacaolem.common.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joaog on 12/08/2017.
 */

public class DataUpdateVisit extends RealmObject implements Serializable {

    private AntNest nest;

    @PrimaryKey
    private Long dataUpdateId;

    private Date collectionDate;
    private Date registerDate;
    private Long collector;

    private Coordinate newBeginingPoint;
    private Coordinate newEndingPoint;

    private String notes;

    private RealmList<Ant> ants;

    public DataUpdateVisit() {
        collectionDate = new Date();
    }

    public Long getDataUpdateId() {
        return dataUpdateId;
    }

    public void setDataUpdateId(Long dataUpdateId) {
        this.dataUpdateId = dataUpdateId;
    }

    public Date getCollectionDate() {
        return collectionDate;
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

    public Long getCollector() {
        return collector;
    }

    public void setCollector(Long collector) {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RealmList<Ant> getAnts() {
        return ants;
    }

    public void setAnts(RealmList<Ant> ants) {
        this.ants = ants;
    }

    public AntNest getNest() {
        return nest;
    }

    public void setNest(AntNest nest) {
        this.nest = nest;
    }
}
