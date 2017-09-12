package br.com.jgeniselli.catalogacaolem.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Date;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.location.CountryModel;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryModel;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryToRealmAdapter;
import br.com.jgeniselli.catalogacaolem.common.location.StateModel;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.main.MainActivity_;
import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class StartActivity extends AppCompatActivity {

    @ViewById
    TextView statusLabel;

    @ViewById
    Button test_btn;

    @RestService
    SessionRestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

//        test_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestLocationContent();
//            }
//        });

        requestLocationContent();
    }

    public void requestLocationContent() {

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<CountryModel> countries = realm.where(CountryModel.class).findAll();
        if (countries.size() == 0) {

            // TODO: REMOVER -- Mock mock mock
            realm.beginTransaction();

            CountryModel country = realm.createObject(CountryModel.class, 1);
            country.setName("Brasil");

            StateModel state = realm.createObject(StateModel.class, 1);
            state.setName("São Paulo");
            state.setInitials("SP");
            state.setCountry(country);

            CityModel city = realm.createObject(CityModel.class, 1);
            city.setName("Santa Gertrudes");
            city.setState(state);

            city = realm.createObject(CityModel.class, 2);
            city.setName("Rio Claro");
            city.setState(state);

            Coordinate coordinate1 = realm.createObject(Coordinate.class, 1);
            coordinate1.setLatitude(-21.00000012345);
            coordinate1.setLongitude(-47.00000012345);

            Coordinate coordinate2 = realm.createObject(Coordinate.class, 2);
            coordinate1.setLatitude(-21.00000012345);
            coordinate1.setLongitude(-47.00000012345);

            AntNest nest = realm.createObject(AntNest.class, 1);
            nest.setName("Ninho A");
            nest.setCity(city);
            nest.setRegisterDate(new Date());
            nest.setVegetation("Mata Atlântica");
            nest.setBeginingPoint(coordinate1);
            nest.setEndingPoint(coordinate2);

            nest = realm.createObject(AntNest.class, 2);
            nest.setName("Ninho B");
            nest.setCity(city);
            nest.setRegisterDate(new Date());
            nest.setVegetation("Mata Atlântica");
            nest.setBeginingPoint(coordinate1);
            nest.setEndingPoint(coordinate2);

            city = realm.createObject(CityModel.class, 3);
            city.setName("Limeira");
            city.setState(state);

            nest = realm.createObject(AntNest.class, 3);
            nest.setName("Ninho C");
            nest.setCity(city);
            nest.setRegisterDate(new Date());
            nest.setVegetation("Mata Atlântica");
            nest.setBeginingPoint(coordinate1);
            nest.setEndingPoint(coordinate2);

            city = realm.createObject(CityModel.class, 4);
            city.setName("Piracicaba");
            city.setState(state);

            realm.commitTransaction();

            realm.close();
            updateStatus(getString(R.string.updating_location_content));

            redirectToMain();

            // Implementação original
            /*
            try {
                List<RestCountryModel> responseCountry = restClient.locationContent();
                if (responseCountry.size() > 0) {
                    RestCountryToRealmAdapter.saveCountryModel(responseCountry.get(0));
                }
                redirectToMain();
            } catch (Exception e) {
                System.out.println("Eita preula\n\n");
                e.printStackTrace();
            }
            */
        } else {
            realm.close();
            redirectToMain();
        }
    }

    @UiThread
    public void redirectToMain() {
        MainActivity_.intent(this).start();
    }

    @UiThread
    public void updateStatus(String status) {
        statusLabel.setText(status);
    }
}
