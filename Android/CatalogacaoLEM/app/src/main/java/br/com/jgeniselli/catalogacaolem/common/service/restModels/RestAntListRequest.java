package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;

import java.util.List;

/**
 * Created by jgeniselli on 11/10/17.
 */

public class RestAntListRequest extends AuthenticatedRestModel {

    private List<RestAnt> ants;

    public RestAntListRequest(Context context, List<RestAnt> ants) {
        super(context);
        this.ants = ants;
    }

    public List<RestAnt> getAnts() {
        return ants;
    }
}
