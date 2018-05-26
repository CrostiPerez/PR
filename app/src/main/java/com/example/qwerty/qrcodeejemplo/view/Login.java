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

import cz.msebera.android.httpclient.Header;

import static android.view.View.VISIBLE;

public class Login extends AppCompatActivity {
    EditText txtName;
    EditText txtPassword;
    Button btnLogin;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.activity_login);
        txtName = findViewById(R.id.usernameInputText);
        txtPassword = findViewById(R.id.passwordInputText);
        btnLogin = findViewById(R.id.loginButton);
        progress = findViewById(R.id.progressBar3);
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
        progress.setVisibility(View.INVISIBLE);
    }

    private void login(RequestParams params) {
        RestClient.post("login.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if(!response.isNull(0)) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    try {
                        User.setId(response.getJSONObject(0).getString("login_id"), getApplicationContext());
                        User.setPassword(response.getJSONObject(0).getString("login_password"), getApplicationContext());
                        startActivity(intent);
                        finish();
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        String user = User.getId(getApplicationContext());
        String pass = User.getPassword(getApplicationContext());
        if (user != null && pass != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return;
    }

}
