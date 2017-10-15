/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jgeniselli
 * @param <T>
 */
public interface ValueTransformer<T> {
    
    T objectFromContent(FormField formField);
    String contentFromObject(T object, FormField formField);
    
    public static class StringValueTransformer implements ValueTransformer<String>{

        @Override
        public String objectFromContent(FormField formField) {
            return formField.getContent();
        }

        @Override
        public String contentFromObject(String object, FormField formField) {
            return object;
        }
    }
    
    public static class LongValueTransformer implements ValueTransformer<Long>{
        @Override
        public Long objectFromContent(FormField formField) {
            if (formField.getContent() == null || 
                    formField.getContent().length() == 0) {
                return new Long(0);
            }
            long value = 0;
            try {
                value = Long.valueOf(formField.getContent()); 
            } catch (NumberFormatException e) {
                value = Double.valueOf(formField.getContent()).longValue();
            } 
            return value;
        }

        @Override
        public String contentFromObject(Long object, FormField formField) {
            return String.format("%d", object);
        }
    }
    
    public static class DoubleValueTransformer implements ValueTransformer<Double> {

        @Override
        public Double objectFromContent(FormField formField) {
            if (formField.getContent() == null || 
                    formField.getContent().length() == 0) {
                return 0.0;
            }
            double value = 0;
            try {
                value = Double.valueOf(formField.getContent());
            } catch (NumberFormatException e) {
                value = Integer.valueOf(formField.getContent()).doubleValue();
            } 
            return value;
        }

        @Override
        public String contentFromObject(Double object, FormField formField) {
            return String.format("%f", object);
        }
    }
    
    public static class DateValueTransformer implements ValueTransformer<Date> {

        @Override
        public Date objectFromContent(FormField formField) {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date; 
            try {
                date = formatter.parse(formField.getContent());
            } catch (ParseException ex) {
                date = null;
            }
            return date;
        }

        @Override
        public String contentFromObject(Date object, FormField formField) {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString; 
            dateString = formatter.format(object);
            return dateString;
        }
    }
}
    
    