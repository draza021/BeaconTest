
/*
 * SplashActivity.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.development.id.ns.myapplication.R;
import com.development.id.ns.myapplication.model.Constants;

import static com.development.id.ns.myapplication.model.Constants.ACCESS_TOKEN;
import static com.development.id.ns.myapplication.model.Constants.MyPREFERENCES;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.getInt(Constants.PREFS_IMAGE_WEIGHT, 0) == 0) {
            int weight = calculateWeight();
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            sharedpreferences.edit().putInt(Constants.PREFS_IMAGE_WEIGHT, weight).apply();
        }

        String accessToken = sharedpreferences.getString(ACCESS_TOKEN, null);
        if (!TextUtils.isEmpty(accessToken)) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(ACCESS_TOKEN, accessToken);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }


    private int calculateWeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        float widthPixels = displayMetrics.widthPixels;
        float heightPixels = displayMetrics.heightPixels;
        Log.e("TAG", "initial width: " + widthPixels);

        int desiredHeight = (int) ((widthPixels / 100f) * 66f);
        return (int) ((desiredHeight / heightPixels) * 100f);
    }
}
