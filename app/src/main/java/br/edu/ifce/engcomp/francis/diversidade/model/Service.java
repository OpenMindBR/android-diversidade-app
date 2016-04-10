package br.edu.ifce.engcomp.francis.diversidade.model;

import java.io.Serializable;

/**
 * Created by Joamila on 10/04/2016.
 */
public class Service implements Serializable{
    private String name;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
