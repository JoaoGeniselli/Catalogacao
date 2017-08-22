package br.com.jgeniselli.catalogacaolem.citiesSync;

/**
 * Created by jgeniselli on 21/08/17.
 */

public interface SynchronizationCallbackListener {

    void onSynchronizationSuccess(Object response);
    void onSynchronizationError(Error error);

}
