package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.login.User;

/**
 * Created by jgeniselli on 19/09/17.
 */

public class RestDataUpdateVisit extends AuthenticatedRestModel {

    private long nestId;
    private String collectionDate;
    private long collectorId;

    private HashMap beginingPoint;
    private HashMap endingPoint;

    private String notes;

    public RestDataUpdateVisit(DataUpdateVisit dataUpdateVisit, Context context) {
        super(context);

        nestId = dataUpdateVisit.getNest().getNestId();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (dataUpdateVisit.getCollectionDate() != null) {
            collectionDate = dateFormat.format(dataUpdateVisit.getCollectionDate());
        } else {
            collectionDate = dateFormat.format(new Date());
        }
        collectorId = User.shared(context).getRegisterId();

        if (dataUpdateVisit.getNewBeginingPoint() != null) {
            beginingPoint = dataUpdateVisit.getNewBeginingPoint().toHashMap();
        }
        if (dataUpdateVisit.getNewEndingPoint() != null) {
            endingPoint = dataUpdateVisit.getNewEndingPoint().toHashMap();
        }

        notes = dataUpdateVisit.getNotes();
    }
}
