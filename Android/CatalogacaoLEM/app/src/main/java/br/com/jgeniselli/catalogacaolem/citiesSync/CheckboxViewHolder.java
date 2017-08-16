package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.BindableView;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;

/**
 * Created by joaog on 12/08/2017.
 */

public class CheckboxViewHolder extends RecyclerView.ViewHolder implements BindableView {

    private TextView titleLabel;
    private ImageView selectionIndicator;
    private Integer tag;

    private boolean selected;

    public CheckboxViewHolder(View itemView, boolean selected) {
        super(itemView);

        titleLabel = (TextView) itemView.findViewById(R.id.checkboxTitleLabel);
        selectionIndicator = (ImageView) itemView.findViewById(R.id.checkboxSelectionIndicator);
        itemView.setTag(this);

        setSelected(selected);
    }

    @Override
    public void bind(Object model) {
        if (model == null) {
            return;
        }
        setSelected(false);
        CityModel city = (CityModel) model;
        titleLabel.setText(city.getState().getInitials() + " - " + city.getName());
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;

        if (selectionIndicator != null) {
            selectionIndicator.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public TextView getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(TextView titleLabel) {
        this.titleLabel = titleLabel;
    }

    public ImageView getSelectionIndicator() {
        return selectionIndicator;
    }

    public void setSelectionIndicator(ImageView selectionIndicator) {
        this.selectionIndicator = selectionIndicator;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
