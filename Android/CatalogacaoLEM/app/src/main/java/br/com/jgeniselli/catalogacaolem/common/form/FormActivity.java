package br.com.jgeniselli.catalogacaolem.common.form;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
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

    @ViewById
    Toolbar toolbar;

    @Extra
    FormModel form;

    @Extra
    SaveFormStrategy saveStrategy;

    Realm realm;

    @AfterViews
    public void init() {
        if (form == null) {
            return;
        }
        setTitle(form.getTitle());
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        formRecycler.setLayoutManager(layoutManager);

        FormLineAdapter adapter = new FormLineAdapter(this, form);
        formRecycler.setAdapter(adapter);

        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save: {
                finishForm();
                break;
            }
            default: {
                break;
            }
        }
        return true;
    }

    public void finishForm() {
        if (form.validate()) {
            if (saveStrategy != null) {
                saveStrategy.save(form, realm, new SaveFormStrategy.SaveStrategyCallback() {
                    @Override
                    public void onSaveSuccess() {
                        finish();
                    }
                    @Override
                    public void onSaveError(int messageId) {
                        showAlert(messageId);
                    }
                });
            }
        } else {
            formRecycler.getAdapter().notifyDataSetChanged();
        }
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

            EventBus.getDefault().post(new CoordinatesResponseEvent(latitude, longitude));
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

    @UiThread
    public void showAlert(int messageId) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(R.string.warning)
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
