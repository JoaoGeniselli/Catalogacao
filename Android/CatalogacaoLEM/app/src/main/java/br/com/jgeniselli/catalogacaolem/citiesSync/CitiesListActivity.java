package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.MyPreferences_;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;
import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_cities_list)
public class CitiesListActivity extends AppCompatActivity {

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

    private Realm realm;

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        RealmResults<CitySynchronization> cities = realm.where(CitySynchronization.class).findAll();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        citiesRecycler.setLayoutManager(layoutManager);

        CitiesListLineAdapter adapter = new CitiesListLineAdapter(cities);
        citiesRecycler.setAdapter(adapter);

        if (prefs.lastCitiesSynchronization().get().length() > 0) {
            lastUpdateLabel.setText(prefs.lastCitiesSynchronization().get());
        } else {
            lastUpdateLabel.setVisibility(View.GONE);
        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Subscribe
    public void onSyncCityRemoveRequestEvent(SyncCityRemoveRequestEvent event) {
        if (event.getCitySynchronization() != null) {
            realm.beginTransaction();
            event.getCitySynchronization().deleteFromRealm();
            realm.commitTransaction();

            CitiesListLineAdapter adapter = (CitiesListLineAdapter) citiesRecycler.getAdapter();
            RealmResults<CitySynchronization> cities = realm.where(CitySynchronization.class).findAll();
            adapter.setCities(cities);
        }
    }
}
