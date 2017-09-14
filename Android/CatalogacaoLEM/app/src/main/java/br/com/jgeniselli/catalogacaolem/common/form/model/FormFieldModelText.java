package br.com.jgeniselli.catalogacaolem.common.form.model;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFieldModelText extends FormFieldModel {

    private String content;

    public FormFieldModelText(int id, int order, String title, String tag) {
        super(id, order, title, tag);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.TEXT;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (!isRequired()) {
            setErrored(false);
        } else if (content != null && content.length() > 0) {
            setErrored(false);
        }
        this.content = content;
    }

    @Override
    public boolean validate() {
        if (this.isRequired() && (content == null || content.length() == 0)) {
            setErrored(true);
            return false;
        }
        return true;
    }
}
