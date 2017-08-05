package br.com.jgeniselli.catalogacaolem.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.location.CountryModel;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryModel;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryToRealmAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class StartActivity extends AppCompatActivity {

    @RestService
    SessionRestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        requestLocationContent();
    }

    @Background
    public void requestLocationContent() {

        Realm realm = Realm.getDefaultInstance();

        final RealmResults<CountryModel> countries = realm.where(CountryModel.class).findAll();

        if (countries.size() == 0) {

            try {
                RestCountryModel responseCountry = restClient.locationContent();
                RestCountryToRealmAdapter.saveCountryModel(responseCountry);
            } catch (Exception e) {
                System.out.println("Eita preula\n\n" + e);
            }

        }
    }
}
