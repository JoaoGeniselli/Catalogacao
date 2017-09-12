package br.com.jgeniselli.catalogacaolem.common.form.modelAdapters;

import br.com.jgeniselli.catalogacaolem.common.form.FormModel;

/**
 * Created by jgeniselli on 03/09/17.
 */

public abstract class FormToModelAdapter<T> {

    public abstract T modelFromForm(FormModel form);

    public abstract void updateModelFromForm(T model, FormModel form);
}
