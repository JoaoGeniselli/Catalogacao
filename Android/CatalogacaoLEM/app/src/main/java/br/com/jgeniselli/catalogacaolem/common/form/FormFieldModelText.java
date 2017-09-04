package br.com.jgeniselli.catalogacaolem.common.form;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFieldModelText extends FormFieldModel implements TextContentFormField {

    private String content;

    public FormFieldModelText(int id, int order, String title, String tag) {
        super(id, order, title, tag);
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
