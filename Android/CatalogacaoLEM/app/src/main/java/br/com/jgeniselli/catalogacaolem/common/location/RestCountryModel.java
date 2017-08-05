package br.com.jgeniselli.catalogacaolem.common.location;

import java.util.List;

/**
 * Created by joaog on 05/08/2017.
 */

public class RestCountryModel {

    private Long id;
    private String name;
    private List<RestStateModel> states;

    public RestCountryModel() {

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

    public List<RestStateModel> getStates() {
        return states;
    }

    public void setStates(List<RestStateModel> states) {
        this.states = states;
    }
}
