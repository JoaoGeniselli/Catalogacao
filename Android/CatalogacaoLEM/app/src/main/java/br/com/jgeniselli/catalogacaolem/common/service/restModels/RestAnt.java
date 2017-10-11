package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.login.User;

/**
 * Created by jgeniselli on 20/09/17.
 */

public class RestAnt implements Serializable {

    private String name;
    private String order;
    private String family;
    private String subfamily;
    private String genus;
    private String subgenus;
    private String species;
    private String notes;
    private long collectorId;
    private long dataUpdateId;

    public RestAnt(Ant ant, Long dataUpdateId, Context context) {
        collectorId = User.shared(context).getRegisterId();
        this.dataUpdateId = dataUpdateId;

        name      = ant.getName();
        order     = ant.getAntOrder();
        family    = ant.getAntFamily();
        subfamily = ant.getAntSubfamily();
        genus     = ant.getAntGenus();
        subgenus  = ant.getAntSubgenus();
        species   = ant.getAntSpecies();
        notes     = ant.getNotes();
    }
}
