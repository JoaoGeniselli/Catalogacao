package br.com.jgeniselli.catalogacaolem.common.form.model;

import android.net.Uri;

/**
 * Created by jgeniselli on 20/11/2017.
 */

public class PlaceholderPhotoModel {

    private Long photoId;
    private Long registerId;
    private String filePath;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
