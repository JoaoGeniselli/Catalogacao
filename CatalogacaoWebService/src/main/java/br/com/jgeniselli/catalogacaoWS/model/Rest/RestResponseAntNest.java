/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.Coordinate;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author jgeniselli
 */
public class RestResponseAntNest {
    
    private Long registerId;
    private Long cityId;
    private String vegetation;
    private String name;
    private String address;
    private Coordinate beginingPoint;
    private Coordinate endingPoint;
    private String lastDataUpdateDate;
    private String registerDate;

    public RestResponseAntNest() {
        
    }
    
    public RestResponseAntNest(AntNest nest) {
        setup(nest);
    }
    
    private void setup(AntNest nest) {
        setRegisterId(nest.getNestId());
        setCityId(nest.getCity().getId());
        setVegetation(nest.getVegetation());
        setAddress(nest.getAddress());
        name = nest.getName();
        
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        setRegisterDate(formatter.format(nest.getRegisterDate()));
        
        List<DataUpdateVisit> sortedDataUpdates 
                = new ArrayList(nest.getDataUpdateVisits());
        
        Comparator<DataUpdateVisit> comparator 
                = (DataUpdateVisit o1, DataUpdateVisit o2) -> 
                        o1.getRegisterDate().compareTo(o2.getRegisterDate());
        
        Collections.sort(sortedDataUpdates, comparator);

        DataUpdateVisit lastVisit 
                = sortedDataUpdates.get(sortedDataUpdates.size() - 1);
        
        setBeginingPoint(lastVisit.getNewEndingPoint());
        setEndingPoint(lastVisit.getNewEndingPoint());

        setLastDataUpdateDate(formatter.format(lastVisit.getRegisterDate()));
    }

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getVegetation() {
        return vegetation;
    }

    public void setVegetation(String vegetation) {
        this.vegetation = vegetation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLastDataUpdateDate() {
        return lastDataUpdateDate;
    }

    public void setLastDataUpdateDate(String lastDataUpdateDate) {
        this.lastDataUpdateDate = lastDataUpdateDate;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
