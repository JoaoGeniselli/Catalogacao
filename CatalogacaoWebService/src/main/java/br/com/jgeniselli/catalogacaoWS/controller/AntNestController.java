/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.AntNestRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.City;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @RequestMapping(method=POST, path="/addNewNest")
    public String addNewNest(AntNest nest) {
        nestRepository.save(nest);
        return nest.getName() + ": Salvo com sucesso";
    }
    
    @RequestMapping(method=POST, path="/nestsByCities")
    public ArrayList<AntNest> nestsByCities(
            @RequestParam(name = "cities") ArrayList<City> cities) {
        ArrayList<AntNest> nests = (ArrayList<AntNest>) nestRepository.findByCity(cities);
        return nests;
    }
    
    
    
    
}
