package br.com.jgeniselli.catalogacaolem.common.form;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFieldModelText extends FormFieldModel implements TextContentFormField {

    private String content;

    public FormFieldModelText(int id, int order, String title) {
        super(id, order, title);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.TEXT;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }
}
