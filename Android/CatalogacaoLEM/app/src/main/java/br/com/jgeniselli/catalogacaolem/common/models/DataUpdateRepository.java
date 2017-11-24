package br.com.jgeniselli.catalogacaolem.common.models;

/**
 * Created by jgeniselli on 19/11/2017.
 */

public class DataUpdateRepository extends BaseModelRepository<DataUpdateVisit> {

    public DataUpdateRepository() {
        super(DataUpdateVisit.class);
    }

    @Override
    protected void setIdToEntity(long id, DataUpdateVisit object) {
        object.setDataUpdateId(id);
    }

    @Override
    protected String getPrimaryKeyName() {
        return "dataUpdateId";
    }

    @Override
    public void cloneToDestiny(DataUpdateVisit source, DataUpdateVisit destiny) {

    }
}
