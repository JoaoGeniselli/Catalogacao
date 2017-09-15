package br.com.jgeniselli.catalogacaolem.common.form.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelImageList;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class ImageListFormFieldViewHolder extends FormFieldViewHolder<FormFieldModelImageList> {

    private RecyclerView recyclerView;

    public ImageListFormFieldViewHolder(View itemView) {
        super(itemView);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);


    }

    @Override
    public void bind(FormFieldModelImageList model) {

    }

    @Override
    public int preferedViewId() {
        return 0;
    }
}
