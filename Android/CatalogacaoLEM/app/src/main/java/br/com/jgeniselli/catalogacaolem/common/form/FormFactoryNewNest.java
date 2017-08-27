package br.com.jgeniselli.catalogacaolem.common.form;

import java.util.ArrayList;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFactoryNewNest extends FormAbstractFactory {

    @Override
    public FormModel getForm() {
        ArrayList<FormFieldModel> fields = new ArrayList<>();

        fields.add(new FormFieldModelText(1, 1, "Nome para o ninho:"));
        fields.add(new FormFieldModelText(2, 2, "Vegetação Predominante:"));
        fields.add(new FormFieldModelText(3, 3, "Endereaço:"));
        fields.add(new FormFieldModelCity(4, 4, "Cidade"));
        fields.add(new FormFieldModelCoordinate(5, 6, "Ponto inicial do ninho"));
        fields.add(new FormFieldModelCoordinate(5, 6, "Ponto final do ninho"));

        FormModel formModel = new FormModel(1, "Novo Ninho", "Formulário de cadastro de novo ninho para análises", fields);
        return formModel;
    }
}
