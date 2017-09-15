package br.com.jgeniselli.catalogacaolem.common.form.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import br.com.jgeniselli.catalogacaolem.common.view.BindableViewHolder;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class ThumbnailViewHolder extends BindableViewHolder<PhotoModel> {

    private WeakReference<PhotoModel> model;

    private ImageButton removeButton;
    private ImageView imageView;

    public ThumbnailViewHolder(View itemView) {
        super(itemView);

        removeButton = (ImageButton) itemView.findViewById(R.id.removeButton);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model != null && model.get() != null) {
                    model.get().requestRemoval();
                }
            }
        });
    }

    @Override
    public void bind(PhotoModel model) {
        this.model = new WeakReference<>(model);
    }


}
