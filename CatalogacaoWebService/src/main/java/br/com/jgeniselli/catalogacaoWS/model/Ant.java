package br.com.jgeniselli.catalogacaoWS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 * Created by joaog on 26/05/2017.
 */
@Entity
public class Ant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name; // texto

    // Taxonomy
    private String antOrder; // texto
    private String antFamily; // texto
    private String antSubfamily; // texto
    private String antGenus; // texto
    private String antSubgenus; // texto
    private String antSpecies; // texto

    @ManyToOne
    @JsonIgnore
    private DataUpdateVisit visit;

    @ManyToOne
    @JsonIgnore
    private AntNest antNest;
    
    @OneToMany
    private List<Photo> photos;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date registerDate;

    public Ant() {

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

    public DataUpdateVisit getVisit() {
        return visit;
    }

    public void setVisit(DataUpdateVisit visit) {
        this.visit = visit;
    }

    public AntNest getAntNest() {
        return antNest;
    }

    public void setAntNest(AntNest antNest) {
        this.antNest = antNest;
    }
    
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public Long getId() {
        return id;
    }
    
    

}
