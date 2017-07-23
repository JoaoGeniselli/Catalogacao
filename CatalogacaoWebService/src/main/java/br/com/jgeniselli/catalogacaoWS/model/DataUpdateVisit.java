package br.com.jgeniselli.catalogacaoWS.model;

import java.util.Date;

/**
 * Created by joaog on 26/05/2017.
 */

public class DataUpdateVisit {

    public String collectorName; // texto
    private Date collectionDate; // data

    private Coordinate newBeginingPoint;
    private Coordinate newEndingPoint;

    public String observation;
    public AntNest nest;

}
