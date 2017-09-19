package br.com.jgeniselli.catalogacaolem.common;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by joaog on 05/08/2017.
 */

@SharedPref
public interface MyPreferences {

    @DefaultString("")
    String name();

    @DefaultString("")
    String token();

    @DefaultString("")
    String lastCitiesSynchronization();

    @DefaultString("")
    String user_name();

    @DefaultString("")
    String user_id();

    @DefaultString("")
    String user_token();
}
