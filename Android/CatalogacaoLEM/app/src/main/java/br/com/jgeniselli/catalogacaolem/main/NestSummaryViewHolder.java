package br.com.jgeniselli.catalogacaolem.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.view.BindableView;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;

/**
 * Created by joaog on 12/08/2017.
 */

public class NestSummaryViewHolder extends RecyclerView.ViewHolder implements BindableView<AntNest> {

    public TextView nameLabel;
    public TextView addressLabel;
    public TextView vegetationLabel;
    public ImageView newImageView;

    private WeakReference<AntNest> model;

    public NestSummaryViewHolder(View itemView) {
        super(itemView);

        nameLabel = (TextView) itemView.findViewById(R.id.nest_summary_name);
        addressLabel = (TextView) itemView.findViewById(R.id.nest_summary_address);
        vegetationLabel = (TextView) itemView.findViewById(R.id.nest_summary_vegetation);
        newImageView = (ImageView) itemView.findViewById(R.id.newImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model != null) {
                    EventBus.getDefault().post(new NestDetailsRequestEvent(model.get()));
                }
            }
        });
    }

    @Override
    public void bind(AntNest nest) {
        if (nest == null) {
            return;
        }
        model = new WeakReference<>(nest);
        nameLabel.setText(nest.getName());
        addressLabel.setText(nest.getCompleteAddress());
        vegetationLabel.setText(nest.getVegetation());
        newImageView.setVisibility(nest.isRegistered() ? View.GONE : View.VISIBLE);
    }
}