package br.com.jgeniselli.catalogacaolem.common.form;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.citiesSync.CitySelectionActivity_;
import br.com.jgeniselli.catalogacaolem.citiesSync.CoordinatesSelectionActivity_;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import io.realm.Realm;

@EActivity(R.layout.activity_form)
public class FormActivity extends AppCompatActivity {

    public static final int CITY_SELECTION_RESULT_ID = 5;
    public static final int COORDINATES_SELECTION_RESULT_ID = 10;

    @ViewById
    RecyclerView formRecycler;

    @Extra
    FormModel form;

    Realm realm;

    @AfterViews
    public void init() {
        if (form == null) {
            return;
        }
        setTitle(form.getTitle());

        realm = Realm.getDefaultInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        formRecycler.setLayoutManager(layoutManager);

        FormLineAdapter adapter = new FormLineAdapter(this, form);
        formRecycler.setAdapter(adapter);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == CITY_SELECTION_RESULT_ID) {
            Long cityId = data.getLongExtra("addedCityId", 0);

            CityModel city = null;
            if (cityId != 0) {
                city = realm.where(CityModel.class).equalTo("id", cityId).findFirst();
            }
            EventBus.getDefault().post(new CityResponseEvent(city));

        } else if (requestCode == COORDINATES_SELECTION_RESULT_ID) {

            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityRequestEvent(CityRequestEvent event) {
        CitySelectionActivity_
                .intent(this)
                .startForResult(CITY_SELECTION_RESULT_ID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityResponseEvent(CityResponseEvent event) {
        formRecycler.getAdapter().notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCoordinatesRequestEvent(CoordinatesRequestEvent event) {
        CoordinatesSelectionActivity_
                .intent(this)
                .startForResult(COORDINATES_SELECTION_RESULT_ID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCoordinatesResponseEvent(CoordinatesResponseEvent event) {
        formRecycler.getAdapter().notifyDataSetChanged();
    }
}
