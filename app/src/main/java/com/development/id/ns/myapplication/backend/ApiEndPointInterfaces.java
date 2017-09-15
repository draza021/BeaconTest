
/*
 * ApiEndPointInterfaces.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.backend;

import com.development.id.ns.myapplication.backend.json.LoginRequest;
import com.development.id.ns.myapplication.backend.json.Restaurants;
import com.development.id.ns.myapplication.backend.json.RestaurantsRequest;
import com.development.id.ns.myapplication.login.LoginCredentials;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiEndPointInterfaces {

    @Headers( "Content-Type: application/json" )
    @POST("customer/login?app_version=2.8.1")
    Call<LoginRequest> loginWithCredentials(@Body LoginCredentials data);

    @Headers( "Content-Type: application/json" )
    @POST("restaurant/info?app_version=2.8.1")
    Call<Restaurants> getRestaurants(@Body RestaurantsRequest data);

}
