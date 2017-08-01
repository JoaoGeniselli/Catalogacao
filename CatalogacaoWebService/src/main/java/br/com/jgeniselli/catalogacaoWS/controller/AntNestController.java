/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.Ant;
import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.AntNestRepository;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisit;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisitRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.City;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author joaog
 */
@RestController
public class AntNestController extends BaseController {
    
    @Autowired
    private AntNestRepository nestRepository;
    
    @Autowired
    private DataUpdateVisitRepository dataUpdateVisitRepository;
    
    @RequestMapping(method=POST, path="/addNewNest")
    public String addNewNest(@RequestBody AntNest nest) {
        nest.setRegisterDate(new Date());
        nestRepository.save(nest);
        return nest.getName() + ": Salvo com sucesso";
    }
    
    @RequestMapping(method=GET, path="/nestsByCities")
    public ArrayList<AntNest> nestsByCities(
            @RequestParam(name = "cities") ArrayList<Integer> cities) {
        ArrayList<AntNest> nests = (ArrayList<AntNest>) nestRepository.findByCityIdIn(cities);
        return nests;
    }
    
    @RequestMapping(method=POST, path="/addNewDataUpdateVisit")
    public String addNewDataUpdateVisit(@RequestBody DataUpdateVisit visit) {
        visit.setRegisterDate(new Date());
        
        for(Ant ant : visit.getAnts()) {
            ant.setVisit(visit);
        }        
        dataUpdateVisitRepository.save(visit);

        return "Salvo com sucesso";
    }

    @RequestMapping(method=GET, path="/allNests")
    public ArrayList<AntNest> allNests () {
        ArrayList<AntNest> nests = (ArrayList<AntNest>) nestRepository.findAll();
        return nests;
    }
    
    @RequestMapping(method=GET, path="/allDataUpdates")
    public ArrayList<DataUpdateVisit> allDataUpdates() {
        ArrayList<DataUpdateVisit> dataUpdates = (ArrayList<DataUpdateVisit>) dataUpdateVisitRepository.findAll();
        return dataUpdates;
    }
}
