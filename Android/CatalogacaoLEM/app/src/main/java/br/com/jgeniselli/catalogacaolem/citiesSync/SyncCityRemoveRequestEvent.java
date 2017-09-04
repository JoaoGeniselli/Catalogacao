package br.com.jgeniselli.catalogacaolem.citiesSync;

import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;

/**
 * Created by jgeniselli on 03/09/17.
 */

public class SyncCityRemoveRequestEvent {

    private CitySynchronization citySynchronization;

    public SyncCityRemoveRequestEvent(CitySynchronization citySynchronization) {
        this.citySynchronization = citySynchronization;
    }

    public CitySynchronization getCitySynchronization() {
        return citySynchronization;
    }
}
