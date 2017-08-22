package br.com.jgeniselli.catalogacaolem.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.BindableView;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;

/**
 * Created by joaog on 12/08/2017.
 */

public class NestSummaryViewHolder extends RecyclerView.ViewHolder implements BindableView<AntNest> {

    public TextView nameLabel;
    public TextView addressLabel;
    public TextView vegetationLabel;

    public NestSummaryViewHolder(View itemView) {
        super(itemView);

        nameLabel = (TextView) itemView.findViewById(R.id.nest_summary_name);
        addressLabel = (TextView) itemView.findViewById(R.id.nest_summary_address);
        vegetationLabel = (TextView) itemView.findViewById(R.id.nest_summary_vegetation);
    }

    @Override
    public void bind(AntNest nest) {
        if (nest == null) {
            return;
        }
        nameLabel.setText(nest.getName());
        addressLabel.setText(nest.getCompleteAddress());
        vegetationLabel.setText(nest.getVegetation());
    }
}