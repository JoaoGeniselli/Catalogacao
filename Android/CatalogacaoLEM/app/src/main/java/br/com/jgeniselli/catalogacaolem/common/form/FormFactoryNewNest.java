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
        fields.add(new FormFieldModelText(2, 2, "Vegetação Predominante"));
        FormModel formModel = new FormModel(1, "Novo Ninho", "Formulário de cadastro de novo ninho para análises", fields);
        return formModel;
    }
}