/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author joaog
 */
public interface CoordinateRepository extends CrudRepository<Coordinate, Long> {
    
}
