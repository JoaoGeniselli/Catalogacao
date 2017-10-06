/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

import br.com.jgeniselli.catalogacaoWS.model.Ant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jgeniselli
 */
public class AntReportForm extends Form<Ant> {
    
    private final FormField name;
    private final FormField order;
    private final FormField family;
    private final FormField subfamily;
    private final FormField genus;
    private final FormField subgenus;
    private final FormField species;
    private final FormField notes;
    private final FormField initialDate;
    private final FormField finalDate;

    public AntReportForm(Ant commandObject) {
        super(commandObject);
        
        name = new FormField("name", "text", "Nome da Amostra");
        name.setWhereClauseMask("name like %s%%");
        order = new FormField("order", "text", "Ordem");
        order.setWhereClauseMask("name like %s%%");
        family = new FormField("family", "text", "Família");
        family.setWhereClauseMask("name like %s%%");
        subfamily = new FormField("subfamily", "text", "Sub-Família");
        subfamily.setWhereClauseMask("name like %s%%");
        genus = new FormField("genus", "text", "Gênero");
        genus.setWhereClauseMask("name like %s%%");
        subgenus = new FormField("subgenus", "text", "Sub-Gênero");
        subgenus.setWhereClauseMask("name like %s%%");
        species = new FormField("species", "text", "Espécie");
        species.setWhereClauseMask("name like %s%%");
        notes = new FormField("notes", "text", "Observações");
        notes.setWhereClauseMask("name like %s%%");
        
        initialDate = new FormField("initialDate", "date", "Data final de cadastro (dd-MM-aaaa)");
        initialDate.setPattern("\\d{2}-\\d{2}-\\d{4}");
        initialDate.setWhereClauseMask("register_date >= %s");
        
        finalDate = new FormField("finalDate", "date", "Data inicial de cadastro (dd-MM-aaaa)");
        finalDate.setPattern("\\d{2}-\\d{2}-\\d{4}");
        finalDate.setWhereClauseMask("register_date <= %s");
    }

    @Override
    public List<FormField> getFields() {
        ArrayList<FormField> fields = new ArrayList<>();
        
        fields.add(name);
        fields.add(order);
        fields.add(family);
        fields.add(subfamily);
        fields.add(genus);
        fields.add(subgenus);
        fields.add(species);
        fields.add(notes);
        fields.add(initialDate);
        fields.add(finalDate);

        return fields;
    }

    @Override
    public String getActionURI() {
        return "/web/generateAntRport";
    }

    @Override
    public String getTitle() {
        return "Relatório de Formigas";
    }
}
