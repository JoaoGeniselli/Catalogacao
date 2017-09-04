package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.location.CitySynchronization;
import io.realm.RealmResults;

/**
 * Created by joaog on 12/08/2017.
 */

public class CitiesListLineAdapter extends RecyclerView.Adapter<CitiesListLineAdapter.CitySynchronizationViewHolder> {

    private RealmResults<CitySynchronization> cities;

    public CitiesListLineAdapter(RealmResults<CitySynchronization> synchronizationCities) {
        this.cities = synchronizationCities;
    }

    @Override
    public CitySynchronizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_view_city_synchronization, parent, false);
        return new CitySynchronizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitySynchronizationViewHolder holder, int position) {
        CitySynchronization city = cities.get(position);
        holder.bind(city);
    }

    @Override
    public int getItemCount() {
        if (cities == null) return 0;
        return cities.size();
    }

    public RealmResults<CitySynchronization> getCities() {
        return cities;
    }

    public void setCities(RealmResults<CitySynchronization> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public static class CitySynchronizationViewHolder extends RecyclerView.ViewHolder{

        private WeakReference<CitySynchronization> model;
        private WeakReference<TextView> textView;
        private WeakReference<Button> removeButton;

        public CitySynchronizationViewHolder(View itemView) {
            super(itemView);

            textView = new WeakReference<>((TextView)itemView.findViewById(R.id.textView));
            removeButton = new WeakReference<>((Button)itemView.findViewById(R.id.removeButton));

            removeButton.get().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    triggerModelRemoval();
                }
            });
        }

        public void bind(CitySynchronization model) {
            this.model = new WeakReference<>(model);
            if (model.getCity() != null) {
                textView.get().setText(String.format("%s - %s",
                        model.getCity().getState().getName(),
                        model.getCity().getName()));
            }
        }

        public void triggerModelRemoval() {
            if (model != null) {
                EventBus.getDefault().post(new SyncCityRemoveRequestEvent(model.get()));
            }
        }
    }
}
