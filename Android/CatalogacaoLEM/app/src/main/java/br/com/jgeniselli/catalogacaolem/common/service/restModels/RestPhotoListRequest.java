package br.com.jgeniselli.catalogacaolem.common.service.restModels;

import android.content.Context;

import java.util.List;

/**
 * Created by jgeniselli on 15/10/17.
 */

public class RestPhotoListRequest extends AuthenticatedRestModel {

    private List<RestPhoto> photos;

    public RestPhotoListRequest(Context context, List<RestPhoto> photos) {
        super(context);
        this.photos = photos;
    }

    public List<RestPhoto> getPhotos() {
        return photos;
    }
}
