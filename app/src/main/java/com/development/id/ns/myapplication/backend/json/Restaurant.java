
/*
 * Restaurant.java
 *
 * Created by Drago on 9/14/2017
 */

package com.development.id.ns.myapplication.backend.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("intro")
    @Expose
    private String intro;
    @SerializedName("welcome_message")
    @Expose
    private String welcomeMessage;
    @SerializedName("is_open")
    @Expose
    private Boolean isOpen;
    @SerializedName("images")
    @Expose
    private Images images;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}
