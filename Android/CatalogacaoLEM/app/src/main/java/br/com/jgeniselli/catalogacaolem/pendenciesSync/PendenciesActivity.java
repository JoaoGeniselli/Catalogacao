package br.com.jgeniselli.catalogacaolem.pendenciesSync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import br.com.jgeniselli.catalogacaolem.common.service.NestSyncController;
import br.com.jgeniselli.catalogacaolem.common.service.ServiceCallback;
import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_pendencies)
public class PendenciesActivity extends AppCompatActivity {

    @Bean
    NestSyncController nestSyncController;

    private PendenciesState state;

    @ViewById
    TextView statusTextView;

    @ViewById
    RightDetailView nestPendenciesView;

    @ViewById
    RightDetailView dataUpdatePendenciesView;

    @ViewById
    RightDetailView antPendenciesView;

    @ViewById
    RightDetailView photoPendenciesView;

    @ViewById
    Button syncButton;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    Toolbar toolbar;

    Realm realm;

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
        state = new PendenciesState.PendenciesStateDefault();
        loadCounters();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @UiThread
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @UiThread
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @UiThread
    public void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG);
    }

    @Click(R.id.sync_button)
    public void onSyncButtonClick() {
        this.state.startSynchronizing(nestSyncController, this);
    }

    public void setState(PendenciesState state) {
        this.state = state;
    }

    public void startNestsSynchronization() {
        nestSyncController.synchronizeNewNests(this, new ServiceCallback<List<AntNest>>() {
            @Override
            public void onFinish(List<AntNest> failedNests, Error error) {
                startDataUpdateSynchronization();
            }
        });
    }

    public void startDataUpdateSynchronization() {

        nestSyncController.addDataUpdates(this, new ServiceCallback<List<DataUpdateVisit>>() {
            @Override
            public void onFinish(List<DataUpdateVisit> failedDataUpdates, Error error) {
                startAntsSynchronization();
            }
        });
    }

    public void startAntsSynchronization() {

        nestSyncController.synchronizeNewAnts(new ServiceCallback<List<Ant>>() {
            @Override
            public void onFinish(List<Ant> response, Error error) {

            }
        });
    }

    @UiThread
    public void loadCounters() {
        long counter = realm.where(AntNest.class).isNull("registerId").count();
        nestPendenciesView.setDetail(String.format("%d", counter));

        counter = realm.where(DataUpdateVisit.class).isNull("registerId").count();
        dataUpdatePendenciesView.setDetail(String.format("%d", counter));

        counter = realm.where(Ant.class).isNull("registerId").count();
        antPendenciesView.setDetail(String.format("%d", counter));

        counter = realm.where(PhotoModel.class).isNull("registerId").count();
        photoPendenciesView.setDetail(String.format("%d", counter));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusUpdateEvent(StatusUpdateEvent event) {
        this.statusTextView.setText(event.getMessageId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTaggedCounterDecreaseEvent(TaggedCounterDecreaseEvent event) {
        try {
            RightDetailView view = (RightDetailView) findViewById(event.getTag());
            int counterValue = Integer.valueOf(view.getDetail());
            if (counterValue > 0) {
                --counterValue;
                view.setDetail(String.format("%d", counterValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
