package br.com.jgeniselli.catalogacaolem.common.service;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.apache.commons.lang3.StringUtils;
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
import br.com.jgeniselli.catalogacaolem.common.form.model.ChooseFileManager;
import br.com.jgeniselli.catalogacaolem.common.form.modelAdapters.AntNestFormToModelAdapter;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.AntNestRepository;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.common.models.CoordinateRepository;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import br.com.jgeniselli.catalogacaolem.common.models.RestAntNestRealmAdapter;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAnt;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAntListRequest;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAntNest;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestDataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestPhoto;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestPhotoListRequest;
import br.com.jgeniselli.catalogacaolem.login.User;
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

    @Bean
    ChooseFileManager fileManager;

    public NestSyncController() {
        Retrofit retrofit = CatalogacaoLEMApplication.getDefaultRetrofit();
        this.syncService = retrofit.create(NestsSyncService.class);
    }

    private boolean loading = false;

    public void syncCities(
            RealmResults<CitySynchronization> cities,
            Realm realmInstance,
            Context context,
            ServiceCallback<List<RestAntNest>> completion) {

        loading = true;

        List<Long> cityIds = new ArrayList<>();

        for (CitySynchronization citySync : cities) {
            if (citySync.getCity() != null && citySync.getCity().getId() != null) {
                cityIds.add(citySync.getCity().getId());
            }
        }

        HashMap params = new HashMap<>();
        params.put("cities", cityIds);

        User user = User.shared(context);
        String token = user.getToken();

        if (!StringUtils.isEmpty(token)) {
            params.put("token", token);
        }

        List<RestAntNest> response = null;
        Error error = null;
        try {
            Call<List<RestAntNest>> call = syncService.nestsByCities(params);
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

    public void saveNests(List<RestAntNest> restAntNests, Realm realmInstance) {

        List<AntNest> unsavedNests = new ArrayList<>();
        AntNestRepository nestRepo = new AntNestRepository();
        for (RestAntNest restAntNest : restAntNests) {

            AntNest existingNest = realmInstance
                    .where(AntNest.class)
                    .equalTo("registerId", restAntNest.getRegisterId())
                    .findFirst();

            RestAntNestRealmAdapter adapter = new RestAntNestRealmAdapter();
            AntNest createdNest = adapter.adapt(restAntNest, realmInstance);

            if (existingNest == null) {
                unsavedNests.add(createdNest);
            } else {
                nestRepo.update(createdNest, existingNest, realmInstance);
            }
        }

        try {
            nestRepo.create(unsavedNests, realmInstance);
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
            if (dataUpdate.isEmpty()) {
                continue;
            }

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


    public void synchronizeNewAnts(Context context, ServiceCallback<List<Ant>> callback) {
        loading = true;

        Realm realm = Realm.getDefaultInstance();

        RealmResults<DataUpdateVisit> results = realm.where(DataUpdateVisit.class)
                .isNotNull("registerId")
                .findAll();

        final HashMap<RestAnt, Ant> antsByRestAnts = new HashMap<>();

        List<RestAnt> ants = new ArrayList<>();
        for (DataUpdateVisit dataUpdateVisit : results) {
            for (Ant ant : dataUpdateVisit.getAnts()) {
                if (ant.getRegisterId() == null) {
                    RestAnt restAnt = new RestAnt(ant, dataUpdateVisit.getDataUpdateId(), context);
                    ants.add(restAnt);

                    antsByRestAnts.put(restAnt, ant);
                }
            }
        }

        Error error = null;

        RestAntListRequest requestBody = new RestAntListRequest(context, ants);

        try {
            Call<List<ModelResponse>> call = syncService.addAnts(requestBody);
            Response<List<ModelResponse>> response = call.execute();

            if (response.code() == HttpStatus.CREATED.value()) {

                for (int i = 0; i < response.body().size(); i++) {
                    final ModelResponse modelResponse = response.body().get(i);
                    final Ant ant = antsByRestAnts.get(ants.get(i));

                    if (modelResponse.getId() != 0) {
                        Realm.Transaction transaction = new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                ant.setRegisterDate(new Date());
                                ant.setRegisterId(modelResponse.getId());
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

    public void synchronizeNewPhotos(Context context, ServiceCallback<List<Ant>> callback) {
        loading = true;

        Realm realm = Realm.getDefaultInstance();
        RealmResults<PhotoModel> photos = realm.where(PhotoModel.class).isNull("registerId").findAll();

        final HashMap<RestPhoto, PhotoModel> photosByRestPhotos = new HashMap<>();

        List<RestPhoto> restPhotos = new ArrayList<>();
        for (PhotoModel photo: photos) {
            RestPhoto restPhoto = new RestPhoto(photo, fileManager, context);
            restPhotos.add(restPhoto);
            photosByRestPhotos.put(restPhoto, photo);
        }

        Error error = null;
        RestPhotoListRequest request = new RestPhotoListRequest(context, restPhotos);

        try {
            if (photos.size() > 0) {
                Call<List<ModelResponse>> call = syncService.addPhotos(request);
                Response<List<ModelResponse>> response = call.execute();

                if (response.code() == HttpStatus.CREATED.value()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        final ModelResponse modelResponse = response.body().get(i);
                        final PhotoModel photoModel = photosByRestPhotos.get(photos.get(i));

                        if (modelResponse.getId() != 0) {
                            Realm.Transaction transaction = new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    photoModel.setRegisterId(modelResponse.getId());
                                }
                            };
                            realm.executeTransaction(transaction);
                            EventBus.getDefault()
                                    .post(new TaggedCounterDecreaseEvent(R.id.antPendenciesView));
                        }
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

    public void removeAllSynchronizedData() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<DataUpdateVisit> synchronizedDataUpdates = realm.where(DataUpdateVisit.class)
                .isNotNull("registerId")
                .findAll();
        synchronizedDataUpdates.deleteAllFromRealm();

        RealmResults<Ant> synchronizedAnts = realm.where(Ant.class)
                .isNotNull("registerId")
                .findAll();
        synchronizedAnts.deleteAllFromRealm();

        RealmResults<PhotoModel> synchronizedPhotos = realm.where(PhotoModel.class)
                .isNotNull("registerId")
                .findAll();
        synchronizedPhotos.deleteAllFromRealm();
    }

    public boolean isLoading() {
        return loading;
    }
}

