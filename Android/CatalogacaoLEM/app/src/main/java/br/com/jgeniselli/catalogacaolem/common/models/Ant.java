package br.com.jgeniselli.catalogacaolem.common.models;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joaog on 12/08/2017.
 */

public class Ant extends RealmObject implements Serializable {

    @PrimaryKey
    private Long id;

    private Long registerId;

    private String name;
    private String antOrder;
    private String antFamily;
    private String antSubfamily;
    private String antGenus;
    private String antSubgenus;
    private String antSpecies;
    private Date registerDate;
    private String notes;

    public Ant() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAntOrder() {
        return antOrder;
    }

    public void setAntOrder(String antOrder) {
        this.antOrder = antOrder;
    }

    public String getAntFamily() {
        return antFamily;
    }

    public void setAntFamily(String antFamily) {
        this.antFamily = antFamily;
    }

    public String getAntSubfamily() {
        return antSubfamily;
    }

    public void setAntSubfamily(String antSubfamily) {
        this.antSubfamily = antSubfamily;
    }

    public String getAntGenus() {
        return antGenus;
    }

    public void setAntGenus(String antGenus) {
        this.antGenus = antGenus;
    }

    public String getAntSubgenus() {
        return antSubgenus;
    }

    public void setAntSubgenus(String antSubgenus) {
        this.antSubgenus = antSubgenus;
    }

    public String getAntSpecies() {
        return antSpecies;
    }

    public void setAntSpecies(String antSpecies) {
        this.antSpecies = antSpecies;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    public void updateFrom(Ant ant) {
        setName(ant.getName());
        setAntOrder(ant.getAntOrder());
        setAntFamily(ant.getAntFamily());
        setAntSubfamily(ant.getAntSubfamily());
        setAntGenus(ant.getAntGenus());
        setAntSubgenus(ant.getAntSubgenus());
        setAntSpecies(ant.getAntSpecies());
        setNotes(ant.getNotes());
    }
}
