package br.com.jgeniselli.catalogacaolem.common.form.model;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.common.form.event.ImageRemovalRequestEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.ImageRequestEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.ImageResponseEvent;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import io.realm.RealmList;

/**
 * Created by jgeniselli on 03/09/17.
 */

public class FormFieldModelImageList extends FormFieldModel {

    private RealmList<PhotoModel> images;

    public FormFieldModelImageList(int id, int order, String title, String tag) {
        super(id, order, title, tag);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.IMAGE_LIST;
    }

    public RealmList<PhotoModel> getImages() {
        if (images == null) {
            images = new RealmList<>();
        }
        return images;
    }

    public void setImages(RealmList<PhotoModel> images) {
        this.images = images;
    }

    public void requestImage() {
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(new ImageRequestEvent());
    }

    @Subscribe
    public void onImageResponseEvent(ImageResponseEvent event) {
        getImages().add(event.getPhotoModel());
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestImageRemoval(PhotoModel image) {
        try {
            images.remove(image);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(new ImageRemovalRequestEvent(image));
    }
}
