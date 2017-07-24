/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author joaog
 */
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByUserId(String userId);
}
