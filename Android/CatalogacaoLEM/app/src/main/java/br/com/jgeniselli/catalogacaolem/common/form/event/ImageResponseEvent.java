package br.com.jgeniselli.catalogacaolem.common.form.event;

import br.com.jgeniselli.catalogacaolem.common.form.model.PlaceholderPhotoModel;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class ImageResponseEvent {

    private PlaceholderPhotoModel photoModel;

    public ImageResponseEvent(PlaceholderPhotoModel photoModel) {
        this.photoModel = photoModel;
    }

    public PlaceholderPhotoModel getPhotoModel() {
        return photoModel;
    }
}
