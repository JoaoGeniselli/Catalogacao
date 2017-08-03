/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

import br.com.jgeniselli.catalogacaoWS.model.Coordinate;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @author joaog
 */
public class RestDataUpdateVisit {
    private Long nestId;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date collectionDate;
    
    private Long collectorId;
    private Coordinate beginingPoint;
    private Coordinate endingPoint;
    private String notes;
    
    public RestDataUpdateVisit() {
        
    }

    public Long getNestId() {
        return nestId;
    }

    public void setNestId(Long nestId) {
        this.nestId = nestId;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectorId) {
        this.collectorId = collectorId;
    }

    public Coordinate getBeginingPoint() {
        return beginingPoint;
    }

    public void setBeginingPoint(Coordinate beginingPoint) {
        this.beginingPoint = beginingPoint;
    }

    public Coordinate getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(Coordinate endingPoint) {
        this.endingPoint = endingPoint;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
