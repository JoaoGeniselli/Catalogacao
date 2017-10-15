/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jgeniselli
 */
public class QueryUtils {
    
    public static String integerClause(String field, long condition) {
        return String.format("%s = %d", field, condition);
    }
    
    public static String middleStringClause(String field, String condition) {
        return String.format("%s LIKE '%%%s%%'", field, condition);
    }
    
    public static String betweenDatesClause(String field, Date startDate, Date finalDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s BETWEEN %s AND %s", field, formatter.format(startDate), formatter.format(finalDate));
    }
    
    public static String greaterOrEqualThanDateClause(String field, Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s >= %s", field, formatter.format(date));
    }
    
    public static String lowerOrEqualThanDateClause(String field, Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s <= %s", field, formatter.format(date));
    }
    
}
