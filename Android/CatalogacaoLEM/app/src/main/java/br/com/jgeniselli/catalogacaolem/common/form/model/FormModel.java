package br.com.jgeniselli.catalogacaolem.common.form.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModel;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormModel implements Serializable {

    private ArrayList<FormFieldModel> fields;
    private String title;
    private String description;
    private int id;

    private HashMap<String, FormFieldModel> formFieldsByTag;

    public FormModel(int id, String title, String description, ArrayList<FormFieldModel> fields) {
        this.fields = fields;
        this.title = title;
        this.description = description;
        this.id = id;

        updateFormFieldsHash();
    }

    private void updateFormFieldsHash() {
        formFieldsByTag = new HashMap<>();
        for (FormFieldModel fieldModel : fields) {
            if (fieldModel.getTag() != null && fieldModel.getTag().length() > 0) {
                formFieldsByTag.put(fieldModel.getTag(), fieldModel);
            }
        }
    }

    public ArrayList<FormFieldModel> getFields() {
        return fields;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public boolean validate() {
        for (FormFieldModel field : getFields()) {
            if (!field.validate()) {
                return false;
            }
        }
        return true;
    }

    public FormFieldModel fieldByTag(String tag) {
        if (formFieldsByTag != null && formFieldsByTag.containsKey(tag)) {
            return formFieldsByTag.get(tag);
        }
        return null;
    }
}
