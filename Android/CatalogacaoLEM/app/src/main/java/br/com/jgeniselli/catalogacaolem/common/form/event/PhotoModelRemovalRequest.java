package br.com.jgeniselli.catalogacaolem.common.form.event;

import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class PhotoModelRemovalRequest {

    private PhotoModel photoModel;

    public PhotoModelRemovalRequest(PhotoModel photoModel) {
        this.photoModel = photoModel;
    }

    public PhotoModel getPhotoModel() {
        return photoModel;
    }
}
