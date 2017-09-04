package br.com.jgeniselli.catalogacaolem.common.form;

import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;

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

        return nest;
    }
}
