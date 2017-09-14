package br.com.jgeniselli.catalogacaolem.common.form.model;

import java.io.Serializable;

/**
 * Created by jgeniselli on 22/08/17.
 */

public abstract class FormFieldModel implements Serializable {

    private int id;
    private int order;
    private String title;
    private String tag;
    private boolean required;
    private boolean errored;

    public FormFieldModel(int id, int order, String title, String tag) {
        this.id = id;
        this.order = order;
        this.title = title;
        this.tag = tag;
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

    public boolean isErrored() {
        return errored;
    }

    public void setErrored(boolean errored) {
        this.errored = errored;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
