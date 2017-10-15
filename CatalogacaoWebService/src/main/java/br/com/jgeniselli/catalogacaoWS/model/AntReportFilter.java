/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model;

import br.com.jgeniselli.catalogacaoWS.util.QueryUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author jgeniselli
 */
public class AntReportFilter {
    
    
    private Long id;
    
    private String name;
    
    private Long nest;
    
    private String order;
    
    private String family;
    
    private String subfamily;
    
    private String genus;
    
    private String subgenus;
    
    private String species;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finalDate;
    
    private String notes;

    public AntReportFilter() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNest() {
        return nest;
    }

    public void setNest(Long nest) {
        this.nest = nest;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSubfamily() {
        return subfamily;
    }

    public void setSubfamily(String subfamily) {
        this.subfamily = subfamily;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSubgenus() {
        return subgenus;
    }

    public void setSubgenus(String subgenus) {
        this.subgenus = subgenus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
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
        String query = "SELECT \n" +
        "    ANT.id,\n" +
        "    ANT.ant_nest_nest_id AS NINHO,\n" +
        "    ANT.visit_id AS ATUALIZACAO,\n" +
        "    ANT.ant_order AS ORDEM,\n" +
        "    ANT.ant_family AS FAMILIA,\n" +
        "    ANT.ant_subfamily AS 'SUB-FAMILIA',\n" +
        "    ANT.ant_family AS GENERO,\n" +
        "    ANT.ant_subgenus AS 'SUB-GENERO',\n" +
        "    ANT.ant_species AS ESPECIE,\n" +
        "    DATE_FORMAT(ANT.register_date, '%d/%m/%y') AS DATA,\n" +
        "    IFNULL(GROUP_CONCAT(CONCAT($P{IMAGE_PATH}, PHOTO.FILEPATH)\n" +
        "                SEPARATOR '; '),\n" +
        "            'Sem registros') AS FOTOS,\n" +
        "   ANT.notes as OBSERVACOES,\n" +
        "   ANT.register_date \n" +
        "FROM\n" +
        "    ant ANT\n" +
        "        LEFT JOIN\n" +
        "    ant_photos AP ON (AP.ant_id = ANT.id)\n" +
        "        LEFT JOIN\n" +
        "    photo PHOTO ON (PHOTO.id = AP.photos_id)\n" +
        "    GROUP BY ANT.id\n";
        query = query + getHavingClause();
        return query;
    }
     
    public String getHavingClause() {
        List<String> clauses;
        clauses = new ArrayList<>();
        if (id != null && id > 0) {
            clauses.add(QueryUtils.integerClause("ANT.ID", id));
        }
        
        if (nest != null && nest > 0) {
            clauses.add(QueryUtils.integerClause("ANT.ant_nest_nest_id", nest));
        }
        
        if (order != null && order.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("ant.ant_order", order));
        }
        
        if (family != null && family.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("ant.ant_family", family));
        }
        
        if (subfamily != null && subfamily.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("ant.ant_subfamily", subfamily));
        }
        
        if (genus != null && genus.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("ant.ant_genus", genus));
        }
        
        if (subgenus != null && subgenus.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("ant.ant_subgenus", subgenus));
        }
        
        if (species != null && species.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("ant.ant_species", species));
        }

        if (startDate != null && finalDate != null) {
            clauses.add(QueryUtils.betweenDatesClause("ANT.register_date", startDate, finalDate));
        } else if (startDate != null) {
            clauses.add(QueryUtils.greaterOrEqualThanDateClause("ANT.register_date", startDate));
        } else if (finalDate != null) {
            clauses.add(QueryUtils.lowerOrEqualThanDateClause("ANT.register_date", finalDate));
        }
        
        if (notes != null && notes.length() > 0) {
            clauses.add(QueryUtils.middleStringClause("ANT.NOTES", notes));
        }
        
        if (!clauses.isEmpty()) {
           return "HAVING " + StringUtils.join(clauses, " AND ");
        }

        return "";
    }
}
