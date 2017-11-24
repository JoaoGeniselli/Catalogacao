package br.com.jgeniselli.catalogacaolem.common.models;

import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.service.restModels.RestAntNest;
import br.com.jgeniselli.catalogacaolem.login.User;
import io.realm.Realm;

/**
 * Created by jgeniselli on 19/11/2017.
 */

public class RestAntNestRealmAdapter implements RealmAdapter<AntNest, RestAntNest> {

    @Override
    public AntNest adapt(RestAntNest object, Realm realm) {

        AntNest nest = new AntNest();
        nest.setNestId(object.getNestId());
        nest.setName(object.getName());
        nest.setAddress(object.getAddress());
        nest.setVegetation(object.getVegetation());
        nest.setLastDataUpdateDate(object.getLastDataUpdateDate());
        nest.setRegisterId(object.getRegisterId());

        CityModel city = realm
                .where(CityModel.class)
                .equalTo("id", object.getCityId())
                .findFirst();

        nest.setCity(city);

        CoordinateRepository coordinateRepo = new CoordinateRepository();

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude((Double) object.getBeginingPoint().get("latitude"));
        coordinate.setLongitude((Double) object.getBeginingPoint().get("longitude"));
        coordinateRepo.create(coordinate, realm);
        nest.setBeginingPoint(coordinate);

        coordinate = new Coordinate();
        coordinate.setLatitude((Double) object.getEndingPoint().get("latitude"));
        coordinate.setLongitude((Double) object.getEndingPoint().get("longitude"));
        coordinateRepo.create(coordinate, realm);
        nest.setEndingPoint(coordinate);

        return nest;
    }

    @Override
    public RestAntNest reverse(AntNest object, Realm realm) {

        return null;
    }
}
