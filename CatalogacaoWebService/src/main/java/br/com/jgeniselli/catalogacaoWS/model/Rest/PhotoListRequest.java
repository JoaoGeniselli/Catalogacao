/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.Rest;

import java.util.List;

/**
 *
 * @author jgeniselli
 */
public class PhotoListRequest extends AuthenticatedRestModel {
    
    private List<RestPhoto> photos;

    public List<RestPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<RestPhoto> photos) {
        this.photos = photos;
    }
}
