package br.com.jgeniselli.catalogacaolem.common.location;

import java.io.Serializable;

/**
 * Created by joaog on 05/08/2017.
 */

public class RestCityModel implements Serializable {

    private Long id;
    private String name;

    public RestCityModel() {

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
