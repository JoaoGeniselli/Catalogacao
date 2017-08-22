package br.com.jgeniselli.catalogacaolem.common.form;

import java.util.ArrayList;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormModel {

    private ArrayList<FormFieldModel> fields;
    private String title;
    private String description;
    private int id;

    public FormModel(int id, String title, String description, ArrayList<FormFieldModel> fields) {
        this.fields = fields;
        this.title = title;
        this.description = description;
        this.id = id;
    }
}
