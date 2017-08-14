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

import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.location.CountryModel;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryModel;
import br.com.jgeniselli.catalogacaolem.common.location.RestCountryToRealmAdapter;
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

    @Background
    public void requestLocationContent() {

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<CountryModel> countries = realm.where(CountryModel.class).findAll();
        if (countries.size() == 0) {
            realm.close();
            updateStatus(getString(R.string.updating_location_content));
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
