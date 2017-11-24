package br.com.jgeniselli.catalogacaolem.citiesSync;

import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;

/**
 * Created by jgeniselli on 20/11/2017.
 */

interface CitySynchronizationRemoveListener {

    void onRemoveRequest(CitySynchronization sync);
}
