package br.com.jgeniselli.catalogacaolem.common.form.model;

import java.io.Serializable;

import br.com.jgeniselli.catalogacaolem.common.form.model.FormModel;
import io.realm.Realm;

/**
 * Created by jgeniselli on 03/09/17.
 */

public abstract class SaveFormStrategy implements Serializable {

    public abstract void save(FormModel form, Realm realmInstance, SaveStrategyCallback completion);

    public void saveBeforeDestroy(FormModel form, Realm realmInstance){

    }

    public abstract static class SaveStrategyCallback implements Serializable {

        public SaveStrategyCallback() {}

        public abstract void onSaveSuccess();
        public abstract void onSaveError(int messageId);
    }
}
