package br.com.jgeniselli.catalogacaolem.common.form.factory;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModel;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCity;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCoordinate;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelText;
import br.com.jgeniselli.catalogacaolem.common.form.FormModel;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFactoryNewNest extends FormAbstractFactory<AntNest> {

    @Override
    public FormModel getForm() {
        ArrayList<FormFieldModel> fields = new ArrayList<>();

        fields.add(new FormFieldModelText(1, 1, "Nome para o ninho:", "name"));
        fields.add(new FormFieldModelText(2, 2, "Vegetação Predominante:", "vegetation"));
        fields.add(new FormFieldModelText(3, 3, "Endereço:", "address"));
        fields.add(new FormFieldModelCity(4, 4, "Cidade", "city"));
        fields.add(new FormFieldModelCoordinate(5, 6, "Ponto inicial do ninho", "beginingPoint"));
        fields.add(new FormFieldModelCoordinate(6, 7, "Ponto final do ninho", "endingPoint"));

        for (FormFieldModel fieldModel : fields) {
            fieldModel.setRequired(true);
        }

        FormModel formModel = new FormModel(1, "Novo Ninho", "Formulário de cadastro de novo ninho para análises", fields);
        return formModel;
    }

    @Override
    public FormModel getForm(AntNest model) {
        FormModel form = getForm();

        ((FormFieldModelText)form.fieldByTag("name")).setContent(model.getName());
        ((FormFieldModelText)form.fieldByTag("vegetation")).setContent(model.getVegetation());
        ((FormFieldModelText)form.fieldByTag("address")).setContent(model.getAddress());
        ((FormFieldModelCity)form.fieldByTag("city")).setCityModel(model.getCity());
        ((FormFieldModelCoordinate)form.fieldByTag("beginingPoint")).setupFromCoordinate(model.getBeginingPoint());
        ((FormFieldModelCoordinate)form.fieldByTag("endingPoint")).setupFromCoordinate(model.getEndingPoint());

        return form;
    }
}
