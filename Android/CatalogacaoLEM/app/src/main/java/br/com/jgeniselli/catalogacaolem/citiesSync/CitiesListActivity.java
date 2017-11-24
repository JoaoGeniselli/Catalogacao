package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.BuildConfig;
import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.MyPreferences_;
import br.com.jgeniselli.catalogacaolem.common.Utils;
import br.com.jgeniselli.catalogacaolem.common.form.activity.CitySelectionActivity_;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.service.NestSyncController;
import br.com.jgeniselli.catalogacaolem.common.service.ServiceCallback;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAnt;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAntNest;
import br.com.jgeniselli.catalogacaolem.pendenciesSync.NestsSyncService;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Retrofit;

@EActivity(R.layout.activity_cities_list)
public class CitiesListActivity extends AppCompatActivity implements CitySynchronizationRemoveListener {

    public static final int CITY_SELECTION_RESULT_CODE = 1;

    @ViewById
    TextView lastUpdateLabel;

    @ViewById
    RecyclerView citiesRecycler;

    @ViewById
    FloatingActionButton addCityButton;

    @Extra
    Long addedCityId;

    @ViewById
    Toolbar toolbar;

    @Pref
    MyPreferences_ prefs;

    @Bean
    NestSyncController nestSyncController;

    @ViewById
    ProgressBar progressBar;

    private Realm realm;

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        RealmResults<CitySynchronization> cities = realm.where(CitySynchronization.class).findAll();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        citiesRecycler.setLayoutManager(layoutManager);

        CitiesListLineAdapter adapter = new CitiesListLineAdapter(cities, this);
        citiesRecycler.setAdapter(adapter);

        setupUpdateLabel();

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToCitySelection();
            }
        });

        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cities_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (realm != null) {
            realm.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sync: {
                startSync();
                break;
            }
            case R.id.action_erase_all: {
                break;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @UiThread
    public void redirectToCitySelection() {
        CitySelectionActivity_.intent(this).startForResult(CITY_SELECTION_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CITY_SELECTION_RESULT_CODE && data != null) {
            Long cityId = data.getLongExtra("addedCityId", 0);

            CityModel city = null;
            if (cityId != 0) {
                city = realm.where(CityModel.class).equalTo("id", cityId).findFirst();

                CitySynchronization alreadyAddedSync = realm.where(CitySynchronization.class).equalTo("city.id", cityId).findFirst();

                if (alreadyAddedSync == null) {
                    CitySynchronization syncCity = new CitySynchronization();
                    syncCity.setCity(city);

                    realm.beginTransaction();
                    realm.copyToRealm(syncCity);
                    realm.commitTransaction();

                    CitiesListLineAdapter adapter = (CitiesListLineAdapter) citiesRecycler.getAdapter();
                    RealmResults<CitySynchronization> cities = realm.where(CitySynchronization.class).findAll();
                    adapter.setCities(cities);
                }
                else {
                    Toast.makeText(this, R.string.alert_city_already_added, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Background
    public void startSync() {
        if (!nestSyncController.isLoading()) {
            startLoading();
            Realm realm = Realm.getDefaultInstance();

            RealmResults<CitySynchronization> cities = realm.where(CitySynchronization.class)
                    .findAll();

            nestSyncController.syncCities(cities, realm, this, new ServiceCallback<List<RestAntNest>>() {
                @Override
                public void onFinish(List<RestAntNest> response, Error error) {
                    stopLoading();

                    if (error != null) {
                        Utils.showAlert(
                                "Atenção",
                                "Ocorreu um erro ao obter informações das cidades",
                                CitiesListActivity.this
                        );
                    }
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    prefs.lastCitiesSynchronization().put(format.format(new Date()));
                    setupUpdateLabel();

                    if (response != null) {
                        reloadData();
                    }
                }
            });
        }
    }

    @UiThread
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @UiThread
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @UiThread
    public void reloadData() {
        citiesRecycler.getAdapter().notifyDataSetChanged();
    }

    @UiThread
    public void setupUpdateLabel() {
        if (prefs.lastCitiesSynchronization().get().length() > 0) {
            lastUpdateLabel.setText(prefs.lastCitiesSynchronization().get());
        } else {
            lastUpdateLabel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Subscribe
    public void onSyncCityRemoveRequestEvent(SyncCityRemoveRequestEvent event) {
        if (event.getCitySynchronization() != null) {

            RealmResults<AntNest> nestResults = realm
                    .where(AntNest.class)
                    .equalTo("city.id", event.getCitySynchronization().getCity().getId())
                    .isNull("registerId")
                    .findAll();

            ArrayList<Long> nestIds = new ArrayList<>();
            for (AntNest nest : nestResults) {
                nestIds.add(nest.getNestId());
            }

            RealmResults<DataUpdateVisit> dataUpdateResults = realm
                    .where(DataUpdateVisit.class)
                    .in("nest.nestId", nestIds.toArray(new Long[nestIds.size()]))
                    .isNull("registerId")
                    .endGroup()
                    .findAll();

            if (nestResults.size() > 0 || dataUpdateResults.size() > 0) {
                showAlert(R.string.city_has_not_synchronized_nests_alert);
            } else {
                realm.beginTransaction();
                event.getCitySynchronization().deleteFromRealm();
                realm.commitTransaction();

                CitiesListLineAdapter adapter = (CitiesListLineAdapter) citiesRecycler.getAdapter();
                RealmResults<CitySynchronization> cities = realm.where(CitySynchronization.class).findAll();
                adapter.setCities(cities);
            }

        }
    }

    @UiThread
    public void showAlert(int messageId) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(R.string.warning)
                .setMessage(messageId)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void onRemoveRequest(CitySynchronization sync) {
        if (sync != null) {

            RealmResults<AntNest> nestResults = realm
                    .where(AntNest.class)
                    .equalTo("city.id", sync.getCity().getId())
                    .isNull("registerId")
                    .findAll();

            ArrayList<Long> nestIds = new ArrayList<>();
            for (AntNest nest : nestResults) {
                nestIds.add(nest.getNestId());
            }

            boolean hasPendingDataUpdates = false;

            if (nestIds.size() > 0) {
                long count = realm
                        .where(DataUpdateVisit.class)
                        .in("nest.nestId", nestIds.toArray(new Long[nestIds.size()]))
                        .isNull("registerId")
                        .count();

                hasPendingDataUpdates = count > 0;
            }

            if (nestResults.size() > 0 || hasPendingDataUpdates) {
                showAlert(R.string.city_has_not_synchronized_nests_alert);
            } else {
                nestResults = realm
                        .where(AntNest.class)
                        .equalTo("city.id", sync.getCity().getId())
                        .findAll();

                realm.beginTransaction();
                sync.deleteFromRealm();
                for (AntNest nest : nestResults) {
                    nest.deleteFromRealm();
                }
                realm.commitTransaction();

                CitiesListLineAdapter adapter = (CitiesListLineAdapter) citiesRecycler.getAdapter();
                RealmResults<CitySynchronization> cities = realm.where(CitySynchronization.class).findAll();
                adapter.setCities(cities);
            }
        }
    }
}
