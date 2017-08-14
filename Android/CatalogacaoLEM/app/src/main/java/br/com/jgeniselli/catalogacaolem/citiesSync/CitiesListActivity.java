package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class CitiesListActivity extends AppCompatActivity implements CheckboxViewSelectionListener {

    @ViewById
    TextView lastUpdateLabel;

    @ViewById
    RecyclerView citiesRecycler;

    @ViewById
    FloatingActionButton addCityButton;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);

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
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void checkboxViewDidChangeSelection(CheckboxViewHolder viewHolder) {

    }

    @UiThread
    public void redirectToCitySelection() {
        CitySelectionActivity_.intent(this).start();
    }
}
