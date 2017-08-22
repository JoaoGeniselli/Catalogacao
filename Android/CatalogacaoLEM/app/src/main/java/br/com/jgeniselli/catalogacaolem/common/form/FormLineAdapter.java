package br.com.jgeniselli.catalogacaolem.common.form;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormLineAdapter extends RecyclerView.Adapter{

    private Context context;
    private FormModel form;

    private HashMap<FormFieldModelType, Integer> viewIdsByFieldType;

    public FormLineAdapter(Context context, FormModel form) {
        this.context = context;
        this.form = form;
    }

    private HashMap<FormFieldModelType, Integer> getViewIdsByFieldType() {
        if (viewIdsByFieldType == null) {
            viewIdsByFieldType = new HashMap<>();
            viewIdsByFieldType.put(FormFieldModelType.NONE, 0);
            viewIdsByFieldType.put(FormFieldModelType.TEXT, 0);
            viewIdsByFieldType.put(FormFieldModelType.NUMBER, 0);
            viewIdsByFieldType.put(FormFieldModelType.COORDINATE, 0);
        }
        return viewIdsByFieldType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
