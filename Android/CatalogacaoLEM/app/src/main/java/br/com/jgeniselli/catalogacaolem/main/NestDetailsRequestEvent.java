package br.com.jgeniselli.catalogacaolem.main;

import br.com.jgeniselli.catalogacaolem.common.models.AntNest;

/**
 * Created by jgeniselli on 03/09/17.
 */

public class NestDetailsRequestEvent {

    private AntNest nest;

    public NestDetailsRequestEvent(AntNest nest) {
        this.nest = nest;
    }

    public AntNest getNest() {
        return nest;
    }
}
