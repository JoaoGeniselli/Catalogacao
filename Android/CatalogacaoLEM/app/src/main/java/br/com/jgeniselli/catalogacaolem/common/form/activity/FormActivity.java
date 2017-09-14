package br.com.jgeniselli.catalogacaolem.common.form.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormModel;
import br.com.jgeniselli.catalogacaolem.common.form.model.SaveFormStrategy;

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
