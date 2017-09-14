package br.com.jgeniselli.catalogacaolem.nestDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang3.StringUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.view.BindableViewHolder;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;

/**
 * Created by jgeniselli on 07/09/17.
 */

public class NestInfoLineAdapter extends RecyclerView.Adapter<BindableViewHolder> {


    private ArrayList<HashMap> nestPropertyInfo;
    private WeakReference<Context> context;

    public NestInfoLineAdapter(Context context, AntNest nest) {
        this.context = new WeakReference<Context>(context);
        updateNestInfo(nest);
    }

    private void updateNestInfo(AntNest nest) {
        nestPropertyInfo = new ArrayList<>();

        HashMap propertyInfo = new HashMap();
        propertyInfo.put("type", 2);
        propertyInfo.put("title", context.get().getString(R.string.nest_id));
        if (nest.isRegistered()) {
            propertyInfo.put("detail", String.format("%d", nest.getNestId()));
        } else {
            propertyInfo.put("detail", context.get().getString(R.string.new_nest));
        }
        nestPropertyInfo.add(propertyInfo);

        propertyInfo = new HashMap();
        propertyInfo.put("type", 2);
        propertyInfo.put("title", context.get().getString(R.string.name));
        propertyInfo.put("detail", StringUtils.defaultIfEmpty(nest.getName(), "-"));
        nestPropertyInfo.add(propertyInfo);

        propertyInfo = new HashMap();
        propertyInfo.put("type", 2);
        propertyInfo.put("title", context.get().getString(R.string.city));
        propertyInfo.put("detail", String.format("%s, %s/%s",
                nest.getCity().getState().getCountry().getName(),
                nest.getCity().getName(),
                nest.getCity().getState().getInitials()));
        nestPropertyInfo.add(propertyInfo);

        propertyInfo = new HashMap();
        propertyInfo.put("type", 2);
        propertyInfo.put("title", context.get().getString(R.string.address));
        propertyInfo.put("detail", StringUtils.defaultIfEmpty(nest.getAddress(), "-"));
        nestPropertyInfo.add(propertyInfo);

        propertyInfo = new HashMap();
        propertyInfo.put("type", 2);
        propertyInfo.put("title", context.get().getString(R.string.vegetation));
        propertyInfo.put("detail", StringUtils.defaultIfEmpty(nest.getVegetation(), "-"));
        nestPropertyInfo.add(propertyInfo);

        propertyInfo = new HashMap();
        propertyInfo.put("type", 2);
        propertyInfo.put("title", context.get().getString(R.string.initial_point));
        propertyInfo.put("detail", nest.getBeginingPoint().getFormattedPosition());
        nestPropertyInfo.add(propertyInfo);

        propertyInfo = new HashMap();
        propertyInfo.put("type", 2);
        propertyInfo.put("title", context.get().getString(R.string.final_point));
        propertyInfo.put("detail", nest.getEndingPoint().getFormattedPosition());
        nestPropertyInfo.add(propertyInfo);
    }

    @Override
    public int getItemViewType(int position) {
        HashMap propertyInfo = nestPropertyInfo.get(position);

        if (propertyInfo.containsKey("type"))  {
            return (Integer) propertyInfo.get("type");
        }
        return 0;
    }

    @Override
    public BindableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        BindableViewHolder holder = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.line_view_map_nest_info, parent, false);
                holder = new MapViewHolder(view);
                break;
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.line_view_title_detail_nest_info, parent, false);
                holder = new TitleDetailViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(BindableViewHolder holder, int position) {
        holder.bind(nestPropertyInfo.get(position));
    }

    @Override
    public int getItemCount() {

        return nestPropertyInfo.size();
    }

    private static class MapViewHolder extends BindableViewHolder<HashMap> implements OnMapReadyCallback {

        private GoogleMap map;
        private LatLng initialPoint;
        private LatLng finalPoint;


        public MapViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(HashMap model) {

            LatLng point;

            if (model.containsKey("initialPoint")) {
                initialPoint = (LatLng) model.get("initialPoint");
            }

            if (model.containsKey("finalPoint")) {
                finalPoint = (LatLng) model.get("finalPoint");
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {

        }
    }

    private static class TitleDetailViewHolder extends BindableViewHolder<HashMap> {

        private WeakReference<TextView> titleView;
        private WeakReference<TextView> detailView;

        public TitleDetailViewHolder(View itemView) {
            super(itemView);

            titleView = new WeakReference<>(
                    (TextView) itemView.findViewById(R.id.titleView));
            detailView = new WeakReference<>(
                    (TextView) itemView.findViewById(R.id.detailView));
        }

        @Override
        public void bind(HashMap model) {
            if (model.containsKey("title") && model.get("title") != null) {
                titleView.get().setText((CharSequence) model.get("title"));
            }
            if (model.containsKey("detail") && model.get("detail") != null) {
                detailView.get().setText((CharSequence) model.get("detail"));
            }
        }
    }
}
