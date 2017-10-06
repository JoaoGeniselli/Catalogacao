/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

import java.util.List;

/**
 *
 * @author jgeniselli
 */
public abstract class Form<T> {
    
    private List<FormField> fields;
    private T commandObject;

    public Form(T commandObject) {
        this.commandObject = commandObject;
    }

    public List<FormField> getFields() {
        return fields;
    }
    
    public String whereFromFields() {
        String where = "WHERE ";
        for (FormField field : fields) {
            if (field.getContent() != null && field.getContent().length() > 0) {
                where = where + String.format(
                        field.getWhereClauseMask(), 
                        field.getContent());
            }
        }
        return where;
    }

    public T getCommandObject() {
        return commandObject;
    }

    public void setCommandObject(T commandObject) {
        this.commandObject = commandObject;
    }
    public abstract String getTitle();
    public abstract String getActionURI();
}
