
/*
 * DetailsActivity.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.development.id.ns.myapplication.R;
import com.development.id.ns.myapplication.backend.ApiEndPointInterfaces;
import com.development.id.ns.myapplication.backend.RestApi;
import com.development.id.ns.myapplication.backend.json.Restaurant;
import com.development.id.ns.myapplication.backend.json.Restaurants;
import com.development.id.ns.myapplication.backend.json.RestaurantsRequest;
import com.development.id.ns.myapplication.login.TableBeacon;
import com.development.id.ns.myapplication.model.Constants;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.development.id.ns.myapplication.model.Constants.ACCESS_TOKEN;
import static com.development.id.ns.myapplication.model.Constants.MyPREFERENCES;
import static com.development.id.ns.myapplication.model.Constants.PREFS_IMAGE_WEIGHT;

public class DetailsActivity extends AppCompatActivity {
    private ImageView restaurantImage;
    private TextView restaurantName, restaurantWelcomeMessage,
            restaurantIntro, restaurantOpenClosed;
    private ProgressBar progressBar;
    private static final String TAG = DetailsActivity.class.getSimpleName();
    //hardcoded for now
    private final int major = 5;
    private final int minor = 1;
    private SharedPreferences sharedPreferences;
    private int imageWeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        imageWeight = sharedPreferences.getInt(PREFS_IMAGE_WEIGHT, 0);
        initViews(imageWeight);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String accessToken = bundle.getString(ACCESS_TOKEN);
        if (accessToken != null && !accessToken.isEmpty()) {
            showProgress(View.VISIBLE);
            callService(accessToken);
        }
    }

    private void callService(String accessToken) {
        ApiEndPointInterfaces networkService = RestApi.getRestService();
        Call<Restaurants> call = networkService.getRestaurants(new RestaurantsRequest(new TableBeacon(major, minor), accessToken));
        call.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                DetailsActivity.this.onResponse(response);
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                DetailsActivity.this.onResponse(null);
            }
        });
    }

    private void onResponse(Response<Restaurants> response) {
        showProgress(View.GONE);

        if (response != null && response.isSuccessful()) {
            Restaurant restaurant = response.body().getRestaurant();
            restaurantName.setText(restaurant.getName());
            restaurantWelcomeMessage.setText(restaurant.getWelcomeMessage());
            restaurantIntro.setText(restaurant.getIntro());
            restaurantOpenClosed.setText(presentBooleanValue(restaurant.getIsOpen()));
            restaurantOpenClosed.setTextColor(textColor(restaurant.getIsOpen()));
            Picasso.with(getApplicationContext())
                    .load(restaurant.getImages().getThumbnailMedium())
                    .into(restaurantImage);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_data_received, Toast.LENGTH_LONG).show();
        }
    }

    private void initViews(int imageWeight) {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.beacon_test);
        }

        RelativeLayout imageLayout = (RelativeLayout) findViewById(R.id.imageLayout);
        ScrollView textLayout = (ScrollView) findViewById(R.id.textLayout);

        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) imageLayout.getLayoutParams();
        p.weight = imageWeight;

        p = (LinearLayout.LayoutParams) textLayout.getLayoutParams();
        p.weight = 100 - imageWeight;

        restaurantImage = (ImageView) findViewById(R.id.restaurant_image);
        restaurantName = (TextView) findViewById(R.id.restaurant_name_tv);
        restaurantWelcomeMessage = (TextView) findViewById(R.id.restaurant_welcome_message_tv);
        restaurantIntro = (TextView) findViewById(R.id.restaurant_intro_tv);
        restaurantOpenClosed = (TextView) findViewById(R.id.restaurant_open_closed_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(Constants.INTENT_DELETE_ACCESS_TOKEN, true);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String presentBooleanValue(boolean value) {
        if (value) return getString(R.string.open);
        return getString(R.string.closed);
    }

    private int textColor(boolean value) {
        if (value) return Color.GREEN;
        return Color.RED;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void showProgress(int gone) {
        progressBar.setVisibility(gone);
    }
}
