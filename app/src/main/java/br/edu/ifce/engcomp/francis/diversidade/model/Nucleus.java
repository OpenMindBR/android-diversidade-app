package br.edu.ifce.engcomp.francis.diversidade.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bolsista on 12/04/2016.
 */
public class Nucleus implements Serializable {
    String name;
    String phone;
    String email;
    String site;
    AddressNucleus address;
    HourNucleus hour;
    ArrayList<Comment> comments;
    ArrayList<Service> services;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public AddressNucleus getAddress() {
        return address;
    }

    public void setAddress(AddressNucleus address) {
        this.address = address;
    }

    public HourNucleus getHour() {
        return hour;
    }

    public void setHour(HourNucleus hour) {
        this.hour = hour;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
}
