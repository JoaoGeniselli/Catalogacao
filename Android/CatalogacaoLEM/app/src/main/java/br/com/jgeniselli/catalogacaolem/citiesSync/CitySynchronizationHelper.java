package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;
import io.realm.Realm;

/**
 * Created by jgeniselli on 21/08/17.
 */

public class CitySynchronizationHelper {

    private Context context;
    private WeakReference<Realm> weakRealm;

    public CitySynchronizationHelper(@NonNull Context context, @NonNull Realm realm) {
        this.context = context;
        if (realm != null) {
            this.weakRealm = new WeakReference<Realm>(realm);
        }
    }

    public ArrayList<CitySynchronization> getSynchronizationCities() {
        return null;
    }

    public void removeCityFromSynchronization(CityModel city, SynchronizationCallbackListener listener) {

    }

    public void addCityToSynchronization(CityModel city, SynchronizationCallbackListener listener) {

    }

    public void synchronizeCities(SynchronizationCallbackListener listener) {

    }
}
