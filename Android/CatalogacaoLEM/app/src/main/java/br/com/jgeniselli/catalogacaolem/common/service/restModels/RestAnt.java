package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.common.models.Ant;
import br.com.jgeniselli.catalogacaolem.login.User;

/**
 * Created by jgeniselli on 20/09/17.
 */

public class RestAnt {

    private String name;
    private String order;
    private String family;
    private String subfamily;
    private String genus;
    private String subgenus;
    private String species;
    private String notes;
    private long collectorId;

    private WeakReference<Ant> antWeakReference;

    public RestAnt(Ant ant, Context context) {
        collectorId = User.shared(context).getRegisterId();

        name      = ant.getName();
        order     = ant.getAntOrder();
        family    = ant.getAntFamily();
        subfamily = ant.getAntSubfamily();
        genus     = ant.getAntGenus();
        subgenus  = ant.getAntSubgenus();
        species   = ant.getAntSpecies();
        notes     = ant.getNotes();

        antWeakReference = new WeakReference<>(ant);
    }

    public Ant getAntReference() {
        if (antWeakReference != null) {
            return antWeakReference.get();
        }
        return null;
    }
}
