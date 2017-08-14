package br.com.jgeniselli.catalogacaolem.common.location;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joaog on 05/08/2017.
 */

public class StateModel extends RealmObject {

    @PrimaryKey
    private Long id;

    private String initials;

    private String name;

    private CountryModel country;

    public StateModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }
}
