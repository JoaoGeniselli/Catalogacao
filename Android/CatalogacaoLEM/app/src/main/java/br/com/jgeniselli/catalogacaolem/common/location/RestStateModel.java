package br.com.jgeniselli.catalogacaolem.common.location;

import java.util.List;

import io.realm.RealmModel;

/**
 * Created by joaog on 05/08/2017.
 */

public class RestStateModel implements RealmModel {

    private Long id;
    private String initials;
    private String name;

    private List<RestCityModel> cities;

    public RestStateModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RestCityModel> getCities() {
        return cities;
    }

    public void setCities(List<RestCityModel> cities) {
        this.cities = cities;
    }
}
