package com.example.qwerty.qrcodeejemplo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.view.View.VISIBLE;

public class Login extends AppCompatActivity {
    private EditText txtName;
    private EditText txtPassword;
    private Button btnLogin;
    private ProgressBar progress;
    private String user;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.activity_login);
        progress = findViewById(R.id.progressBar3);
        progress.setVisibility(View.INVISIBLE);
        txtName = findViewById(R.id.usernameInputText);
        txtPassword = findViewById(R.id.passwordInputText);
        btnLogin = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("username", txtName.getText().toString());
                params.put("password", txtPassword.getText().toString());
                progress.setVisibility(VISIBLE);
                login(params);
            }
        });
    }

    private void login(RequestParams params) {
        RestClient.post("login.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                try {
                    User.saveFromJSON(response.getJSONObject(0), getApplicationContext());
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void loadData() {
        user = User.getId(this);
        pass = User.getPassword(this);
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
                                finish();
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
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
