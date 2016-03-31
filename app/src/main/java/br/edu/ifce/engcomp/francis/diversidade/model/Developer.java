package br.edu.ifce.engcomp.francis.diversidade.model;

import android.net.Uri;

/**
 * Created by Joamila on 30/03/2016.
 */
public class Developer {
    private String name;
    private Uri urlFacebook;
    private Uri urlTwitter;
    private Uri urlInstagram;
    private Uri urlGithub;

    public Developer(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUrlFacebook() {
        return urlFacebook;
    }

    public void setUrlFacebook(Uri urlFacebook) {
        this.urlFacebook = urlFacebook;
    }

    public Uri getUrlTwitter() {
        return urlTwitter;
    }

    public void setUrlTwitter(Uri urlTwitter) {
        this.urlTwitter = urlTwitter;
    }

    public Uri getUrlInstagram() {
        return urlInstagram;
    }

    public void setUrlInstagram(Uri urlInstagram) {
        this.urlInstagram = urlInstagram;
    }

    public Uri getUrlGithub() {
        return urlGithub;
    }

    public void setUrlGithub(Uri urlGithub) {
        this.urlGithub = urlGithub;
    }

}
