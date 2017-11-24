package br.com.jgeniselli.catalogacaolem.common.form.modelAdapters;

import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelCoordinate;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelImageList;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelText;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormModel;
import br.com.jgeniselli.catalogacaolem.common.form.model.PlaceholderPhotoModel;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModelRepository;
import io.realm.RealmList;

/**
 * Created by jgeniselli on 09/09/17.
 */

public class DataUpdateFormToModelAdapter extends FormToModelAdapter<DataUpdateVisit> {

    @Override
    public DataUpdateVisit modelFromForm(FormModel form) {
        DataUpdateVisit visit = new DataUpdateVisit();

        FormFieldModelText textField = (FormFieldModelText) form.fieldByTag("notes");
        visit.setNotes(textField.getContent());

        FormFieldModelCoordinate coordinatesField = (FormFieldModelCoordinate) form
                .fieldByTag("beginingPoint");
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(coordinatesField.getLatitude());
        coordinate.setLongitude(coordinatesField.getLongitude());
        visit.setNewBeginingPoint(coordinate);

        coordinatesField = (FormFieldModelCoordinate) form
                .fieldByTag("endingPoint");
        coordinate = new Coordinate();
        coordinate.setLatitude(coordinatesField.getLatitude());
        coordinate.setLongitude(coordinatesField.getLongitude());
        visit.setNewEndingPoint(coordinate);

        FormFieldModelImageList imagesField = (FormFieldModelImageList) form.fieldByTag("photos");
        for (PlaceholderPhotoModel photo :
                imagesField.getImages()) {

            PhotoModel photoModel = new PhotoModel();

            photoModel.setDataUpdateVisit(visit);
            photoModel.setDescription(photo.getDescription());
            photoModel.setFilePath(photo.getFilePath());

            if (visit.getPhotos() == null) {
                visit.setPhotos(new RealmList<PhotoModel>());
            }
            visit.getPhotos().add(photoModel);
        }

        return visit;
    }

    @Override
    public void updateModelFromForm(DataUpdateVisit model, FormModel form) {
        DataUpdateVisit visit = modelFromForm(form);

        try {
            model.getNewBeginingPoint().updateFrom(visit.getNewBeginingPoint());
            model.getNewEndingPoint().updateFrom(visit.getNewEndingPoint());
            model.setNotes(visit.getNotes());

            PhotoModelRepository photoRepo = new PhotoModelRepository();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
