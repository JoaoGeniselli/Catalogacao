package br.com.jgeniselli.catalogacaolem.common.form.modelAdapters;

import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCity;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCoordinate;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelText;
import br.com.jgeniselli.catalogacaolem.common.form.FormModel;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;

/**
 * Created by jgeniselli on 10/09/17.
 */

public class AntFormToModelAdapter extends FormToModelAdapter<Ant> {

    @Override
    public Ant modelFromForm(FormModel form) {
        Ant ant = new Ant();

        FormFieldModelText textField = (FormFieldModelText) form.fieldByTag("name");
        ant.setName(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("order");
        ant.setAntOrder(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("family");
        ant.setAntFamily(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("subfamily");
        ant.setAntSubfamily(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("genus");
        ant.setAntGenus(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("subgenus");
        ant.setAntSubgenus(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("species");
        ant.setAntSpecies(textField.getContent());

        textField = (FormFieldModelText) form.fieldByTag("notes");
        ant.setNotes(textField.getContent());

        return ant;
    }

    @Override
    public void updateModelFromForm(Ant model, FormModel form) {
        Ant unmanagedAnt = modelFromForm(form);
        model.updateFrom(unmanagedAnt);
    }
}
