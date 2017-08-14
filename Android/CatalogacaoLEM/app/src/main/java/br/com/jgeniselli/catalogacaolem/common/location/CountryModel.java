package br.com.jgeniselli.catalogacaolem.common.location;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joaog on 05/08/2017.
 */

public class CountryModel extends RealmObject {

    @PrimaryKey
    private Long id;

    private String name;

    public CountryModel() {

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
}
