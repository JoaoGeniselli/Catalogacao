package br.com.jgeniselli.catalogacaolem.common.form.modelAdapters;

import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCoordinate;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelText;
import br.com.jgeniselli.catalogacaolem.common.form.FormModel;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;

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

        return visit;
    }

    @Override
    public void updateModelFromForm(DataUpdateVisit model, FormModel form) {
        DataUpdateVisit visit = modelFromForm(form);

        try {
            model.getNewBeginingPoint().updateFrom(visit.getNewBeginingPoint());
            model.getNewEndingPoint().updateFrom(visit.getNewEndingPoint());
            model.setNotes(visit.getNotes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
