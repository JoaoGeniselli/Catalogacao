package br.com.jgeniselli.catalogacaoWS.controller;

import br.com.jgeniselli.catalogacaoWS.model.location.City;
import br.com.jgeniselli.catalogacaoWS.model.location.CityRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.Country;
import br.com.jgeniselli.catalogacaoWS.model.location.CountryRepository;
import br.com.jgeniselli.catalogacaoWS.model.location.CountryState;
import br.com.jgeniselli.catalogacaoWS.model.location.CountryStateRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController extends BaseAPIController {
    
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryStateRepository stateRepository;
    
    @Autowired
    private CountryRepository countryRepository;
        
    @RequestMapping(method=GET, path="/addCity")
    public String addCity(@RequestParam(required=true, name="name")String name) {
        City city = new City();
        city.setName(name);
//        cityRepository.save(city);
        return name + ": Salva";
    }
    
    @RequestMapping(method=GET, path="/locationContent")
    public List<Country> locationContent() {
        List<Country> countries = (List<Country>) countryRepository.findAll();
        return countries;
    }
    
    @RequestMapping(method=POST,path="/addCountryInfo")
    public String addCountryInfo(@RequestBody HashMap<String, ?> json) { 
        ArrayList<HashMap> states = (ArrayList) json.get("estados");        
        
        Country country = new Country((String) json.get("pais"));
        countryRepository.save(country);

        HashSet<CountryState> countryStates = new HashSet();
        
        for(HashMap stateInfo : states) {
            CountryState state = new CountryState(country, (String) stateInfo.get("nome"));
            state.setInitials((String) stateInfo.get("sigla"));
            state.setCountry(country);
            stateRepository.save(state);

            countryStates.add(state);
            
            HashSet<City> citiesSet = new HashSet();
            ArrayList<String> citiesNames = (ArrayList<String>)stateInfo.get("cidades");
            for (String cityName : citiesNames) {
                City city = new City();
                city.setName(cityName);
                city.setState(state);
                citiesSet.add(city);
                cityRepository.save(city);
            }
            
            state.setCities(citiesSet);
            stateRepository.save(state);
        }
        countryRepository.save(country);
        return "Carga de cidades salva";
    }        
}
