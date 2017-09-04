package br.com.jgeniselli.catalogacaolem.common.form;

import java.util.ArrayList;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFactoryNewNest extends FormAbstractFactory {

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
}
