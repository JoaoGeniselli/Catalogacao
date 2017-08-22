package br.com.jgeniselli.catalogacaolem.common.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.jgeniselli.catalogacaolem.R;

@EActivity(R.layout.activity_form)
public class FormActivity extends AppCompatActivity {

    @ViewById
    RecyclerView formRecycler;

    @AfterViews
    public void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        formRecycler.setLayoutManager(layoutManager);


    }




}
