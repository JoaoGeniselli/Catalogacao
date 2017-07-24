package br.com.jgeniselli.catalogacaoWS.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by joaog on 26/05/2017.
 */
@Entity
public class Ant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String name; // texto

    // Taxonomy
    private String antOrder; // texto
    private String antFamily; // texto
    private String antSubfamily; // texto
    private String antGenus; // texto
    private String antSubgenus; // texto
    private String antSpecies; // texto

    @ManyToOne
    private DataUpdateVisit visit;

    public Ant() {

    }
    
    public DataUpdateVisit getVisit() {
        return visit;
    }

    public void setVisit(DataUpdateVisit visit) {
        this.visit = visit;
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
    
    
}
