package br.com.jgeniselli.catalogacaolem.nestDetails;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.FormModel;
import br.com.jgeniselli.catalogacaolem.common.form.SaveFormStrategy;
import br.com.jgeniselli.catalogacaolem.common.form.factory.FormFactoryDataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.form.FormFragment;
import br.com.jgeniselli.catalogacaolem.common.form.FormFragment_;
import br.com.jgeniselli.catalogacaolem.common.form.modelAdapters.DataUpdateFormToModelAdapter;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import io.realm.Realm;

@EActivity(R.layout.activity_nest_dashboard)
public class NestDashboardActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @Extra
    Long antNestId;

    private Fragment fragment;

    private Realm realm;

    protected DataUpdateVisit dataUpdateVisit;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (!(fragment instanceof NestInfoFragment_)) {
                        NestInfoFragment_ fragment = new NestInfoFragment_();
                        replaceFragment(fragment);
                        fragment.setup(NestDashboardActivity.this, dataUpdateVisit.getNest());
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (!(fragment instanceof FormFragment_)) {
                        DashboardSaveFormStrategy strategy = new DashboardSaveFormStrategy(dataUpdateVisit);
                        FormFragment fragment = FormFragment_
                                .builder()
                                .hidesSave(true)
                                .setup(new FormFactoryDataUpdateVisit().getForm(dataUpdateVisit), strategy)
                                .build();
                        replaceFragment(fragment);
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (!(fragment instanceof AntListFragment_)) {
                        AntListFragment fragment = AntListFragment_
                                .builder()
                                .build();
                        fragment.setDataUpdateVisit(dataUpdateVisit);
                        replaceFragment(fragment);
                    }
                    return true;
            }
            return false;
        }

    };

    private void replaceFragment(Fragment newFragment) {
        this.fragment = newFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();
    }

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        realm = Realm.getDefaultInstance();
        initVisit();

        if (dataUpdateVisit != null) {
            NestInfoFragment_ fragment = new NestInfoFragment_();
            fragment.setup(this, dataUpdateVisit.getNest());
            replaceFragment(fragment);
        }
    }

    private void initVisit() {
        AntNest nest = realm.where(AntNest.class).equalTo("nestId", antNestId).findFirst();
        if (nest == null) {
            // TODO: MOSTRAR ALERT DIALOG COM O ERRO
            return;
        }

        DataUpdateVisit visit = realm
                .where(DataUpdateVisit.class).equalTo("nest.nestId", nest.getNestId())
                .findFirst();

        if (visit == null) {
            visit = new DataUpdateVisit();
            visit.setNest(nest);
            try {
                Number currentIdNum = realm.where(DataUpdateVisit.class).max("dataUpdateId");
                long nextVisitId = currentIdNum == null ? 1 : currentIdNum.intValue() + 1;
                visit.setDataUpdateId(nextVisitId);

                currentIdNum = realm.where(Coordinate.class).max("id");
                long nextCoordinateId = currentIdNum == null ? 1 : currentIdNum.intValue() + 1;
                visit.setNewBeginingPoint(new Coordinate());
                visit.getNewBeginingPoint().setId(nextCoordinateId);

                nextCoordinateId++;
                visit.setNewEndingPoint(new Coordinate());
                visit.getNewEndingPoint().setId(nextCoordinateId);

                realm.beginTransaction();
                realm.copyToRealm(visit);
                realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
                visit = null;
            } finally {
                if (visit != null) {
                    dataUpdateVisit = visit;
                }
            }
        } else {
            dataUpdateVisit = visit;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }

    public static class DashboardSaveFormStrategy extends SaveFormStrategy {

        private WeakReference<DataUpdateVisit> dataUpdateVisit;

        public DashboardSaveFormStrategy(DataUpdateVisit dataUpdateVisit) {
            this.dataUpdateVisit = new WeakReference<DataUpdateVisit>(dataUpdateVisit);
        }

        @Override
        public void save(FormModel form, Realm realmInstance, SaveStrategyCallback completion) {

        }

        @Override
        public void saveBeforeDestroy(FormModel form, Realm realmInstance) {
            super.saveBeforeDestroy(form, realmInstance);

            if (dataUpdateVisit.get() != null) {
                DataUpdateFormToModelAdapter modelAdapter = new DataUpdateFormToModelAdapter();

                DataUpdateVisit unmanagedVisit = modelAdapter.modelFromForm(form);



                modelAdapter.updateModelFromForm(dataUpdateVisit.get(), form);
            }
        }
    }
}
