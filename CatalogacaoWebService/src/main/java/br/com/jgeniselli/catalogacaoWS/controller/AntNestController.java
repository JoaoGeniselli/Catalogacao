/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.Ant;
import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.AntNestRepository;
import br.com.jgeniselli.catalogacaoWS.model.AntRepository;
import br.com.jgeniselli.catalogacaoWS.model.CoordinateRepository;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisit;
import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisitRepository;
import br.com.jgeniselli.catalogacaoWS.model.Photo;
import br.com.jgeniselli.catalogacaoWS.model.Rest.CitiesListRequest;
import br.com.jgeniselli.catalogacaoWS.model.Rest.IndexAnswer;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestAnt;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestAntNest;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestDataUpdateVisit;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestPhoto;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestResponseAntNest;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.City;
import br.com.jgeniselli.catalogacaoWS.model.location.CityRepository;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author joaog
 */
@RestController
public class AntNestController extends BaseController {
    
    @Autowired
    private AntNestRepository nestRepository;
    
    @Autowired
    private AntRepository antRepository;
    
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
            
            if (user == null) {
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

        if (nestInfo.getBeginingPoint() == null ||
                nestInfo.getEndingPoint() == null) {
            String message = "Localização inválida";
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        AntNest nest = new AntNest();

        nest.setCity(city);
        nest.setName(nestInfo.getName());
        nest.setVegetation(nestInfo.getVegetation());
        nest.setAddress(nestInfo.getAddress());
        
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

        if (dataUpdateInfo.getBeginingPoint() == null || 
                dataUpdateInfo.getEndingPoint() == null) {
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

        String message = "Ninho salvo com sucesso";
        HashMap<String, String> map = new HashMap<>();
        map.put("msg", message);
        map.put("id", nest.getNestId().toString());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @RequestMapping(method = POST, path = "/addAnts")
    public List<IndexAnswer> addAnts(@RequestBody List<RestAnt> antInfos) {

        ArrayList<IndexAnswer> indexAnswers = new ArrayList<>();
        for(int i = 0; i < antInfos.size(); i++) {
            RestAnt antInfo = antInfos.get(i);
            
            DataUpdateVisit dataUpdate = null;
            try {
                dataUpdate = dataUpdateVisitRepository.findOne(antInfo.getDataUpdateId());
                if (dataUpdate == null) {
                   throw new Exception();
                }
            } catch (Exception e) {
                String message = "Atualização de dados inválida";
                IndexAnswer error = new IndexAnswer(null, message);
                indexAnswers.add(error);
            } finally {
                if (dataUpdate != null) {
                    Ant ant = new Ant();
                    
                    ant.setName(antInfo.getName());
                    ant.setVisit(dataUpdate);
                    ant.setAntFamily(antInfo.getFamily());
                    ant.setAntOrder(antInfo.getOrder());
                    ant.setAntSubfamily(antInfo.getSubfamily());
                    ant.setAntGenus(antInfo.getGenus());
                    ant.setAntSubgenus(antInfo.getSubgenus());
                    ant.setAntSpecies(antInfo.getSpecies());
                    ant.setAntNest(dataUpdate.getNest());
                    
                    ant.setRegisterDate(new Date());
                    
                    antRepository.save(ant);

                    dataUpdate.getAnts().add(ant);
                    dataUpdateVisitRepository.save(dataUpdate);
                    
                    dataUpdate.getNest().getAnts().add(ant);
                    nestRepository.save(dataUpdate.getNest());
                    
                    indexAnswers.add(new IndexAnswer(ant.getId(), "Salvo com sucesso"));
                }
            }
        }
        return indexAnswers;
    }

    @RequestMapping(method=POST, path="/nestsByCities")
    public ArrayList<RestResponseAntNest> nestsByCities(
            @RequestBody CitiesListRequest body) {
        
        ArrayList<Long> cities = (ArrayList<Long>) body.cities;
        
        if (cities == null) {
            return new ArrayList<>();
        }
        
        ArrayList<AntNest> nests = (ArrayList<AntNest>) nestRepository
                .findByCityIdIn(cities);
        if (nests == null) {
            nests = new ArrayList<>();
        }
        
        ArrayList<RestResponseAntNest> responseAntNests;
        responseAntNests = new ArrayList<>();
        for (AntNest nest : nests) {
            responseAntNests.add(new RestResponseAntNest(nest));
        }
        return responseAntNests;
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
    
    @RequestMapping
    public ResponseEntity<?> addPhotos(@RequestBody List<RestPhoto> photos) {
        ArrayList<IndexAnswer> indexAnswers = new ArrayList<>();
        
    	if (photos != null && photos.size() >0) {
            Photo photo = null;

            for(int i =0 ;i< photos.size(); i++){
                RestPhoto photoInfo = photos.get(i);
                IndexAnswer answer = null;
                
                if (photoInfo.getAntId() != null) {
                    answer = addPhotoToAnt(photoInfo);
                } 
                else if (photoInfo.getNestId() != null) {
                    answer = addPhotoToNest(photoInfo);
                }
                else if (photoInfo.getDataUpdateId() != null) {
                    answer = addPhotoToDataUpdate(photoInfo);
                }
                else {
                    
                }
            }
        } else {
            return null;
        }
        return null;
    }

    private IndexAnswer addPhotoToAnt(RestPhoto photo) {
        Ant ant = null;
//        Photo photo = null;
        IndexAnswer answer = null;
        try {
            ant = antRepository.findOne(photo.getAntId());
            if (ant == null) {
                throw new Exception();
            }
            
            
           
        }
        catch(Exception e) {
            answer = new IndexAnswer(Long.MIN_VALUE, "Formiga inválida");
        } 
        finally {
            return answer;
        }
    }
    
    private IndexAnswer addPhotoToNest(RestPhoto photo) {
        return null;
    }
    
    private IndexAnswer addPhotoToDataUpdate(RestPhoto photo) {
        return null;
    }
    
    private boolean saveToDirectoryMultipartImage(RestPhoto photoInfo, String filename) {
        boolean success = false;
        try {
            byte[] bytes = photoInfo.getImageFile().getBytes();
            BufferedOutputStream buffStream = 
                    new BufferedOutputStream(new FileOutputStream(new File("C:/catalogacaoLEM/img/" + filename)));
            buffStream.write(bytes);
            buffStream.close();
            success = true;
        } 
        catch (Exception e) {
            success = false;
        } 
        finally {
            return success;
        }
    }
}
