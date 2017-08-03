/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.Ant;
import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.AntNestRepository;
import br.com.jgeniselli.catalogacaoWS.model.CoordinateRepository;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisit;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisitRepository;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestAntNest;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestDataUpdateVisit;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.City;
import br.com.jgeniselli.catalogacaoWS.model.location.CityRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CoordinateRepository coordinateRepository;
    
    @RequestMapping(method=POST, path="/addNewNest")
    public ResponseEntity<?> addNewNest(@RequestBody RestAntNest nestInfo) {
        
        User user;
        try {
            user = userRepository.findOne(nestInfo.getCollectorId());
            
            if (user == null || !user.getToken().equals(nestInfo.getToken())) {
                throw new Exception();
            }
        } catch (Exception e) {
            String message = "Operação não autorizada";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        City city;
        try {
            city = cityRepository.findOne(nestInfo.getCityId());
            if (city == null) {
                throw new Exception();
            }
        } catch(Exception e) {
            String message = "Cidade inválida";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        if (nestInfo.getBeginingPoint() == null || nestInfo.getEndingPoint() == null) {
            String message = "Localização inválida";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        AntNest nest = new AntNest();

        nest.setCity(city);
        nest.setName(nestInfo.getName());
        nest.setVegetation(nestInfo.getVegetation());
        
        nestRepository.save(nest);
        coordinateRepository.save(nestInfo.getBeginingPoint());
        coordinateRepository.save(nestInfo.getEndingPoint());
        
        DataUpdateVisit dataUpdate = new DataUpdateVisit();
        
        dataUpdate.setCollector(user);
        dataUpdate.setNewBeginingPoint(nestInfo.getBeginingPoint());
        dataUpdate.setNewEndingPoint(nestInfo.getEndingPoint());
        
        dataUpdate.setNotes("--- REGISTRO DO NINHO ---");
        dataUpdate.setNest(nest);
        dataUpdate.setCollectionDate(new Date());
        dataUpdate.setRegisterDate(new Date());
        
        dataUpdateVisitRepository.save(dataUpdate);
        
        HashSet set = new HashSet();
        set.add(dataUpdate);
        nest.setDataUpdateVisits(set);
        nest.setRegisterDate(new Date());
        nestRepository.save(nest);
        
        String message = "Ninho salvo com sucesso";
        HashMap<String, String> map = new HashMap<>();
        map.put("msg", message);
        map.put("id", nest.getNestId().toString());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @RequestMapping(method = POST, path = "/addDataUpdate")
    public ResponseEntity<?> addDataUpdateToNest(@RequestBody RestDataUpdateVisit dataUpdateInfo) {
        User user;
        try {
            user = userRepository.findOne(dataUpdateInfo.getCollectorId());
            if (user == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            String message = "Operação não autorizada";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        
        AntNest nest;
        try {
            nest = nestRepository.findOne(dataUpdateInfo.getNestId());
            if (nest == null) {
                throw new Exception();
            }
        } catch(Exception e) {
            String message = "O ninho especificado não existe";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        if (dataUpdateInfo.getBeginingPoint() == null || dataUpdateInfo.getEndingPoint() == null) {
            String message = "Localização inválida";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        
        coordinateRepository.save(dataUpdateInfo.getBeginingPoint());
        coordinateRepository.save(dataUpdateInfo.getEndingPoint());
        
        DataUpdateVisit dataUpdate = new DataUpdateVisit();
        
        dataUpdate.setNest(nest);
        dataUpdate.setCollector(user);
        dataUpdate.setNewBeginingPoint(dataUpdateInfo.getBeginingPoint());
        dataUpdate.setNewEndingPoint(dataUpdateInfo.getEndingPoint());
        dataUpdate.setNotes(dataUpdateInfo.getNotes());
        
        if (dataUpdateInfo.getCollectionDate() == null) {
            dataUpdate.setCollectionDate(new Date());
        } else {
            dataUpdate.setCollectionDate(dataUpdateInfo.getCollectionDate());
        }
        dataUpdate.setRegisterDate(new Date());
        dataUpdateVisitRepository.save(dataUpdate);
        
        nest.getDataUpdateVisits().add(dataUpdate);        
        nestRepository.save(nest);

        HashSet set = new HashSet();
        set.add(dataUpdate);
        nest.setDataUpdateVisits(set);
        nest.setRegisterDate(new Date());
        nestRepository.save(nest);
        
        String message = "Ninho salvo com sucesso";
        HashMap<String, String> map = new HashMap<>();
        map.put("msg", message);
        map.put("id", nest.getNestId().toString());
        return new ResponseEntity<>(map, HttpStatus.OK);
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
