package br.com.jgeniselli.catalogacaolem.common.models;

import java.lang.reflect.Field;

import io.realm.Realm;

/**
 * Created by jgeniselli on 19/11/2017.
 */

public class AntNestRepository extends BaseModelRepository<AntNest> {

    public AntNestRepository() {
        super(AntNest.class);
    }

    @Override
    protected void setIdToEntity(long id, AntNest object) {
        object.setNestId(id);
    }

    @Override
    protected String getPrimaryKeyName() {
        return "nestId";
    }

    @Override
    public void cloneToDestiny(AntNest source, AntNest destiny) {
        destiny.updateFrom(source);
    }
}
