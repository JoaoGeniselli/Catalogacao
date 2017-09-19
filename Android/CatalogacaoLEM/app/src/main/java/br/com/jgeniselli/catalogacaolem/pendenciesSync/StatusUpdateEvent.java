package br.com.jgeniselli.catalogacaolem.pendenciesSync;

/**
 * Created by jgeniselli on 19/09/17.
 */

public class StatusUpdateEvent {

    private int messageId;

    public StatusUpdateEvent(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }
}
