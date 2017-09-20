package br.com.jgeniselli.catalogacaolem.common.models;

import android.net.Uri;

import org.greenrobot.eventbus.EventBus;

import br.com.jgeniselli.catalogacaolem.common.form.event.ImageRemovalRequestEvent;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jgeniselli on 03/09/17.
 */

public class PhotoModel extends RealmObject {

    @PrimaryKey
    private Long photoId;

    private Long registerId;
    private String filePath;
    private String description;

    public PhotoModel() {

    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void requestRemoval() {
        EventBus.getDefault().post(new ImageRemovalRequestEvent(this));
    }

    public Uri getFileURI() {
        Uri uri = null;
        try {
            uri = Uri.parse(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            uri = null;
        }
        return uri;
    }

    public void setFileURI(Uri uri) {
        filePath = uri.toString();
    }
}
