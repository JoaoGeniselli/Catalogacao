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
 * @author jgeniselli
 */
public interface MobileTokenRepository extends CrudRepository<MobileToken, Long> {
    List<MobileToken> findByToken(String token);
}
