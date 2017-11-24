package br.com.jgeniselli.catalogacaolem.common.models;

/**
 * Created by jgeniselli on 19/11/2017.
 */

public class PhotoModelRepository extends BaseModelRepository<PhotoModel> {

    public PhotoModelRepository() {
        super(PhotoModel.class);
    }

    @Override
    protected void setIdToEntity(long id, PhotoModel object) {
        object.setPhotoId(id);
    }

    @Override
    protected String getPrimaryKeyName() {
        return "photoId";
    }

    @Override
    public void cloneToDestiny(PhotoModel source, PhotoModel destiny) {

    }
}
