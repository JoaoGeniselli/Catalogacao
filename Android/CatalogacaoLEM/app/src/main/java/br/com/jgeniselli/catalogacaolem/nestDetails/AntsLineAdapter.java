package br.com.jgeniselli.catalogacaolem.nestDetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.view.BindableViewHolder;
import br.com.jgeniselli.catalogacaolem.common.models.Ant;

/**
 * Created by jgeniselli on 09/09/17.
 */

public class AntsLineAdapter extends RecyclerView.Adapter<AntsLineAdapter.AntSummaryViewHolder> {

    private WeakReference<List<Ant>> ants;

    public AntsLineAdapter(List<Ant> ants) {
        if (ants != null) {
            this.ants = new WeakReference<>(ants);
        }
    }

    @Override
    public AntSummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_view_ant_summary, parent, false);
        return new AntSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AntSummaryViewHolder holder, int position) {
        Ant ant = ants.get().get(position);
        holder.bind(ant);
    }

    @Override
    public int getItemCount() {
        if (ants == null) {
            return 0;
        }
        return ants.get().size();
    }

    public static class AntSummaryViewHolder extends BindableViewHolder<Ant> {
        private WeakReference<TextView> titleView;
        private WeakReference<TextView> detailView;

        private WeakReference<Ant> model;

        public AntSummaryViewHolder(View itemView) {
            super(itemView);
            titleView  = new WeakReference(itemView.findViewById(R.id.titleView));
            detailView = new WeakReference(itemView.findViewById(R.id.detailView));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    triggerDetailRequest();
                }
            });
        }

        @Override
        public void bind(Ant model) {
            if (model != null) {
                this.model = new WeakReference<Ant>(model);
                titleView.get().setText(model.getName());
                detailView.get().setText(model.getAntSpecies());
            }
        }

        private void triggerDetailRequest() {
            if (this.model != null && this.model.get() != null) {
                EventBus.getDefault().post(new AntDetailRequestEvent(model.get()));
            }
        }
    }
}
