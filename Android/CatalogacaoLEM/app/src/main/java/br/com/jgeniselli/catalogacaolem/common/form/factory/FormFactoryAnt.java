package br.com.jgeniselli.catalogacaolem.common.form.factory;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModel;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCity;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelCoordinate;
import br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelText;
import br.com.jgeniselli.catalogacaolem.common.form.FormModel;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;

/**
 * Created by jgeniselli on 10/09/17.
 */

public class FormFactoryAnt extends FormAbstractFactory<Ant> {
    @Override
    public FormModel getForm() {
        ArrayList<FormFieldModel> fields = new ArrayList<>();

        fields.add(new FormFieldModelText(1, 1, "Nome para a amostra", "name"));
        fields.add(new FormFieldModelText(2, 2, "Ordem", "order"));
        fields.add(new FormFieldModelText(3, 3, "Família", "family"));
        fields.add(new FormFieldModelText(4, 4, "Subfamília", "subfamily"));
        fields.add(new FormFieldModelText(5, 5, "Gênero", "genus"));
        fields.add(new FormFieldModelText(6, 6, "Subgênero", "subgenus"));
        fields.add(new FormFieldModelText(7, 7, "Espécie", "species"));

        for (FormFieldModel fieldModel : fields) {
            fieldModel.setRequired(true);
        }

        fields.add(new FormFieldModelText(8, 8, "Observações", "notes"));

        FormModel formModel = new FormModel(1, "Nova Formiga", "Formulário de cadastro de nova amostra de formiga", fields);
        return formModel;
    }

    @Override
    public FormModel getForm(Ant model) {
        FormModel form = getForm();

        ((FormFieldModelText)form.fieldByTag("name")).setContent(model.getName());
        ((FormFieldModelText)form.fieldByTag("order")).setContent(model.getAntOrder());
        ((FormFieldModelText)form.fieldByTag("family")).setContent(model.getAntFamily());
        ((FormFieldModelText)form.fieldByTag("subfamily")).setContent(model.getAntSubfamily());
        ((FormFieldModelText)form.fieldByTag("genus")).setContent(model.getAntGenus());
        ((FormFieldModelText)form.fieldByTag("subgenus")).setContent(model.getAntSubgenus());
        ((FormFieldModelText)form.fieldByTag("species")).setContent(model.getAntSpecies());
        ((FormFieldModelText)form.fieldByTag("notes")).setContent(model.getNotes());

        return form;
    }
}
