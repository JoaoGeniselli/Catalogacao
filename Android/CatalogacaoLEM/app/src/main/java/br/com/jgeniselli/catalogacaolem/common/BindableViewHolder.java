package br.com.jgeniselli.catalogacaolem.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jgeniselli on 07/09/17.
 */

public abstract class BindableViewHolder<T> extends RecyclerView.ViewHolder implements BindableView<T> {
    public BindableViewHolder(View itemView) {
        super(itemView);
    }


}
