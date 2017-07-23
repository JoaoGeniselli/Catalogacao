package br.com.jgeniselli.catalogacaoWS.model;

/**
 * Created by joaog on 27/05/2017.
 */

public class Photo {

    public String description;
    private String filename;

    public Photo(String description, String filename) {
        this.description = description;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
