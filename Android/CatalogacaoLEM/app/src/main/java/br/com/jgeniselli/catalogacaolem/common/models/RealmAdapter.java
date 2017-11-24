package br.com.jgeniselli.catalogacaolem.common.models;

import io.realm.Realm;

/**
 * Created by jgeniselli on 19/11/2017.
 */

public interface RealmAdapter<S, T> {
    S adapt(T object, Realm realm);
    T reverse(S object, Realm realm);
}
