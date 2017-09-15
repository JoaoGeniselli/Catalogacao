package br.com.jgeniselli.catalogacaolem.common.form.view;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import io.realm.RealmList;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class ThumbnailLineAdapter extends RecyclerView.Adapter<ThumbnailViewHolder> {

    private RealmList<PhotoModel> photoModels;

    public ThumbnailLineAdapter(RealmList<PhotoModel> photoModels) {
        this.photoModels = photoModels;
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (photoModels == null) return 0;
        return photoModels.size();
    }
}
