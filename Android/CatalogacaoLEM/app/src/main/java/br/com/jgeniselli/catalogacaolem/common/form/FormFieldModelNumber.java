package br.com.jgeniselli.catalogacaolem.common.form;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFieldModelNumber extends FormFieldModel {

    private boolean decimal;
    private Number value;

    private int intValue;
    private double doubleValue;

    public FormFieldModelNumber(int id, int order, String title, String tag) {
        super(id, order, title, tag);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.NUMBER;
    }

    public boolean isDecimal() {
        return decimal;
    }

    public void setDecimal(boolean decimal) {
        this.decimal = decimal;
    }

    public Number getValue() {
        if (decimal) {
            return doubleValue;
        } else {
            return intValue;
        }
    }

    @Override
    public boolean validate() {
        return super.validate();
    }

    public void setValue(Number value) {

    }

    public void setValueFromString(String string) {

    }
}
