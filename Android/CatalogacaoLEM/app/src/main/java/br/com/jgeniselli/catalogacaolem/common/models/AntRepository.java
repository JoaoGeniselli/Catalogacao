package br.com.jgeniselli.catalogacaolem.common.models;

/**
 * Created by jgeniselli on 19/11/2017.
 */

public class AntRepository extends BaseModelRepository<Ant> {

    public AntRepository() {
        super(Ant.class);
    }

    @Override
    protected void setIdToEntity(long id, Ant object) {
        object.setId(id);
    }

    @Override
    protected String getPrimaryKeyName() {
        return "id";
    }

    @Override
    public void cloneToDestiny(Ant source, Ant destiny) {

    }
}
