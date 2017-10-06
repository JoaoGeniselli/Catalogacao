/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS.model.form;

import br.com.jgeniselli.catalogacaoWS.model.DataUpdateVisit;

/**
 *
 * @author jgeniselli
 */
public class DataUpdateEditionForm extends Form<DataUpdateVisit>{
    
    

    public DataUpdateEditionForm(DataUpdateVisit commandObject) {
        super(commandObject);
        
        FormField field = new FormField("collectionDate", "date", "Data de Coleta");
        field.setContent(commandObject.getCollectionDate().toString());
        
        field = new FormField("newBeginingPoint.latitude", "text", "Coordenada Inicial - Latitude");
        field.setContent(String.format("%f", commandObject.getNewBeginingPoint().getLatitude()));

        field = new FormField("newBeginingPoint.longitude", "text", "Coordenada Inicial - Longitude");
        field.setContent(String.format("%f", commandObject.getNewBeginingPoint().getLongitude()));
        
        field = new FormField("newBeginingPoint.latitude", "text", "Coordenada Final - Latitude");
        field.setContent(String.format("%f", commandObject.getNewEndingPoint().getLatitude()));

        field = new FormField("newBeginingPoint.longitude", "text", "Coordenada Final - Longitude");
        field.setContent(String.format("%f", commandObject.getNewEndingPoint().getLongitude()));
        
        field = new FormField("notes", "text", "Observações");
        field.setContent(commandObject.getNotes());

        field = new FormField("newBeginingPoint.longitude", "text", "Coordenada inicial - Longitude");
        field.setContent(String.format("%f", commandObject.getNewBeginingPoint().getLongitude()));

        /*

    @OneToMany
    private List<Photo> photos;
        
        */
    }

    @Override
    public String getActionURI() {
        return "/web/saveDataUpdateForm";
    }

    @Override
    public String getTitle() {
        return "Edição de Atualização de Dados";
    }
}
