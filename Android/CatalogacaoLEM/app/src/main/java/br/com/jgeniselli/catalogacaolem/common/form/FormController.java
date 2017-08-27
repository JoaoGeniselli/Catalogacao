package br.com.jgeniselli.catalogacaolem.common.form;

import java.util.HashMap;

/**
 * Created by jgeniselli on 26/08/17.
 */

public class FormController {

    private FormModel formModel;

    private HashMap<FormFieldModelType, FormEventHandler> eventHandlerMap;

    public FormController(FormModel formModel) {
        this.formModel = formModel;
        this.eventHandlerMap = new HashMap<>();
    }

    public HashMap<FormFieldModelType, FormEventHandler> getEventHandlerMap() {
        return eventHandlerMap;
    }

    public void setEventHandlerMap(HashMap<FormFieldModelType, FormEventHandler> eventHandlerMap) {
        this.eventHandlerMap = eventHandlerMap;
    }
}
