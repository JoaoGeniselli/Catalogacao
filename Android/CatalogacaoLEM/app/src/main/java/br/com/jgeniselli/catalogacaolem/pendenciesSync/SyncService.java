package br.com.jgeniselli.catalogacaolem.pendenciesSync;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.service.ServiceCallback;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;

/**
 * Created by jgeniselli on 17/09/17.
 */

public class SyncService {

    private ServiceCallback<Boolean> callback;
    private Realm realm;

    private NestsSyncService nestsSyncService;

    public SyncService(ServiceCallback<Boolean> callback, Realm realm) {
        //Retrofit retrofit = new Retrofit.Builder().baseUrl()

        this.callback = callback;
        this.realm = realm;
    }

    public void triggerSync() {
        RealmResults<AntNest> results = realm.where(AntNest.class).isNull("registerId").findAll();


    }

    @Background
    public void syncNests() {

    }

    @Background
    public void syncDataUpdates() {

    }

    @Background
    public void syncAnts() {

    }

    @Background
    public void syncPhotos() {

    }
}
