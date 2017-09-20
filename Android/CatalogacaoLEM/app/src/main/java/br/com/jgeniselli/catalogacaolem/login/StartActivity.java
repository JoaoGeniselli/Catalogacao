package br.com.jgeniselli.catalogacaolem.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Date;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.MyPreferences_;
import br.com.jgeniselli.catalogacaolem.common.Utils;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.location.CountryModel;
import br.com.jgeniselli.catalogacaolem.common.location.StateModel;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.common.service.ServiceCallback;
import br.com.jgeniselli.catalogacaolem.main.MainActivity_;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;

@EActivity(R.layout.activity_start)
public class StartActivity extends AppCompatActivity {

    @ViewById
    TextView statusLabel;

    @RestService
    SessionRestClient restClient;

    @Bean
    SessionController sessionController;

    @Pref
    MyPreferences_ prefs;

    @AfterViews
    public void afterViews() {
        User sharedUser = User.shared(this);

        // TODO: REMOVER MOCK
//        if (sharedUser.getToken() == null || sharedUser.getToken().length() == 0) {
//            redirectToLogin();
//        } else {
            requestLocationContent();
//        }
    }

    public void requestLocationContent() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<CountryModel> countries = realm.where(CountryModel.class).findAll();
        if (countries.size() == 0) {
            // TODO: REMOVER MOCK
            mockData(realm);
            redirectToMain();

            realm.close();

            // TODO: IMPLEMENTAÇÃO ORIGINAL - DESCOMENTAR
//            sessionController.requestLocationContent(new ServiceCallback<String>() {
//                @Override
//                public void onFinish(String response, Error error) {
//                    if (error != null) {
//                        Utils.showAlert("Atenção", error.getMessage(), StartActivity.this);
//                    } else {
//                        redirectToMain();
//                    }
//                }
//            });
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

    @UiThread
    public void redirectToLogin() {
        LoginActivity_.intent(this).start();
    }

    @Override
    public void onBackPressed() {

    }

    public void mockData(Realm realm) {
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

        User user = new User("joao", null);
        user.setName("João");
        user.setToken("1234-1234");

        User.setSharedUser(user, this);
    }
}
