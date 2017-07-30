/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisit;
import java.util.Date;
import java.util.List;

/**
 *
 * @author joaog
 */
public class RestAnt {
    
    private Integer id;
    private String name; // texto

    private String antOrder; // texto
    private String antFamily; // texto
    private String antSubfamily; // texto
    private String antGenus; // texto
    private String antSubgenus; // texto
    private String antSpecies; // texto
    private DataUpdateVisit visit;
    private RestAntNest antNest;
    private List<RestPhoto> photos;
    private Date registerDate;
    
}
