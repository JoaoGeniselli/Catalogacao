package br.com.jgeniselli.catalogacaolem.common.form.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelImageList;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import io.realm.RealmList;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class ThumbnailLineAdapter extends RecyclerView.Adapter<ThumbnailViewHolder> {

    private FormFieldModelImageList formModel;

    public ThumbnailLineAdapter() {

    }

    public FormFieldModelImageList getFormModel() {
        return formModel;
    }

    public void setFormModel(FormFieldModelImageList formModel) {
        this.formModel = formModel;
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_view_image_item, parent, false);
        return new ThumbnailViewHolder(view, formModel);
    }

    @Override
    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {
        PhotoModel model = formModel.getImages().get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        if (formModel == null) return 0;
        return formModel.getImages().size();
    }
}
