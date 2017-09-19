package br.com.jgeniselli.catalogacaolem.pendenciesSync;

/**
 * Created by jgeniselli on 19/09/17.
 */

public class TaggedCounterDecreaseEvent {

    private int tag;

    public TaggedCounterDecreaseEvent(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }
}
