package br.com.jgeniselli.catalogacaolem.common;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by joaog on 05/08/2017.
 */

public class CatalogacaoLEMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
