/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author jgeniselli
 */
@Entity
public class Role {
	@Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String role;
	
	public Long getId() {
            return id;
	}
	public void setId(long id) {
            this.id = id;
	}
	public String getRole() {
            return role;
	}
	public void setRole(String role) {
            this.role = role;
	}
}