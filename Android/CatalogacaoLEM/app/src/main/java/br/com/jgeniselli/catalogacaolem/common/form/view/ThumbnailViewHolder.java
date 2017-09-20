package br.com.jgeniselli.catalogacaolem.common.form.view;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelImageList;
import br.com.jgeniselli.catalogacaolem.common.form.utils.ThumbnailUtil;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import br.com.jgeniselli.catalogacaolem.common.view.BindableViewHolder;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class ThumbnailViewHolder extends BindableViewHolder<PhotoModel> {

    private WeakReference<PhotoModel> photoModel;
    private WeakReference<FormFieldModelImageList> formModel;

    private ImageButton removeButton;
    private ImageView imageView;

    public ThumbnailViewHolder(View itemView, final FormFieldModelImageList __formModel) {
        super(itemView);

        this.formModel = new WeakReference<FormFieldModelImageList>(__formModel);

        removeButton = (ImageButton) itemView.findViewById(R.id.removeButton);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formModel != null && formModel.get() != null &&
                        photoModel != null && photoModel.get() != null) {
                    formModel.get().requestImageRemoval(photoModel.get());
                }
            }
        });
    }

    @Override
    public void bind(PhotoModel model) {
        this.photoModel = new WeakReference<>(model);
        RoundedBitmapDrawable thumbnail = ThumbnailUtil.getThumbailFromUri(itemView.getContext(), model.getFileURI());
        if (thumbnail != null)
            imageView.setImageDrawable(thumbnail);
    }
}
