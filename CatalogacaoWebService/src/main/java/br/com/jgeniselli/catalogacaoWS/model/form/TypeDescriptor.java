/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

/**
 *
 * @author jgeniselli
 */
public class TypeDescriptor {
    private final String htmlType;
    private final String pattern;
    private final String javascriptTriggerClass;
    private final ValueTransformer valueTransformer;

    public TypeDescriptor(String htmlType, String pattern, String javascriptTriggerClass, ValueTransformer valueTransformer) {
        this.htmlType = htmlType;
        this.pattern = pattern;
        this.javascriptTriggerClass = javascriptTriggerClass;
        this.valueTransformer = valueTransformer;
    }

    public String getHtmlType() {
        return htmlType;
    }

    public String getPattern() {
        return pattern;
    }

    public String getJavascriptTriggerClass() {
        return javascriptTriggerClass;
    }

    public ValueTransformer getValueTransformer() {
        return valueTransformer;
    }
}
