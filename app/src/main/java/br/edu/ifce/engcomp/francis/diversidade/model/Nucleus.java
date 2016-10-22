package br.edu.ifce.engcomp.francis.diversidade.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bolsista on 12/04/2016.
 */
public class Nucleus implements Serializable {
    int id;
    String name;
    String responsible;
    String phone;
    String email;
    String site;
    String type;
    AddressNucleus address;
    double latitude;
    double longitude;
    ArrayList<HourNucleus> hour;
    ArrayList<Comment> comments;
    ArrayList<Service> services;

    public Nucleus(int id, String name, String responsible, String phone, String email, String site, String type,
                   double latitude, double longitude, AddressNucleus address,ArrayList<Service> services){
        this.id = id;
        this.name = name;
        this.responsible = responsible;
        this.phone = phone;
        this.email = email;
        this.site = site;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.services = services;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public double getLatitude() { return latitude; }

    public void setLatitude(long latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(long longitude) { this.longitude = longitude; }

    public ArrayList<HourNucleus> getHour() {
        return hour;
    }

    public void setHour(ArrayList<HourNucleus> hour) {
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

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
