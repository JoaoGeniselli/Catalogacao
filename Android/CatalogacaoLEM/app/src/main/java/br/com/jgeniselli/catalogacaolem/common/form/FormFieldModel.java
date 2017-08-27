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
    private boolean required;

    public FormFieldModel(int id, int order, String title) {
        this.id = id;
        this.order = order;
        this.title = title;
        this.required = false;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean validate() {
        return true;
    }
}
