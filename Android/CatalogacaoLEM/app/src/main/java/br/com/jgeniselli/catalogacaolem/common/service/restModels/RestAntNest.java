package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;

import java.util.Date;
import java.util.HashMap;

import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.login.User;

/**
 * Created by jgeniselli on 19/09/17.
 */

public class RestAntNest extends AuthenticatedRestModel {

    private Long nestId;
    private Long cityId;
    private String name;
    private Long collectorId;
    private String vegetation;
    private String address;
    private HashMap beginingPoint;
    private HashMap endingPoint;
    private Date lastDataUpdateDate;
    private Long registerId;

    public RestAntNest(Context context) {
        super(context);
    }

    public RestAntNest(AntNest nest, Context context) {
        super(context);
        nestId = nest.getNestId();
        cityId = nest.getCity().getId();
        name = nest.getName();
        vegetation = nest.getVegetation();
        address = nest.getAddress();

        beginingPoint = nest.getBeginingPoint().toHashMap();
        endingPoint = nest.getEndingPoint().toHashMap();

        collectorId = User.shared(context).getRegisterId();

        lastDataUpdateDate = nest.getLastDataUpdateDate();
    }

    public Long getNestId() {
        return nestId;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public Long getCollectorId() {
        return collectorId;
    }

    public String getVegetation() {
        return vegetation;
    }

    public String getAddress() {
        return address;
    }

    public HashMap getBeginingPoint() {
        return beginingPoint;
    }

    public HashMap getEndingPoint() {
        return endingPoint;
    }

    public Date getLastDataUpdateDate() {
        return lastDataUpdateDate;
    }

    public Long getRegisterId() {
        return registerId;
    }


}
