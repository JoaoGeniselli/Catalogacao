package br.com.jgeniselli.catalogacaolem.common.models;

/**
 * Created by jgeniselli on 19/11/2017.
 */

public class CoordinateRepository extends BaseModelRepository<Coordinate> {

    public CoordinateRepository() {
        super(Coordinate.class);
    }

    @Override
    protected void setIdToEntity(long id, Coordinate object) {
        object.setId(id);
    }

    @Override
    protected String getPrimaryKeyName() {
        return "id";
    }

    @Override
    public void cloneToDestiny(Coordinate source, Coordinate destiny) {

    }
}
