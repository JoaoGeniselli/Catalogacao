/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.controller;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author joaog
 */
class ImageStorageService {
    void init() {
        
    }

    void store(MultipartFile file){
        
    }

    Stream<Path> loadAll() {
       return null; 
    }

    Path load(String filename) {
       return null; 
    }

    File loadAsResource(String filename) {
        return null;
    }

    void deleteAll() {
        
    }
    
}
