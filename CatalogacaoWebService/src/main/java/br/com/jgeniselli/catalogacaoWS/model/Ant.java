package br.com.jgeniselli.catalogacaoWS.model;

/**
 * Created by joaog on 26/05/2017.
 */

public class Ant {
    private Integer id;

    public String name; // texto
    public String order; // texto
    public String family; // texto
    public String subfamily; // texto
    public String genus; // texto
    public String subgenus; // texto
    public String species; // texto

    private AntNest nest;
    private DataUpdateVisit visit;

    public Ant() {

    }

    public AntNest getNest() {
        return nest;
    }

    public void setNest(AntNest nest) {
        this.nest = nest;
    }

    public DataUpdateVisit getVisit() {
        return visit;
    }

    public void setVisit(DataUpdateVisit visit) {
        this.visit = visit;
    }
}
