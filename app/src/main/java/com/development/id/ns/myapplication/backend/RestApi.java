
/*
 * RestApi.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.backend;

import android.util.Log;

import com.facebook.stetho.okhttp3.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {
    public static final String API_BASE_URL = "https://usemenu.com/playground/public/api/v2/";
    private static final long SIZE_OF_CACHE = 60 * 1024 * 1024; // 60 MB
    private static final long TIMEOUT_SECONDS = 30;

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    static public void setContext(File cacheDir) {
        if (okHttpClient != null)
            return;

        // Create Cache
        Cache cache = null;
        try {
            cache = new Cache(new File(cacheDir, "http"), SIZE_OF_CACHE);
        } catch (Exception e) {
            Log.e(RestApi.class.getSimpleName(), "Could not create Cache!", e);
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        HttpLoggingInterceptor.Level level = BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
        logging.setLevel(level);

        // Create OkHttpClient V3
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(logging)
                .build();


        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    static public ApiEndPointInterfaces getRestService() {
        return retrofit.create(ApiEndPointInterfaces.class);
    }

    static public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
