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
public class Form {
    
    private String title;
    private List<FormField> fields;

    public Form(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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

}
