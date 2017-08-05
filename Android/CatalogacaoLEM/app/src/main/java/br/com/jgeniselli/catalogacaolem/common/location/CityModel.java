package br.com.jgeniselli.catalogacaolem.common.location;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by joaog on 05/08/2017.
 */

public class CityModel extends RealmObject {

    @Index
    private Long id;

    private String name;

    private StateModel state;

    public CityModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateModel getState() {
        return state;
    }

    public void setState(StateModel state) {
        this.state = state;
    }
}
