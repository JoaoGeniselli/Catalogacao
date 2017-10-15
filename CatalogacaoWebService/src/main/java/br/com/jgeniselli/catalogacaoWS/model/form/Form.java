/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

import br.com.jgeniselli.catalogacaoWS.model.Ant;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormIdProvider;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormIgnore;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormLabel;
import br.com.jgeniselli.catalogacaoWS.model.annotation.FormTitle;
import ch.qos.logback.classic.gaffer.PropertyUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author jgeniselli
 */
public class Form<T> implements Serializable {
    
    private List<FormField> fields;
    private T commandObject;
    
    private String title;
    private String headMessage;
    private String footerMessage;
    private String actionURI;

    public Form() {
        this.fields = new ArrayList<>();
    }
    
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

    public String getHeadMessage() {
        return headMessage;
    }

    public void setHeadMessage(String headMessage) {
        this.headMessage = headMessage;
    }

    public String getFooterMessage() {
        return footerMessage;
    }

    public void setFooterMessage(String footerMessage) {
        this.footerMessage = footerMessage;
    }
    
    public void setup() {
        Field[] modelFields = commandObject.getClass().getDeclaredFields();
        
        ArrayList<FormField> formFields = new ArrayList<>();
        
        HashMap availableTypes = htmlTypeByPropertyType();
        
        for (Field field : modelFields) {
            if (field.isAnnotationPresent(FormIdProvider.class)) {
                FormField idField = new FormField("id", "hidden", "");
                try {
                    idField.setContent(String.format("%d", PropertyUtils.getProperty(commandObject, field.getName())));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
                }
                formFields.add(idField);
            }

            if (!field.isAnnotationPresent(FormIgnore.class)) {
                Type concreteType = field.getType();
                        
                if (availableTypes.containsKey(concreteType)) {
                    String name = field.isAnnotationPresent(FormLabel.class) ? 
                            field.getAnnotation(FormLabel.class).value() :
                            field.getName();
                    
                    TypeDescriptor typeDescriptor = (TypeDescriptor) availableTypes.get(concreteType);

                    FormField formField = new FormField(
                            field.getName(), 
                            typeDescriptor.getHtmlType(),
                            name
                    );
                    formField.setPattern(typeDescriptor.getPattern());
                    formField.setExtraClasses(typeDescriptor.getJavascriptTriggerClass());
                    
                    try {
                        formField.setContent(typeDescriptor.getValueTransformer().contentFromObject(PropertyUtils.getProperty(commandObject, field.getName()), formField));
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

    public HashMap<Class, TypeDescriptor> htmlTypeByPropertyType() {
        HashMap<Class, TypeDescriptor> map = new HashMap();

        map.put(String.class, new TypeDescriptor("text", null, null, new ValueTransformer.StringValueTransformer()));
        map.put(Date.class,   new TypeDescriptor("date", "", "date_input", new ValueTransformer.DateValueTransformer()));
        map.put(Long.class,   new TypeDescriptor("text", "", "integer_input", new ValueTransformer.LongValueTransformer()));
        map.put(Double.class, new TypeDescriptor("text", null, "double_input", new ValueTransformer.DoubleValueTransformer()));
        
        return map;
    }
    
    public void applyChanges(T oficialObject) throws FormValidationException {
        HashMap<Class, TypeDescriptor> typeDescriptors = htmlTypeByPropertyType();
        
        for (FormField formField : fields) {
            try {
                Field field = commandObject.getClass().getField(formField.getTag());
                TypeDescriptor typeDescriptor = typeDescriptors.get(field.getClass());
                field.set(commandObject, typeDescriptor
                        .getValueTransformer()
                        .objectFromContent(formField)
                );
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                
            }
        }
    }

    public static class FormValidationException extends Exception {
    
    }
}
