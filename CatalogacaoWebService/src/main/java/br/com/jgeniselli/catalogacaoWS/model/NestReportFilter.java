/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model;

import br.com.jgeniselli.catalogacaoWS.util.QueryUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author jgeniselli
 */
public class NestReportFilter {
    
    private Long id;

    private Long collector;
    
    private String name;
    
    private String cityName;
    private Long cityId;
    
    private Boolean showActives;
    private Boolean showInactives;
    
    private String address;

    private String vegetation;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finalDate;
    
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollector() {
        return collector;
    }

    public void setCollector(Long collector) {
        this.collector = collector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Boolean isShowActives() {
        return showActives;
    }

    public void setShowActives(Boolean showActives) {
        this.showActives = showActives;
    }

    public Boolean isShowInactives() {
        return showInactives;
    }

    public void setShowInactives(Boolean showInactives) {
        this.showInactives = showInactives;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVegetation() {
        return vegetation;
    }

    public void setVegetation(String vegetation) {
        this.vegetation = vegetation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getQuery() {
        String query = 
        "SELECT \n" +
        "    V.ID AS ATUALIZACAO,\n" +
        "    V.nest_nest_id AS NINHO,\n" +
        "    DATE_FORMAT(V.collection_date, '%d/%m/%y') AS DATA,\n" +
        "    CONCAT('Lat: ',\n" +
        "            INITIAL_COORDINATE.latitude,\n" +
        "            '\r\nLon: ',\n" +
        "            INITIAL_COORDINATE.longitude) AS PONTO_INICIAL,\n" +
        "    CONCAT('Lat: ',\n" +
        "            FINAL_COORDINATE.latitude,\n" +
        "            '\r\nLon: ',\n" +
        "            FINAL_COORDINATE.longitude) AS PONTO_FINAL,\n" +
        "    V.notes AS OBSERVACOES,\n" +
        "    IFNULL(GROUP_CONCAT(CONCAT($P{IMAGE_PATH}, P.FILEPATH)\n" +
        "                SEPARATOR '; '),\n" +
        "            'Sem Registros') AS FOTOS,\n" +
        "    IFNULL(GROUP_CONCAT(A.NAME\n" +
        "                SEPARATOR '; '),\n" +
        "            'Sem Registros') AS AMOSTRAS,\n" +
        "	N.active AS NINHO_ATIVO,\n" +
        "    N.address AS ENDERECO,\n" +
        "    N.vegetation AS VEGETACAO,\n" +
        "    V.COLLECTOR_ID AS COLLECTOR,\n" +
        "    V.COLLECTION_DATE\n" +
        "FROM\n" +
        "    data_update_visit V\n" +
        "        LEFT JOIN\n" +
        "    data_update_visit_photos I ON I.DATA_UPDATE_VISIT_ID = V.ID\n" +
        "        LEFT JOIN\n" +
        "    ant_nest N ON V.NEST_NEST_ID = N.NEST_ID\n" +
        "        LEFT JOIN\n" +
        "    photo P ON P.ID = I.PHOTOS_ID\n" +
        "        LEFT JOIN\n" +
        "    coordinate INITIAL_COORDINATE ON (INITIAL_COORDINATE.id = V.new_begining_point_id)\n" +
        "        LEFT JOIN\n" +
        "    coordinate FINAL_COORDINATE ON (FINAL_COORDINATE.id = V.new_ending_point_id)\n" +
        "        LEFT JOIN\n" +
        "    ant A ON A.VISIT_ID = V.ID\n" +
        "GROUP BY V.ID, N.NEST_ID\n";

        query = query + getHavingClause();
        query = query + " ORDER BY V.NEST_NEST_ID;";
        
        return query;
    }
    
    public String getHavingClause() {
        List<String> clauses;
        clauses = new ArrayList<>();
        if (id != null && id > 0) {
            clauses.add(QueryUtils.integerClause("V.ID", id));
        }
        
        if (collector != null && collector > 0) {
            clauses.add(QueryUtils.integerClause("V.COLLECTOR_ID", collector));
        }
        
        if (showActives != null && showActives) {
            clauses.add(QueryUtils.integerClause("N.ACTIVE", 1));
        }
        
        if (showInactives != null && showInactives) {
            clauses.add(QueryUtils.integerClause("N.ACTIVE", 0));
        }
        
        if (address != null && address.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("N.ADDRESS", address));
        }
        
        if (vegetation != null && vegetation.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("N.VEGETATION", vegetation));
        }

        if (startDate != null && finalDate != null) {
            clauses.add(QueryUtils.betweenDatesClause("V.COLLECTION_DATE", startDate, finalDate));
        } else if (startDate != null) {
            clauses.add(QueryUtils.greaterOrEqualThanDateClause("V.COLLECTION_DATE", startDate));
        } else if (finalDate != null) {
            clauses.add(QueryUtils.lowerOrEqualThanDateClause("V.COLLECTION_DATE", finalDate));
        }
        
        if (notes != null && notes.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("V.NOTES", notes));
        }
        
        if (!clauses.isEmpty()) {
           return "HAVING " + StringUtils.join(clauses, " AND ");
        }

        return "";
    }
}
