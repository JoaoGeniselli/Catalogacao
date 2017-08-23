package br.com.jgeniselli.catalogacaolem.common.form;

import android.support.v7.widget.RecyclerView;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 22/08/17.
 */

public enum FormFieldModelType {
    NONE(0, 0),
    TEXT(1, R.layout.line_view_form_text),
    NUMBER(2, R.layout.line_view_form_text),
    COORDINATE(3, R.layout.line_view_form_text);

    private int id;
    private int viewId;

    FormFieldModelType(int id, int viewId) {
        this.id = id;
        this.viewId = viewId;
    }

    public static FormFieldModelType typeForId(int id) {
        for (FormFieldModelType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return NONE;
    }

    public int getId() {
        return id;
    }

    public int getViewId() {
        return viewId;
    }
}
