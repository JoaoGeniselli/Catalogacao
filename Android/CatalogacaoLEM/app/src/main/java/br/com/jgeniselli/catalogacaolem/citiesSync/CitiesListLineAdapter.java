package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import io.realm.RealmResults;

/**
 * Created by joaog on 12/08/2017.
 */

public class CitiesListLineAdapter extends RecyclerView.Adapter<CheckboxViewHolder> implements View.OnClickListener {

    private RealmResults<CityModel> cities;
    private WeakReference<CheckboxViewSelectionListener> wListener;

    public CitiesListLineAdapter(RealmResults<CityModel> cities, CheckboxViewSelectionListener selectionListener) {
        this.cities = cities;
        if (selectionListener != null) {
            wListener = new WeakReference<CheckboxViewSelectionListener>(selectionListener);
        }
    }

    @Override
    public CheckboxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_view_checkbox, parent, false);

        view.setOnClickListener(this);
        return new CheckboxViewHolder(view, false);
    }

    @Override
    public void onBindViewHolder(CheckboxViewHolder holder, int position) {
        holder.setTag(position);
        holder.bind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        if (cities == null) return 0;

        return cities.size();
    }

    @Override
    public void onClick(View v) {
        CheckboxViewHolder viewHolder = (CheckboxViewHolder) v.getTag();

        viewHolder.setSelected(!viewHolder.isSelected());

        if (wListener != null) {
            wListener.get().checkboxViewDidChangeSelection(viewHolder);
        }
    }

    public void setCities(RealmResults<CityModel> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }
}
