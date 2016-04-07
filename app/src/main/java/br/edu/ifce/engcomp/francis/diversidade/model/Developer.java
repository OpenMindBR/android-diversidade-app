package br.edu.ifce.engcomp.francis.diversidade.model;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Joamila on 30/03/2016.
 */
public class Developer implements Serializable {
    private String name;
    private ArrayList<Contact> contacts;

    public Developer(String name){
        this.name = name;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
