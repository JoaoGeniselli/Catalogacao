package br.com.jgeniselli.catalogacaolem.common.form.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.jgeniselli.catalogacaolem.common.view.BindableView;

/**
 * Created by jgeniselli on 22/08/17.
 */

public abstract class FormFieldViewHolder<T> extends RecyclerView.ViewHolder implements BindableView<T> {

    public FormFieldViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public abstract void bind(T model);

    public abstract int preferedViewId();
}
