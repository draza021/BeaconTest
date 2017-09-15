
/*
 * BeaconTest.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication;

import android.app.Application;

import com.development.id.ns.myapplication.backend.RestApi;
import com.facebook.stetho.Stetho;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BeaconTest extends Application {
    public static BeaconTest instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initApplication();
    }

    private void initApplication() {
        setRestApi();
        Picasso picassoInstance = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(RestApi.getOkHttpClient()))
                .indicatorsEnabled(BuildConfig.DEBUG)
                .loggingEnabled(BuildConfig.DEBUG)
                .build();
        Picasso.setSingletonInstance(picassoInstance);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void setRestApi() {
        RestApi.setContext(getCacheDir());
    }
}
