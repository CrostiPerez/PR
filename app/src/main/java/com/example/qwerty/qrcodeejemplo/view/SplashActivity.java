package com.example.qwerty.qrcodeejemplo.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qwerty.qrcodeejemplo.database.DbSchema;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

import static com.example.qwerty.qrcodeejemplo.database.DbSchema.*;
import static com.example.qwerty.qrcodeejemplo.database.RestClient.*;

public class SplashActivity extends AppCompatActivity {

    //private final int DURACION_SPLASH = 1500;
    private String mUser;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = User.getId(SplashActivity.this);
        mPassword = User.getPassword(SplashActivity.this);

        try {
            if (existUserCredentials()) {
                validateCredentials(mUser, mPassword);
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        finish();
    }

    private boolean existUserCredentials() {
        return mUser != null && mPassword != null && !mUser.equals("") && !mPassword.equals("");
    }

    private void validateCredentials(String username, String password) {

        RequestParams params = new RequestParams();
        params.put(ExistUserScript.Params.$1, username);
        params.put(ExistUserScript.Params.$2, password);

        post(ExistUserScript.FILE_NAME, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                String responseID;
                String responsePass;
                try {
                    responseID = response.getJSONObject(0).getString(UserTable.Cols.ID);
                    responsePass = response.getJSONObject(0).getString(UserTable.Cols.PASSWORD);

                    if (responseID.equals(mUser) && responsePass.equals(mPassword)) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

}
