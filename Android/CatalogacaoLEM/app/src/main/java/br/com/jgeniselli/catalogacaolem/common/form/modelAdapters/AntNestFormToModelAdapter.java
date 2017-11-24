package br.com.jgeniselli.catalogacaolem.common.form.modelAdapters;

import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelCity;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelCoordinate;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelImageList;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelText;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormModel;
import br.com.jgeniselli.catalogacaolem.common.form.model.PlaceholderPhotoModel;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;

/**
 * Created by jgeniselli on 03/09/17.
 */

public class AntNestFormToModelAdapter extends FormToModelAdapter<AntNest> {

    public AntNestFormToModelAdapter() {
    }

    @Override
    public AntNest modelFromForm(FormModel form) {
        AntNest nest = new AntNest();

        FormFieldModelText textField = (FormFieldModelText) form.fieldByTag("name");
        nest.setName(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("vegetation");
        nest.setVegetation(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("address");
        nest.setAddress(textField.getContent());

        FormFieldModelCity cityField = (FormFieldModelCity) form.fieldByTag("city");
        nest.setCity(cityField.getCityModel());

        FormFieldModelCoordinate positionField = (FormFieldModelCoordinate) form.fieldByTag("beginingPoint");
        Coordinate position = new Coordinate();
        position.setLatitude(positionField.getLatitude());
        position.setLongitude(positionField.getLongitude());
        nest.setBeginingPoint(position);

        positionField = (FormFieldModelCoordinate) form.fieldByTag("endingPoint");
        position = new Coordinate();
        position.setLatitude(positionField.getLatitude());
        position.setLongitude(positionField.getLongitude());
        nest.setEndingPoint(position);

        FormFieldModelImageList imagesField = (FormFieldModelImageList) form.fieldByTag("photos");
        for (PlaceholderPhotoModel photo :
                imagesField.getImages()) {

            PhotoModel photoModel = new PhotoModel();

            photoModel.setAntNest(nest);
            photoModel.setDescription(photo.getDescription());
            photoModel.setFilePath(photo.getFilePath());

            nest.getPhotos().add(photoModel);
        }
        return nest;
    }

    @Override
    public void updateModelFromForm(AntNest model, FormModel form) {
        AntNest nest = modelFromForm(form);

        model.setName(nest.getName());
        model.setCity(nest.getCity());
        model.setAddress(nest.getAddress());
        model.setVegetation(nest.getVegetation());
        model.setEndingPoint(nest.getEndingPoint());
        model.setBeginingPoint(nest.getBeginingPoint());
    }
}
