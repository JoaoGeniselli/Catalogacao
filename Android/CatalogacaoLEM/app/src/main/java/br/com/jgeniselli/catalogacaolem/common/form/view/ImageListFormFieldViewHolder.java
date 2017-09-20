package br.com.jgeniselli.catalogacaolem.common.form.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.event.ImageRequestEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.ImageResponseEvent;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelImageList;

/**
 * Created by jgeniselli on 15/09/17.
 */

public class ImageListFormFieldViewHolder extends FormFieldViewHolder<FormFieldModelImageList> {

    private RecyclerView recyclerView;
    private ImageButton takePhotoButton;
    private WeakReference<FormFieldModelImageList> weakModel;

    public ImageListFormFieldViewHolder(View itemView) {
        super(itemView);

        takePhotoButton = (ImageButton) itemView.findViewById(R.id.takePhotoButton);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.imageRecycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                itemView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        ThumbnailLineAdapter adapter = new ThumbnailLineAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerImageRequest();
            }
        });
    }

    @Override
    public void bind(FormFieldModelImageList model) {
        weakModel = new WeakReference<>(model);
        ThumbnailLineAdapter adapter = (ThumbnailLineAdapter) recyclerView.getAdapter();
        adapter.setFormModel(model);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int preferedViewId() {
        return 0;
    }

    private void triggerImageRequest() {
        if (weakModel != null && weakModel.get() != null) {
            weakModel.get().requestImage();
        }
    }
}
