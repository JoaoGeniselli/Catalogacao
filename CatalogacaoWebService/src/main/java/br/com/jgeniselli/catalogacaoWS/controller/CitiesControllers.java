package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.AntNest;
import br.com.jgeniselli.catalogacaoWS.model.location.City;
import br.com.jgeniselli.catalogacaoWS.model.location.CityRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.Country;
import br.com.jgeniselli.catalogacaoWS.model.location.CountryRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.State;
import br.com.jgeniselli.catalogacaoWS.model.location.StateRepository;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CitiesControllers extends BaseController {
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @RequestMapping(method=GET, path="/cities")
    public @ResponseBody Iterable<City> cities() {
        return cityRepository.findAll();
    }
    
    @RequestMapping(method=GET, path="/addCity")
    public String addCity(@RequestParam(required=true, name="name")String name) {
        
        
        return name + ": Salva";
    }
    
    @RequestMapping(method=POST, path="/nests")
//    public ArrayList<AntNest> nests(@RequestParam(name = "cities") ArrayList<Spring> cities) {
    public ArrayList<AntNest> nests() {
        ArrayList cities;
        cities = new ArrayList<>();
        ArrayList<AntNest> nests = new ArrayList<>();
        
//        if (cities.contains("Santa Gertrudes")) {
//            nests.add(new AntNest("Ninho 1", "Mata Atlântica", "Santa Gertrudes", "SP", "Brasil"));
//            nests.add(new AntNest("Ninho 2", "Mata Atlântica", "Santa Gertrudes", "SP", "Brasil"));
//        }
//
//        if (cities.contains("Rio Claro")) {
//            nests.add(new AntNest("Ninho 3", "Mata Atlântica", "Rio Claro", "SP", "Brasil"));
//            nests.add(new AntNest("Ninho 4", "Mata Atlântica", "Rio Claro", "SP", "Brasil"));
//        }
//        
//        if (cities.isEmpty()) {
//            nests.add(new AntNest("Ninho 1", "Mata Atlântica", "Santa Gertrudes", "SP", "Brasil"));
//            nests.add(new AntNest("Ninho 2", "Mata Atlântica", "Santa Gertrudes", "SP", "Brasil"));
//            nests.add(new AntNest("Ninho 3", "Mata Atlântica", "Rio Claro", "SP", "Brasil"));
//            nests.add(new AntNest("Ninho 4", "Mata Atlântica", "Rio Claro", "SP", "Brasil"));
//        }       
        return nests;
    }
    
    
//     public String loadCitiesFromJson(HashMap<String, ?> json) { 
//        ArrayList<HashMap> states = (ArrayList) json.get("estados");
//        
//        Country country = new Country((String) json.get("pais"));
//        countryRepository.save(country);
//        
//         for (HashMap stateInfo : states) {
//            State state = new State(country, (String) stateInfo.get("nome"));
//            stateRepository.save(state); 
//            
//             for (Object city : (ArrayList) stateInfo.get("cidades")) {
//                 
//             }
//         }
//
//         return "";
//     }   
//    
            
}
