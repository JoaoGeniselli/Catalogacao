package br.com.jgeniselli.catalogacaolem.common.form.event;

import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelImageList;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class ImageRemovalRequestEvent {

    private PhotoModel photoModel;

    private FormFieldModelImageList formModel;

    public ImageRemovalRequestEvent(PhotoModel photoModel, FormFieldModelImageList formModel) {
        this.photoModel = photoModel;
        this.formModel = formModel;
    }

    public PhotoModel getPhotoModel() {
        return photoModel;
    }

    public FormFieldModelImageList getFormModel() {
        return formModel;
    }
}
