package br.com.jgeniselli.catalogacaolem.common.location;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by jgeniselli on 21/08/17.
 */

public class CitySynchronization extends RealmObject {

    private CityModel city;
    private Date lastSynchronization;

    public CitySynchronization() {

    }

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    public Date getLastSynchronization() {
        return lastSynchronization;
    }

    public void setLastSynchronization(Date lastSynchronization) {
        this.lastSynchronization = lastSynchronization;
    }
}
