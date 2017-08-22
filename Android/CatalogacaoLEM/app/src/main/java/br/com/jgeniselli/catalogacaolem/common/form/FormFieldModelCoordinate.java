package br.com.jgeniselli.catalogacaolem.common.form;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFieldModelCoordinate extends FormFieldModel {

    public FormFieldModelCoordinate(int id, int order, String title) {
        super(id, order, title);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.COORDINATE;
    }
}
