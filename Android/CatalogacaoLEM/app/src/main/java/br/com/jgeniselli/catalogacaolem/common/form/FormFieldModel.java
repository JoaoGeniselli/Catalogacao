package br.com.jgeniselli.catalogacaolem.common.form;

import java.io.Serializable;

/**
 * Created by jgeniselli on 22/08/17.
 */

public abstract class FormFieldModel implements Serializable {

    private int id;
    private int order;
    private String title;
    private FormFieldModelType type;

    public FormFieldModel(int id, int order, String title) {
        this.id = id;
        this.order = order;
        this.title = title;
    }

    public abstract FormFieldModelType getType();

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }
}
