/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jgeniselli
 */
public class NestForm extends Form{
    
    private FormField name;
    private FormField cityName;
    private FormField vegetation;
    private FormField address;
    private FormField collector;
    private FormField initialDate;
    private FormField finalDate;
    private FormField notes;
    private FormField initialCoordinate;
    private FormField finalCoordinate;

    public NestForm(String title) {
        super(title);
        
        name    = new FormField("name", "text", "Nome do Ninho");
        cityName = new FormField("cityName", "text", "Cidade");
        vegetation = new FormField("vegetation", "text", "Vegetação");
        address = new FormField("address", "text", "Endereço");
        collector = new FormField("collector", "text", "Coletor");
        initialDate = new FormField("initialDate", "date", "Data Inicial (dd-MM-aaaa)");
        initialDate.setPattern("\\d{2}-\\d{2}-\\d{4}");
        finalDate = new FormField("finalDate", "date", "Data Final (dd-MM-aaaa)");
        finalDate.setPattern("\\d{2}-\\d{2}-\\d{4}");
        notes = new FormField("notes", "text", "Observações");
        initialCoordinate = new FormField("initialCoordinate", "text", "Coordenadas Iniciais (lat, long)");
        finalCoordinate = new FormField("finalCoordinate", "text", "Coordenadas Finais (lat, long)");
    }

    @Override
    public List<FormField> getFields() {
        ArrayList<FormField> fields = new ArrayList<>();
        
        fields.add(name);
        fields.add(cityName);
        fields.add(vegetation);
        fields.add(address);
        fields.add(collector);
        fields.add(initialDate);
        fields.add(finalDate);
        fields.add(notes);
//        fields.add(initialCoordinate);
//        fields.add(finalCoordinate);

        return fields;
    }

}
