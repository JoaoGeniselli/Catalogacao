package br.com.jgeniselli.catalogacaoWS.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by joaog on 26/05/2017.
 */

@Entity
public class AntNest implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer nestId;
    
//    @ManyToOne
//    private City city;
    
    private String name;
    private String vegetation;
    
//    private Date lastVisitDate;

//    public Coordinate lastBeginingPoint; // coordenadas
//    public Coordinate lastEndingPoint; // coordenadas

    public AntNest() {
        
    }
}
