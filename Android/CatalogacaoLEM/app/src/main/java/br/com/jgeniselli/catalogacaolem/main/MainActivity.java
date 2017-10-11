package br.com.jgeniselli.catalogacaolem.main;
import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.citiesSync.CitiesListActivity_;
import br.com.jgeniselli.catalogacaolem.common.form.activity.FormActivity_;
import br.com.jgeniselli.catalogacaolem.common.form.modelAdapters.AntNestFormToModelAdapter;
import br.com.jgeniselli.catalogacaolem.common.form.factory.FormFactoryNewNest;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormModel;
import br.com.jgeniselli.catalogacaolem.common.form.model.SaveFormStrategy;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.login.User;
import br.com.jgeniselli.catalogacaolem.nestDetails.NestDashboardActivity_;
import br.com.jgeniselli.catalogacaolem.pendenciesSync.PendenciesActivity_;
import io.realm.Realm;
import io.realm.RealmResults;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@EActivity
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById
    RecyclerView nestsRecycler;

    @ViewById
    TextView nameLabel;

    @ViewById
    TextView userIdLabel;


    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNewNestForm();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nameLabel = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nameLabel);
        userIdLabel = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userIdLabel);


        User user = User.shared(this);

        nameLabel.setText(user.getName());
        userIdLabel.setText(user.getUsername());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        nestsRecycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nestsRecycler.getAdapter().notifyDataSetChanged();
    }

    @UiThread
    public void callNewNestForm() {
        FormModel form = new FormFactoryNewNest().getForm();
        FormActivity_.intent(this).form(form).saveStrategy(new MainNewNestSaveStrategy()).start();
    }

    @AfterViews
    public void init() {
        realm = Realm.getDefaultInstance();
        RealmResults<AntNest> nests = realm.where(AntNest.class).findAllSorted("name");
        NestSummaryLineAdapter adapter = new NestSummaryLineAdapter(nests);
        nestsRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // do nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_download_nests) {
            CitiesListActivity_.intent(this).start();
        } else if (id == R.id.nav_sync) {
            PendenciesActivity_.intent(this).start();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNestDetailsRequestEvent(NestDetailsRequestEvent event) {
        NestDashboardActivity_.intent(this).antNestId(event.getNest().getNestId()).start();
    }

    private static class MainNewNestSaveStrategy extends SaveFormStrategy {
        @Override
        public void save(FormModel form, Realm realmInstance, SaveStrategyCallback completion) {
            AntNest nest = new AntNestFormToModelAdapter().modelFromForm(form);
            try {
                Number currentIdNum = realmInstance.where(AntNest.class).max("nestId");
                long nextId = currentIdNum == null ? 1 : currentIdNum.intValue() + 1;
                nest.setNestId(nextId);

                currentIdNum = realmInstance.where(Coordinate.class).max("id");
                long nextCoordinateId = currentIdNum == null ? 1 : currentIdNum.intValue() + 1;
                nest.getBeginingPoint().setId(nextCoordinateId);

                nextCoordinateId++;
                nest.getEndingPoint().setId(nextCoordinateId);
                
                realmInstance.beginTransaction();
                realmInstance.copyToRealm(nest);
                realmInstance.commitTransaction();
            }
            catch (Exception e) {
                e.printStackTrace();
                nest = null;
            }
            finally {
                if (completion != null) {
                    if (nest == null) {
                        completion.onSaveError(R.string.error_saving_form);
                    } else {
                        completion.onSaveSuccess();
                    }
                }
            }
        }
    }
}
