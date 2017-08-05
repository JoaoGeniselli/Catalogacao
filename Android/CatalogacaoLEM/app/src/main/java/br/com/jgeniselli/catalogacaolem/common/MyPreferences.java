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
}
