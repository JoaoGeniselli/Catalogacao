package br.com.jgeniselli.catalogacaolem.common.form;

/**
 * Created by jgeniselli on 22/08/17.
 */

public enum FormFieldModelType {
    NONE(0),
    TEXT(1),
    NUMBER(2),
    COORDINATE(3);

    int id;

    FormFieldModelType(int id) {
        this.id = id;
    }

    public FormFieldModelType getTypeForId(int id) {
        for (FormFieldModelType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return NONE;
    }
}
