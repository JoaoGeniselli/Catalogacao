package br.com.jgeniselli.catalogacaolem.common.form;

/**
 * Created by jgeniselli on 03/09/17.
 */

public abstract class FormToModelAdapter<T> {

    public abstract T modelFromForm(FormModel form);
}
