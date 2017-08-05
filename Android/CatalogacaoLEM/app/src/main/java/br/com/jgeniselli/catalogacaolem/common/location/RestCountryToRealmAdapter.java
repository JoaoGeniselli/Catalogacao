package br.com.jgeniselli.catalogacaolem.common.location;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by joaog on 05/08/2017.
 */

public class RestCountryToRealmAdapter {

    public static void saveCountryModel(RestCountryModel countryModel) {
        Realm realm = Realm.getDefaultInstance();

        final CountryModel country = new CountryModel();

        country.setId(countryModel.getId());
        country.setName(countryModel.getName());

        realm.copyToRealmOrUpdate(country);

        final ArrayList<StateModel> states = new ArrayList<>();
        final ArrayList<CityModel> cities = new ArrayList<>();

        for (RestStateModel stateModel : countryModel.getStates()) {
            StateModel state = new StateModel();

            state.setId(stateModel.getId());
            state.setInitials(stateModel.getInitials());
            state.setName(stateModel.getName());
            state.setCountry(country);

            states.add(state);

            for (RestCityModel cityModel : stateModel.getCities()) {
                CityModel city = new CityModel();
                city.setId(cityModel.getId());
                city.setName(cityModel.getName());
                city.setState(state);

                cities.add(city);
            }
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(country);

                for (StateModel state: states) {
                    realm.copyToRealmOrUpdate(state);
                }
                for (CityModel city : cities) {
                    realm.copyToRealmOrUpdate(cities);
                }
            }
        });
    }
}
