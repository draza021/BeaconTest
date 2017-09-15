
/*
 * RestaurantsRequest.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.backend.json;

import com.development.id.ns.myapplication.login.TableBeacon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantsRequest {

    @Expose
    @SerializedName("table_beacon")
    private TableBeacon tableBeacon;
    @Expose
    @SerializedName("access_token")
    private String accessToken;

    public RestaurantsRequest(TableBeacon tableBeacon, String accessToken) {
        this.tableBeacon = tableBeacon;
        this.accessToken = accessToken;
    }
}
