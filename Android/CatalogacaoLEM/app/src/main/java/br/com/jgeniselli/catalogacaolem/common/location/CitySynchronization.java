package br.com.jgeniselli.catalogacaolem.common.location;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by jgeniselli on 21/08/17.
 */

public class CitySynchronization extends RealmObject {

    private CityModel city;
    private Date lastSynchronization;

}
