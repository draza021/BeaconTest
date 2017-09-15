
/*
 * LoginRequest.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.backend.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("favorites")
    @Expose
    private Favorites favorites;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

}
