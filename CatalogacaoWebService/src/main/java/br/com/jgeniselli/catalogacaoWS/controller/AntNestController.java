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
import br.com.jgeniselli.catalogacaoWS.model.PhotoRepository;
import br.com.jgeniselli.catalogacaoWS.model.Rest.AntListRequest;
import br.com.jgeniselli.catalogacaoWS.model.Rest.CitiesListRequest;
import br.com.jgeniselli.catalogacaoWS.model.Rest.IndexAnswer;
import br.com.jgeniselli.catalogacaoWS.model.Rest.PhotoListRequest;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestAnt;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestAntNest;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestDataUpdateVisit;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestPhoto;
import br.com.jgeniselli.catalogacaoWS.model.Rest.RestResponseAntNest;
import br.com.jgeniselli.catalogacaoWS.model.User;
import br.com.jgeniselli.catalogacaoWS.model.UserRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.City;
import br.com.jgeniselli.catalogacaoWS.model.location.CityRepository;
import br.com.jgeniselli.catalogacaoWS.util.ImageUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author joaog
 */
@RestController
public class AntNestController extends BaseAPIController {
    
    public static final String IMAGES_PATH = "~/Desktop/images/";
    
    @Autowired
    private AntNestRepository nestRepository;
    
    @Autowired
    private AntRepository antRepository;
    
    @Autowired
    private DataUpdateVisitRepository dataUpdateVisitRepository;
    
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;
    
    @Autowired
    private PhotoRepository photoRepository;

    @Value("${server.imagesPath}")
    private String imagesPath;

    @RequestMapping(method=POST, path="/addNewNest")
    public ResponseEntity<?> addNewNest(@RequestBody RestAntNest nestInfo) {

        User user;
        try {
            user = getUser(nestInfo.getCollectorId());
            validateTokenString(nestInfo);
        } catch (InvalidUserException | InvalidTokenException e) {
            String message = e.getMessage();
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
            user = getUser(dataUpdateInfo.getCollectorId());
            validateTokenString(dataUpdateInfo);
        } catch (InvalidUserException | InvalidTokenException e) {
            String message = e.getMessage();
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
    public ResponseEntity<?> addAnts(@RequestBody AntListRequest request) {
        
        try {
            validateTokenString(request);
        } catch (InvalidTokenException ex) {
            String message = ex.getMessage();
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        ArrayList<IndexAnswer> indexAnswers = new ArrayList<>();
        for(int i = 0; i < request.getAnts().size(); i++) {
            RestAnt antInfo = request.getAnts().get(i);
            
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
        return new ResponseEntity<>(indexAnswers, HttpStatus.CREATED);
    }

    @RequestMapping(method=POST, path="/nestsByCities")
    public ResponseEntity<?> nestsByCities(@RequestBody CitiesListRequest body) {
        
        try {
            validateTokenString(body);
        } catch (InvalidTokenException ex) {
            String message = ex.getMessage();
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        ArrayList<Long> cities = (ArrayList<Long>) body.cities;
        
        if (cities == null) {
            return new ResponseEntity<>(new ArrayList(), HttpStatus.OK);
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
        return new ResponseEntity<>(responseAntNests, HttpStatus.OK);
    }

    @RequestMapping(path = "/addPhotos")
    public ResponseEntity<?> addPhotos(@RequestBody PhotoListRequest request) {
        
        try {
            validateTokenString(request);
        } catch (InvalidTokenException ex) {
            String message = ex.getMessage();
            HashMap<String, String> map = new HashMap<>();
            map.put("msg", message);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        ArrayList<IndexAnswer> indexAnswers = new ArrayList<>();
        ResponseEntity response;
        
    	if (request.getPhotos() != null && request.getPhotos().size() > 0) {
            Photo photo = null;

            for(int i =0 ;i< request.getPhotos().size(); i++){
                RestPhoto photoInfo = request.getPhotos().get(i);
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
                
                indexAnswers.add(answer);
            }
            response = new ResponseEntity(indexAnswers, HttpStatus.OK);
        } else {
            response = new ResponseEntity("Lista vazia", HttpStatus.OK);
        }
        return response;
    }

    private IndexAnswer addPhotoToAnt(RestPhoto restPhoto) {
        Ant ant;
        Photo photo;
        IndexAnswer answer = null;
        try {
            ant = antRepository.findOne(restPhoto.getAntId());
            if (ant == null) {
                throw new Exception();
            }
            
            String imageName = ImageUtil.generateImageFilename("ant-", null);
            String filepath = imagesPath + imageName;
            ImageUtil.saveImageFromBase64(restPhoto.getBase64Photo(), filepath);

            photo = new Photo();
            photo.setDescription(restPhoto.getDescription());
            photo.setRegisterDate(new Date());
            photo.setFilepath(imageName);

            photoRepository.save(photo);
            
            ant.addPhoto(photo);
            antRepository.save(ant);
            
            answer = new IndexAnswer(photo.getId(), "Imagem salva com sucesso");
        }
        catch(Exception e) {
            answer = new IndexAnswer(Long.MIN_VALUE, "Erro ao salvar imagem");
        } 
        return answer;
    }
    
    private IndexAnswer addPhotoToNest(RestPhoto restPhoto) {
        AntNest antNest;
        Photo photo;
        IndexAnswer answer = null;
        try {
            antNest = nestRepository.findOne(restPhoto.getNestId());
            if (antNest == null) {
                throw new Exception();
            }
            
            String imageName = ImageUtil.generateImageFilename("nest-", null);
            String filepath = imagesPath + imageName;
            ImageUtil.saveImageFromBase64(restPhoto.getBase64Photo(), filepath);

            photo = new Photo();
            photo.setDescription(restPhoto.getDescription());
            photo.setRegisterDate(new Date());
            photo.setFilepath(imageName);

            photoRepository.save(photo);
            
            antNest.addPhoto(photo);
            nestRepository.save(antNest);
            
            answer = new IndexAnswer(photo.getId(), "Imagem salva com sucesso");
        }
        catch(Exception e) {
            answer = new IndexAnswer(Long.MIN_VALUE, "Erro ao salvar imagem");
        } 
        return answer;
    }
    
    private IndexAnswer addPhotoToDataUpdate(RestPhoto restPhoto) {
        DataUpdateVisit dataUpdate;
        Photo photo;
        IndexAnswer answer = null;
        try {
            dataUpdate = dataUpdateVisitRepository.findOne(restPhoto.getDataUpdateId());
            if (dataUpdate == null) {
                throw new Exception();
            }
            
            String imageName = ImageUtil.generateImageFilename("data-update-", null);
            String filepath = imagesPath + imageName;
            ImageUtil.saveImageFromBase64(restPhoto.getBase64Photo(), filepath);

            photo = new Photo();
            photo.setDescription(restPhoto.getDescription());
            photo.setRegisterDate(new Date());
            photo.setFilepath(imageName);

            photoRepository.save(photo);
            
            dataUpdate.addPhoto(photo);
            dataUpdateVisitRepository.save(dataUpdate);
            
            answer = new IndexAnswer(photo.getId(), "Imagem salva com sucesso");
        }
        catch(Exception e) {
            answer = new IndexAnswer(Long.MIN_VALUE, "Erro ao salvar imagem");
        } 
        return answer;
    }
}
