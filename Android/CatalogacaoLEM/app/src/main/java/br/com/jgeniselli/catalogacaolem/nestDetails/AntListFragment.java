package br.com.jgeniselli.catalogacaolem.nestDetails;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.FormActivity_;
import br.com.jgeniselli.catalogacaolem.common.form.FormModel;
import br.com.jgeniselli.catalogacaolem.common.form.SaveFormStrategy;
import br.com.jgeniselli.catalogacaolem.common.form.factory.FormFactoryAnt;
import br.com.jgeniselli.catalogacaolem.common.form.modelAdapters.AntFormToModelAdapter;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import io.realm.Realm;

/**
 * Created by jgeniselli on 09/09/17.
 */

@EFragment(R.layout.fragment_ant_list)
public class AntListFragment extends Fragment {

    DataUpdateVisit dataUpdateVisit;

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    Toolbar toolbar;

    @AfterViews
    public void afterViews() {
        ((Activity)getContext()).setTitle(R.string.title_ants_list);
        ((AppCompatActivity)getContext()).setSupportActionBar(toolbar);

        if (dataUpdateVisit != null && dataUpdateVisit.getAnts() != null) {
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);

            AntsLineAdapter adapter = new AntsLineAdapter(dataUpdateVisit.getAnts());
            recyclerView.setAdapter(adapter);
        }

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.filhadaputa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAntForm(null);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callAntForm(Ant ant) {
        FormModel form = null;
        SaveFormStrategy saveStrategy = null;

        if (ant == null) {
            form = new FormFactoryAnt().getForm();
            long id = dataUpdateVisit.getDataUpdateId();
            saveStrategy = new NewAntSaveFormStrategy(id);
        } else {
            long id = ant.getId();
            saveStrategy = new AntUpdateSaveFormStrategy(id);
            form = new FormFactoryAnt().getForm(ant);
        }

        FormActivity_.intent(getContext())
                .saveStrategy(saveStrategy)
                .form(form)
                .start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAntDetailRequestEvent(AntDetailRequestEvent event) {
        if (event.getAnt() != null) {
            callAntForm(event.getAnt());
        }
    }

    public static class NewAntSaveFormStrategy extends SaveFormStrategy {

        private Long dataUpdateVisitId;

        public NewAntSaveFormStrategy(Long dataUpdateVisitId) {
            this.dataUpdateVisitId = dataUpdateVisitId;
        }

        @Override
        public void save(FormModel form, Realm realmInstance, SaveStrategyCallback completion) {
            Ant ant = new AntFormToModelAdapter().modelFromForm(form);
            DataUpdateVisit dataUpdateVisit = realmInstance
                    .where(DataUpdateVisit.class).equalTo("dataUpdateId", dataUpdateVisitId)
                    .findFirst();

            try {
                Number currentIdNum = realmInstance.where(Ant.class).max("id");
                long nextId = currentIdNum == null ? 1 : currentIdNum.intValue() + 1;
                ant.setId(nextId);
                realmInstance.beginTransaction();
                realmInstance.copyToRealm(ant);
                dataUpdateVisit.getAnts().add(ant);
                realmInstance.commitTransaction();
            }
            catch (Exception e) {
                e.printStackTrace();
                ant = null;
            }
            finally {
                if (completion != null) {
                    if (ant == null) {
                        completion.onSaveError(R.string.error_saving_form);
                    } else {
                        completion.onSaveSuccess();
                    }
                }
            }
        }
    }

    public static class AntUpdateSaveFormStrategy extends SaveFormStrategy {
        private long antId;

        public AntUpdateSaveFormStrategy(long antId) {
            this.antId = antId;
        }

        @Override
        public void save(FormModel form, Realm realmInstance, SaveStrategyCallback completion) {
            Ant ant = realmInstance.where(Ant.class).equalTo("id", antId).findFirst();

            try {
                AntFormToModelAdapter adapter = new AntFormToModelAdapter();

                realmInstance.beginTransaction();
                adapter.updateModelFromForm(ant, form);
                realmInstance.copyToRealmOrUpdate(ant);
                realmInstance.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
                ant = null;
            } finally {
                if (ant == null) {
                    completion.onSaveError(R.string.alert_invalid_ant);
                } else {
                    completion.onSaveSuccess();
                }
            }
        }

        @Override
        public void saveBeforeDestroy(FormModel form, Realm realmInstance) {
            super.saveBeforeDestroy(form, realmInstance);
        }
    }

    public void setDataUpdateVisit(DataUpdateVisit dataUpdateVisit) {
        this.dataUpdateVisit = dataUpdateVisit;
    }
}
