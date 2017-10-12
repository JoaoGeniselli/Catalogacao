package br.com.jgeniselli.catalogacaoWS.model;

import br.com.jgeniselli.catalogacaoWS.model.annotation.FormIgnore;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormLabel;
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
    @FormIgnore
    private Long id;
    
    @FormLabel("Nome")
    private String name;

    // Taxonomy
    @FormLabel("Ordem")
    private String antOrder; 
    @FormLabel("Família")
    private String antFamily;
    @FormLabel("Sub-Família")
    private String antSubfamily;
    @FormLabel("Gênero")
    private String antGenus;
    @FormLabel("Sub-Gênero")
    private String antSubgenus;
    @FormLabel("Espécie")
    private String antSpecies;

    @ManyToOne
    @JsonIgnore
    @FormIgnore
    private DataUpdateVisit visit;

    @ManyToOne
    @JsonIgnore
    @FormIgnore
    private AntNest antNest;
    
    @OneToMany
    @FormIgnore
    private List<Photo> photos;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @FormIgnore
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

    public List<Photo> getPhotos() {
        return photos;
    }
}
