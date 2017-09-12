package br.com.jgeniselli.catalogacaolem.common.form.factory;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModel;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCity;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCoordinate;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelText;
import br.com.jgeniselli.catalogacaolem.common.form.FormModel;
import br.com.jgeniselli.catalogacaolem.common.models.DataUpdateVisit;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFactoryDataUpdateVisit extends FormAbstractFactory<DataUpdateVisit> {

    @Override
    public FormModel getForm() {
        ArrayList<FormFieldModel> fields = new ArrayList<>();

        fields.add(new FormFieldModelCoordinate(1, 1, "Novo ponto inicial do ninho", "beginingPoint"));
        fields.add(new FormFieldModelCoordinate(2, 2, "Novo ponto final do ninho", "endingPoint"));
        fields.add(new FormFieldModelText(1, 1, "Observações", "notes"));

        FormModel formModel = new FormModel(1, "Atualização de dados", "Formulário de atualização de dados de um ninho existente para análises", fields);
        return formModel;
    }

    @Override
    public FormModel getForm(DataUpdateVisit model) {
        FormModel form = getForm();

        ((FormFieldModelText)form.fieldByTag("notes")).setContent(model.getNotes());
        ((FormFieldModelCoordinate)form.fieldByTag("beginingPoint")).setupFromCoordinate(model.getNewBeginingPoint());
        ((FormFieldModelCoordinate)form.fieldByTag("endingPoint")).setupFromCoordinate(model.getNewEndingPoint());

        return form;
    }
}
