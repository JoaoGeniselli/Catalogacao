/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model;

import br.com.jgeniselli.catalogacaoWS.model.location.City;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author jgeniselli
 */
public interface AntNestRepository extends CrudRepository<AntNest, Long> {
    
    List<AntNest> findByCity(List<City> cities);
}
