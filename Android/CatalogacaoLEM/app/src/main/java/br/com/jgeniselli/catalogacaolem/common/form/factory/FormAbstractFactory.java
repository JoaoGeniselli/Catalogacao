package br.com.jgeniselli.catalogacaolem.common.form.factory;

import br.com.jgeniselli.catalogacaolem.common.form.model.FormModel;

/**
 * Created by jgeniselli on 22/08/17.
 */

public abstract class FormAbstractFactory<T> {
    public abstract FormModel getForm();
    public abstract FormModel getForm(T model);
}
