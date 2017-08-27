package br.com.jgeniselli.catalogacaolem.common.form;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static br.com.jgeniselli.catalogacaolem.common.form.FormFieldModelType.TEXT;

/**
 * Created by jgeniselli on 26/08/17.
 */

public class FormViewHolderFactory {

    private RecyclerView.ViewHolder getViewHolderByType(FormFieldModelType type, View view) {

        switch (type) {
            case TEXT:
                return new TextFormFieldViewHolder(view);
            case NUMBER:
                return new TextFormFieldViewHolder(view); // TODO: MUDAR
            case COORDINATE:
                return new CoordinatesFormFieldViewHolder(view);
            case CITY:
                return new CityFormFieldViewHolder(view);
            default:
                return new TextFormFieldViewHolder(view);
        }
    }

    private int getLayoutFromType(FormFieldModelType type) {
        return type.getViewId();
    }

    public RecyclerView.ViewHolder getRecyclerViewHolderByType(Context context, ViewGroup parent, FormFieldModelType type) {
        View view = LayoutInflater.from(context).inflate(getLayoutFromType(type), parent, false);
        return getViewHolderByType(type, view);
    }
}
