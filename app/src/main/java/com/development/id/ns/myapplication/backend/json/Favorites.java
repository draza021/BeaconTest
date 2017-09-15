
/*
 * Favorites.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.backend.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Favorites {

    @SerializedName("items")
    @Expose
    private List<Object> items = null;
    @SerializedName("restaurants")
    @Expose
    private List<Object> restaurants = null;

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public List<Object> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Object> restaurants) {
        this.restaurants = restaurants;
    }

}
