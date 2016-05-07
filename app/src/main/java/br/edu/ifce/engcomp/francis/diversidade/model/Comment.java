package br.edu.ifce.engcomp.francis.diversidade.model;

import java.io.Serializable;

/**
 * Created by Joamila on 10/04/2016.
 */
public class Comment implements Serializable {
    private String author;
    private String date;
    private String content;
    private int idNucleus;

    public Comment(String author, String content, String date){
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdNucleus() { return idNucleus; }

    public void setIdNucleus(int idNucleus) { this.idNucleus = idNucleus; }
}
