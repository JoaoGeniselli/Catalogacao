package br.com.jgeniselli.catalogacaolem.common.models;

import org.greenrobot.eventbus.EventBus;

import br.com.jgeniselli.catalogacaolem.common.form.event.PhotoModelRemovalRequest;
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
    private String thumbnailFilePath;
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public void setThumbnailFilePath(String thumbnailFilePath) {
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void requestRemoval() {
        EventBus.getDefault().post(new PhotoModelRemovalRequest(this));
    }
}
