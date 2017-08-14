package br.com.jgeniselli.catalogacaolem.common.models;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by joaog on 12/08/2017.
 */

public class Coordinate extends RealmObject implements Serializable {

    private double latitude;
    private double longitude;

    public Coordinate() {

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
