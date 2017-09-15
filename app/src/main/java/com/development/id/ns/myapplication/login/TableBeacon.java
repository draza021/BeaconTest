
/*
 * TableBeacon.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableBeacon {

    @SerializedName("major")
    @Expose
    private Integer major;
    @SerializedName("minor")
    @Expose
    private Integer minor;

    public TableBeacon(Integer major, Integer minor) {
        this.major = major;
        this.minor = minor;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }
}
