/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

import br.com.jgeniselli.catalogacaoWS.model.Ant;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormIgnore;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormLabel;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormTitle;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jgeniselli
 */
public class Form<T> {
    
    private List<FormField> fields;
    private T commandObject;
    
    private String title;
    private String headMessage;
    private String footerMessage;
    private String actionURI;

    public Form(T commandObject) {
        this.commandObject = commandObject;
        setup();
    }

    public void setFields(List<FormField> fields) {
        this.fields = fields;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionURI() {
        return actionURI;
    }

    public void setActionURI(String actionURI) {
        this.actionURI = actionURI;
    }

    public void setup() {
        Field[] modelFields = commandObject.getClass().getDeclaredFields();
        
        ArrayList<FormField> formFields = new ArrayList<>();
        
        HashMap availableTypes = htmlTypeByPropertyType();
        
        for (Field field : modelFields) {
            if (!field.isAnnotationPresent(FormIgnore.class)) {
                Type concreteType = field.getType();
                        
                if (availableTypes.containsKey(concreteType)) {
                    String name = field.isAnnotationPresent(FormLabel.class) ? 
                            field.getAnnotation(FormLabel.class).value() :
                            field.getName();
                    
                    FormField formField = new FormField(
                            field.getName(), 
                            (String) availableTypes.get(concreteType.getClass()), 
                            name
                    );
                    
                    formFields.add(formField);
                }
            }
        }

        if (commandObject.getClass().isAnnotationPresent(FormTitle.class)) {
            setTitle(commandObject
                    .getClass()
                    .getAnnotation(FormTitle.class).value()
            );
        }
        setFields(formFields);
    }

    public HashMap<Class, String> htmlTypeByPropertyType() {
        HashMap<Class, String> map = new HashMap();

        map.put(String.class, "text");
        map.put(Date.class, "date");
        map.put(Long.class, "text");
        map.put(Double.class, "text");
        
        return map;
    }
    
    public void applyChanges() throws FormValidationException {
        
    }

    public static class FormValidationException extends Exception {
    
    }
}
