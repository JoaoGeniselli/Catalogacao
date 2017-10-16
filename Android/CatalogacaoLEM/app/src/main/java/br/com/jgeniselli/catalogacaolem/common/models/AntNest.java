package br.com.jgeniselli.catalogacaolem.common.models;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.Date;

import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import io.realm.AntNestRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joaog on 12/08/2017.
 */

public class AntNest extends RealmObject implements Serializable {

    @PrimaryKey
    private Long nestId;

    private Long registerId;

    private CityModel city;
    private String name;
    private String vegetation;
    private String address;
    private Coordinate beginingPoint;
    private Coordinate endingPoint;
    private Date lastDataUpdateDate;
    private boolean registered;

    private RealmList<PhotoModel> photos;

    private RealmList<DataUpdateVisit> dataUpdateVisits;

    private Date registerDate;

    public AntNest() {
        registered = false;
        this.photos = new RealmList<>();
    }

    public boolean isRegistered() {
        return registerDate != null;
    }

    public String getCompleteAddress() {
        return city.getState().getCountry().getName() + ", " +
                city.getState().getName() + ", " +
                city.getName();
    }

    public Long getNestId() {
        return nestId;
    }

    public void setNestId(Long nestId) {
        this.nestId = nestId;
    }

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
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

    public RealmList<DataUpdateVisit> getDataUpdateVisits() {
        return dataUpdateVisits;
    }

    public void setDataUpdateVisits(RealmList<DataUpdateVisit> dataUpdateVisits) {
        this.dataUpdateVisits = dataUpdateVisits;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

    public Date getLastDataUpdateDate() {
        return lastDataUpdateDate;
    }

    public void setLastDataUpdateDate(Date lastDataUpdateDate) {
        this.lastDataUpdateDate = lastDataUpdateDate;
    }

    public RealmList<PhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(RealmList<PhotoModel> photos) {
        this.photos = photos;
    }
}
