package br.com.jgeniselli.catalogacaolem.common.form;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormLineAdapter extends RecyclerView.Adapter<FormFieldViewHolder>{

    private Context context;
    private FormModel form;
    private FormViewHolderFactory viewHolderFactory;

    private HashMap<FormFieldModelType, Integer> viewIdsByFieldType;

    public FormLineAdapter(Context context, FormModel form) {
        this.viewHolderFactory = new FormViewHolderFactory();
        this.context = context;
        this.form = form;
    }

    @Override
    public FormFieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (FormFieldViewHolder) viewHolderFactory
                .getRecyclerViewHolderByType(context, parent, FormFieldModelType.typeForId(viewType));
    }

    @Override
    public void onBindViewHolder(FormFieldViewHolder holder, int position) {
        FormFieldModel model = form.getFields().get(position);
        holder.bind(model);
    }

    @Override
    public int getItemViewType(int position) {
        FormFieldModel model = form.getFields().get(position);
        return model.getType().getId();
    }

    @Override
    public int getItemCount() {
        return form.getFields().size();
    }
}
