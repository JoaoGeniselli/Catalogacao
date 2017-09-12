package br.com.jgeniselli.catalogacaolem.common.form;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
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
import org.androidannotations.annotations.FragmentById;
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

    @ViewById
    RecyclerView formRecycler;

    @ViewById
    Toolbar toolbar;

    @Extra
    FormModel form;

    @Extra
    SaveFormStrategy saveStrategy;

    @FragmentById
    FormFragment formFragment;

    @AfterViews
    public void init() {
        if (form == null) {
            return;
        }
        this.formFragment.setup(form, saveStrategy);
    }
}
