package br.com.jgeniselli.catalogacaolem.common.form.event;

/**
 * Created by jgeniselli on 27/08/17.
 */

public class CoordinatesResponseEvent {

    private double latitude;
    private double longitude;

    public CoordinatesResponseEvent(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
