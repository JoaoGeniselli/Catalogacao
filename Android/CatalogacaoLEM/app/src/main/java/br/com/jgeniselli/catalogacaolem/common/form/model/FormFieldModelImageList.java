package br.com.jgeniselli.catalogacaolem.common.form.model;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;

/**
 * Created by jgeniselli on 03/09/17.
 */

public class FormFieldModelImageList extends FormFieldModel {

    private ArrayList<PhotoModel> images;

    public FormFieldModelImageList(int id, int order, String title, String tag) {
        super(id, order, title, tag);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.IMAGE_LIST;
    }
}
