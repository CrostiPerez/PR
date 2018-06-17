package com.example.qwerty.qrcodeejemplo.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 1500;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = User.getId(SplashActivity.this);
        pass = User.getPassword(SplashActivity.this);
        try {
            if ((!user.equals("")) && (!pass.equals(""))) {
                RequestParams params = new RequestParams();
                params.put("login_id", user);
                params.put("login_password", pass);
                RestClient.post("exist.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        String responseID;
                        String responsePass;
                        try {
                            responseID = response.getJSONObject(0).getString("login_id");
                            responsePass = response.getJSONObject(0).getString("login_password");
                            if (responseID.equals(user) && responsePass.equals(pass)) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                        setContentView(R.layout.activity_login);
                    }
                });
            } else {
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        finish();
    }

}
