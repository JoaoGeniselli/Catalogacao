package br.com.jgeniselli.catalogacaolem.nestDetails;

import br.com.jgeniselli.catalogacaolem.common.models.Ant;

/**
 * Created by jgeniselli on 10/09/17.
 */

public class AntDetailRequestEvent {

    private Ant ant;

    public AntDetailRequestEvent(Ant ant) {
        this.ant = ant;
    }

    public Ant getAnt() {
        return ant;
    }
}
