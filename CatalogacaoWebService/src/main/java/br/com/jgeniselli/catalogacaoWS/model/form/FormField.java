/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

import java.io.Serializable;

/**
 *
 * @author jgeniselli
 */
public class FormField implements Serializable {
    
    private int id;
    private int order;
    private String tag;
    private String htmlInputType;
    private String extraClasses;
    private String pattern;
    private String label;
    private String content;
    private String whereClauseMask;
    
    public FormField() {
        
    }

    public FormField(String tag, String htmlInputType, String label) {
        this.htmlInputType = htmlInputType;
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public String getTag() {
        return tag;
    }

    public String getHtmlInputType() {
        return htmlInputType;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWhereClauseMask() {
        return whereClauseMask;
    }

    public void setWhereClauseMask(String whereClauseMask) {
        this.whereClauseMask = whereClauseMask;
    }

    public String getExtraClasses() {
        return extraClasses;
    }

    public void setExtraClasses(String extraClasses) {
        this.extraClasses = extraClasses;
    }
    
    
}
