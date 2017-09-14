package br.com.jgeniselli.catalogacaolem.common.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 16/08/17.
 */

public class DefaultLineAdapter extends RecyclerView.Adapter<DefaultLineViewHolder> {

    private WeakReference<View.OnClickListener> weakClickListener;
    private ArrayList<String> values;

    public DefaultLineAdapter(ArrayList<String> values, View.OnClickListener weakClickListener) {
        if (weakClickListener != null) {
            this.weakClickListener = new WeakReference<View.OnClickListener>(weakClickListener);
        }
        this.values = values;
    }

    @Override
    public DefaultLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_view_default, parent, false);

        if (weakClickListener != null) {
            view.setOnClickListener(weakClickListener.get());
        }
        return new DefaultLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DefaultLineViewHolder holder, int position) {
        String currentValue = values.get(position);
        holder.bind(currentValue);
    }

    @Override
    public int getItemCount() {
        if (values == null) {
            return 0;
        }
        return values.size();
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
        notifyDataSetChanged();
    }
}
