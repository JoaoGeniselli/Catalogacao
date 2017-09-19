package br.com.jgeniselli.catalogacaolem.common.service;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.greenrobot.eventbus.EventBus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.BuildConfig;
import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.CatalogacaoLEMApplication;
import br.com.jgeniselli.catalogacaolem.common.MyPreferences_;
import br.com.jgeniselli.catalogacaolem.common.form.modelAdapters.AntNestFormToModelAdapter;
import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAntNest;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestDataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.pendenciesSync.NestsSyncService;
import br.com.jgeniselli.catalogacaolem.pendenciesSync.TaggedCounterDecreaseEvent;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jgeniselli on 14/09/17.
 */

@EBean
public class NestSyncController {

    private NestsSyncService syncService;

    public NestSyncController() {
        Retrofit retrofit = CatalogacaoLEMApplication.getDefaultRetrofit();
        this.syncService = retrofit.create(NestsSyncService.class);
    }

    private boolean loading = false;

    public void syncCities(
            RealmResults<CitySynchronization> cities,
            Realm realmInstance,
            ServiceCallback<List<AntNest>> completion) {

        loading = true;

        List<Long> cityIds = new ArrayList<>();

        for (CitySynchronization citySync : cities) {
            if (citySync.getCity() != null && citySync.getCity().getId() != null) {
                cityIds.add(citySync.getCity().getId());
            }
        }

        HashMap<String, List<Long>> params = new HashMap<>();
        params.put("cities", cityIds);

        List<AntNest> response = null;
        Error error = null;
        try {
            Call<List<AntNest>> call = syncService.nestsByCities(params);
            //response = restClient.nestsByCities(params);

            response = call.execute().body();
        } catch (Exception e) {
            response = null;
        } finally {
            if (completion != null) {
                saveNests(response, realmInstance);
                completion.onFinish(response, error);
            }
            loading = false;
        }
    }



    @Background
    public void saveNests(List<AntNest> nests, Realm realmInstance) {
        try {
            Number currentIdNum = realmInstance.where(AntNest.class).max("nestId");
            long nextNestId = currentIdNum == null ? 1 : currentIdNum.intValue() + 1;

            currentIdNum = realmInstance.where(Coordinate.class).max("id");
            long nextCoordinateId = currentIdNum == null ? 1 : currentIdNum.intValue() + 1;

            realmInstance.beginTransaction();

            for (AntNest nest : nests) {
                nest.setNestId(nextNestId);
                nextNestId++;

                if (nest.getBeginingPoint() != null) {
                    nest.getBeginingPoint().setId(nextCoordinateId);
                    nextCoordinateId++;
                }

                if (nest.getEndingPoint() != null) {
                    nest.getEndingPoint().setId(nextCoordinateId);
                    nextCoordinateId++;
                }
            }

            realmInstance.copyToRealm(nests);
            realmInstance.commitTransaction();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Background
    public void synchronizeNewNests(Context context, ServiceCallback<List<AntNest>> callback) {

        loading = true;
        Realm realm = Realm.getDefaultInstance();

        RealmResults<AntNest> nests = realm.where(AntNest.class).isNull("registerId").findAll();
        Error error = null;

        for (final AntNest nest : nests) {
            try {
                RestAntNest restAntNest = new RestAntNest(nest, context);
                Call<ModelResponse> call = syncService.addNewNest(restAntNest);
                Response<ModelResponse> response = call.execute();

                if (response.code() == HttpStatus.OK.value()) {
                    final ModelResponse body = response.body();

                    Realm.Transaction transaction = new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            nest.setRegisterId(body.getId());
                            nest.setRegisterDate(new Date());
                        }
                    };
                    realm.executeTransaction(transaction);

                    EventBus.getDefault()
                            .post(new TaggedCounterDecreaseEvent(R.id.nestPendenciesView));
                }
            } catch (Exception e) {
                error = new Error("Ocorreu um erro ao sincronizar os ninhos");
                e.printStackTrace();
            }
        }

        if (callback != null) {
            loading = false;
            realm.close();
            callback.onFinish(null, error);
        }
    }

    @Background
    public void addDataUpdates(Context context, ServiceCallback<List<DataUpdateVisit>> callback) {

        loading = true;
        Realm realm = Realm.getDefaultInstance();

        RealmResults<DataUpdateVisit> dataUpdateVisits = realm.where(DataUpdateVisit.class)
                .isNull("registerId")
                .isNotNull("nest.registerId")
                .findAll();

        Error error = null;

        for (final DataUpdateVisit dataUpdate : dataUpdateVisits) {
            try {
                RestDataUpdateVisit restDataUpdateVisit = new RestDataUpdateVisit(dataUpdate, context);
                Call<ModelResponse> call = syncService.addNewDataUpdate(restDataUpdateVisit);
                Response<ModelResponse> response = call.execute();

                if (response.code() == HttpStatus.OK.value()) {
                    final ModelResponse body = response.body();
                    Realm.Transaction transaction = new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            dataUpdate.setRegisterId(body.getId());
                            dataUpdate.getNest().setEndingPoint(dataUpdate.getNewEndingPoint());
                            dataUpdate.getNest().setBeginingPoint(dataUpdate.getNewBeginingPoint());
                            dataUpdate.getNest().setLastDataUpdateDate(new Date());
                        }
                    };
                    realm.executeTransaction(transaction);

                    EventBus.getDefault()
                            .post(new TaggedCounterDecreaseEvent(R.id.dataUpdatePendenciesView));
                }
            } catch (Exception e) {
                e.printStackTrace();
                error = new Error("Ocorreu um erro ao sincronizar as atualizações de dados");
            }
        }

        if (callback != null) {
            loading = false;
            realm.close();
            callback.onFinish(null, error);
        }
    }


    public void synchronizeNewAnts(ServiceCallback<List<Ant>> callback) {
        loading = true;

        Realm realm = Realm.getDefaultInstance();

        RealmResults<DataUpdateVisit> results = realm.where(DataUpdateVisit.class)
                .isNotNull("registerId")
                .findAll();

        List<Ant> ants = new ArrayList<>();
        for (DataUpdateVisit dataUpdateVisit : results) {
            for (Ant ant : dataUpdateVisit.getAnts()) {
                if (ant.getRegisterId() == null) {
                    ants.add(ant);
                }
            }
        }

        Error error = null;

        try {
            Call<List<ModelResponse>> call = syncService.addAnts(ants);
            Response<List<ModelResponse>> response = call.execute();

            if (response.code() == HttpStatus.OK.value()) {

                for (int i = 0; i < response.body().size(); i++) {
                    ModelResponse modelResponse = response.body().get(i);
                    final Ant ant = ants.get(i);

                    if (modelResponse.getId() == 0) {
                        Realm.Transaction transaction = new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                ant.setRegisterDate(new Date());
                            }
                        };
                        realm.executeTransaction(transaction);
                        EventBus.getDefault()
                                .post(new TaggedCounterDecreaseEvent(R.id.antPendenciesView));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            error = new Error("Ocorreu um erro ao sincronizar as novas formigas...");
        }

        if (callback != null) {
            loading = false;
            realm.close();
            callback.onFinish(null, error);
        }
    }

    public boolean isLoading() {
        return loading;
    }
}

