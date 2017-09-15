
/*
 * LoginActivity.java
 *
 * Created by Drago on 9/13/2017
 */

package com.development.id.ns.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.development.id.ns.myapplication.R;
import com.development.id.ns.myapplication.backend.ApiEndPointInterfaces;
import com.development.id.ns.myapplication.backend.RestApi;
import com.development.id.ns.myapplication.backend.json.LoginRequest;
import com.development.id.ns.myapplication.login.LoginCredentials;
import com.development.id.ns.myapplication.model.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.development.id.ns.myapplication.model.Constants.ACCESS_TOKEN;
import static com.development.id.ns.myapplication.model.Constants.MyPREFERENCES;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private EditText userNameEt, passwordEt;
    private String userName = "";
    private String password = "";

    private SharedPreferences sharedpreferences;

    // username = "test@testmenu.com";
    // password = "test1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.login);
        }
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint(getString(R.string.user_name_hint));
        passwordWrapper.setHint(getString(R.string.password_hint));
        userNameEt = (EditText) findViewById(R.id.userNameEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        Button loginButton = (Button) findViewById(R.id.loginBtn);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            boolean shouldDeleteAccessToken = bundle.getBoolean(Constants.INTENT_DELETE_ACCESS_TOKEN, false);
            if (shouldDeleteAccessToken)
                sharedpreferences.edit().putString(Constants.PREFS_ACCESS_TOKEN, "").apply();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();
                callService();
            }
        });
    }

    private void callService() {
        ApiEndPointInterfaces networkService = RestApi.getRestService();
        Call<LoginRequest> call = networkService.loginWithCredentials(new LoginCredentials(userName, password));
        call.enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                if (response.isSuccessful() && !response.body().getAccessToken().isEmpty()) {
                    String accessToken = response.body().getAccessToken();
                    //start details screen
                    //save token to preferences
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(ACCESS_TOKEN, accessToken);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, DetailsActivity.class);
                    intent.putExtra(ACCESS_TOKEN, accessToken);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.wrong_credentials_message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.no_data_received, Toast.LENGTH_LONG).show();
            }
        });
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
}
