package br.com.jgeniselli.catalogacaolem.common.service;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.MyPreferences_;
import br.com.jgeniselli.catalogacaolem.common.form.modelAdapters.AntNestFormToModelAdapter;
import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by jgeniselli on 14/09/17.
 */

@EBean
public class NestSyncController {

    @RestService
    NestSyncRestClient restClient;

    @Bean
    BaseErrorHandler errorHandler;

    @AfterInject
    void afterInject() {
        restClient.setRestErrorHandler(errorHandler);
    }

    private boolean loading = false;

    public void syncCities(
            RealmResults<CitySynchronization> cities,
            Realm realmInstance,
            ServiceCallback<List<AntNest>> completion) {

        loading = true;

        ArrayList<Long> cityIds = new ArrayList<>();

        for (CitySynchronization citySync : cities) {
            if (citySync.getCity() != null && citySync.getCity().getId() != null) {
                cityIds.add(citySync.getCity().getId());
            }
        }

        List<AntNest> response = null;
        Error error = null;
        try {
            response = restClient.nestsByCities(cityIds);
        } catch (Exception e) {
            response = null;
        } finally {
            if (completion != null) {
                saveNests(response, realmInstance);
                completion.onFinish(response, error);
            }
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
    public void synchronizeNewNests(
            List<AntNest> nests,
            Realm realmInstance,
            ServiceCallback<List<AntNest>> callback) {

        List<AntNest> failedNests = new ArrayList<>();

        for (final AntNest nest : nests) {
            try {
                final ModelResponse response = restClient.addNewNest(nest);
                Realm.Transaction transaction = new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        nest.setRegisterId(response.getId());
                        nest.setRegisterDate(new Date());
                    }
                };
                realmInstance.executeTransaction(transaction);
            } catch (Exception e) {
                failedNests.add(nest);
            }
        }

        if (callback != null) {
            callback.onFinish(failedNests, null);
        }
    }

    @Background
    public void addDataUpdates(
            List<DataUpdateVisit> dataUpdateVisits,
            Realm realmInstance,
            ServiceCallback<List<DataUpdateVisit>> callback
            ) {

        List<DataUpdateVisit> failedDataUpdates = new ArrayList<>();

        for (final DataUpdateVisit dataUpdate : dataUpdateVisits) {
            try {
                final ModelResponse response = restClient.addNewDataUpdate(dataUpdate);
                Realm.Transaction transaction = new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        dataUpdate.setRegisterId(response.getId());
                    }
                };
                realmInstance.executeTransaction(transaction);
            } catch (Exception e) {
                failedDataUpdates.add(dataUpdate);
            }
        }

        if (callback != null) {
            callback.onFinish(failedDataUpdates, null);
        }
    }

    public boolean isLoading() {
        return loading;
    }
}

