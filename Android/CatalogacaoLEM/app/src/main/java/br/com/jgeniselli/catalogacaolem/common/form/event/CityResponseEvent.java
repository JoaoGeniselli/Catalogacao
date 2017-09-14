package br.com.jgeniselli.catalogacaolem.common.form.event;

import br.com.jgeniselli.catalogacaolem.common.location.CityModel;

/**
 * Created by jgeniselli on 27/08/17.
 */

public class CityResponseEvent {

    private CityModel cityModel;

    public CityResponseEvent(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }
}
