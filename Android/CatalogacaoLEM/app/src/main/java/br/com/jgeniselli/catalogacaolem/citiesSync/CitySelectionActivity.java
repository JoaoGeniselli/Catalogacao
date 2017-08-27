package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.DefaultLineAdapter;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

@EActivity
public class CitySelectionActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewById
    EditText cityField;

    @ViewById
    RecyclerView citiesRecycler;

    private RealmResults<CityModel> filteredCities;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        realm = Realm.getDefaultInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        citiesRecycler.setLayoutManager(layoutManager);

        DefaultLineAdapter adapter = new DefaultLineAdapter(new ArrayList<String>(), this);
        citiesRecycler.setAdapter(adapter);

        cityField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                int minimumFilterLength = 3;
                if (sequence.length() >= minimumFilterLength) {
                    updateRecyclerContent(sequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void updateRecyclerContent(String searchText) {
        RealmResults<CityModel> cities = realm
                .where(CityModel.class)
                .contains("name", searchText, Case.INSENSITIVE)
                .findAllSorted("name");

        filteredCities = cities;

        ArrayList<String> citiesNames = new ArrayList<>();
        for (CityModel city : cities) {
            citiesNames.add(String.format("%s - %s", city.getState().getInitials(), city.getName()));
        }

        citiesRecycler.removeAllViews();
        ((DefaultLineAdapter)citiesRecycler.getAdapter()).setValues(citiesNames);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    private void pushCityIdToPreviousActivity(Long cityId) {
        Intent intent = new Intent();
        intent.putExtra("addedCityId", cityId);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        Integer position = citiesRecycler.indexOfChild(v);;
        if (position != null && position >= 0 && position <= filteredCities.size()) {
            CityModel selectedCity = filteredCities.get(position);
            pushCityIdToPreviousActivity(selectedCity.getId());
        }
    }
}
