package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;

import java.util.HashMap;

import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.login.User;

/**
 * Created by jgeniselli on 19/09/17.
 */

public class RestAntNest extends AuthenticatedRestModel {

    private Long cityId;
    private String name;
    private Long collectorId;
    private String vegetation;
    private String address;
    private HashMap beginingPoint;
    private HashMap endingPoint;

    public RestAntNest(AntNest nest, Context context) {
        super(context);
        cityId = nest.getCity().getId();
        name = nest.getName();
        vegetation = nest.getVegetation();
        address = nest.getAddress();

        beginingPoint = nest.getBeginingPoint().toHashMap();
        endingPoint = nest.getEndingPoint().toHashMap();

        collectorId = User.shared(context).getRegisterId();
    }
}
