
/*
 * LoginCredentials.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.login;

import com.google.gson.annotations.SerializedName;

public class LoginCredentials {
    @SerializedName("email")
    private String userName;
    private String password;

    public LoginCredentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
