package br.com.jgeniselli.catalogacaolem.common.models;

import java.io.Serializable;
import java.util.HashMap;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joaog on 12/08/2017.
 */

public class Coordinate extends RealmObject implements Serializable {

    @PrimaryKey
    private Long id;

    private double latitude;
    private double longitude;

    public Coordinate() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFormattedPosition() {
        return String.format("Lat: %02.14f\nLon: %02.14f", latitude, longitude);
    }

    public void updateFrom(Coordinate unmanagedCoordinate) {
        setLatitude(unmanagedCoordinate.getLatitude());
        setLongitude(unmanagedCoordinate.getLongitude());
    }

    public HashMap<String, Double> toHashMap() {
        HashMap hashmap = new HashMap();

        hashmap.put("latitude", latitude);
        hashmap.put("longitude", longitude);

        return hashmap;
    }

    public boolean zeroCoordinates() {
        return latitude == 0.0 && longitude == 0.0;
    }
}
