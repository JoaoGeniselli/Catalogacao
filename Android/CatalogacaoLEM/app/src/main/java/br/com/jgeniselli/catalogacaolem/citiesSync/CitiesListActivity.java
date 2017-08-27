package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class CitiesListActivity extends AppCompatActivity implements CheckboxViewSelectionListener {

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

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);

        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        RealmResults<CityModel> cities = realm.where(CityModel.class).findAll();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        citiesRecycler.setLayoutManager(layoutManager);

        CitiesListLineAdapter adapter = new CitiesListLineAdapter(cities, this);
        citiesRecycler.setAdapter(adapter);

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToCitySelection();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cities_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void checkboxViewDidChangeSelection(CheckboxViewHolder viewHolder) {

    }

    @UiThread
    public void redirectToCitySelection() {
        CitySelectionActivity_.intent(this).startForResult(CITY_SELECTION_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CITY_SELECTION_RESULT_CODE) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
