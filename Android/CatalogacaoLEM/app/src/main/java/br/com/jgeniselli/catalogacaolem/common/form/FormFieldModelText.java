package br.com.jgeniselli.catalogacaolem.common.form;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFieldModelText extends FormFieldModel {

    private String content;

    public FormFieldModelText(int id, int order, String title) {
        super(id, order, title);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.TEXT;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
