package br.edu.ifce.engcomp.francis.diversidade.model;

import java.io.Serializable;

/**
 * Created by Bolsista on 07/04/2016.
 */
public class Contact implements Serializable {
    String media;
    String path;

    public Contact(String media, String path) {
        this.media = media;
        this.path = path;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
