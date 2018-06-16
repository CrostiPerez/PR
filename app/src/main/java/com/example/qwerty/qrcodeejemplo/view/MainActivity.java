package com.example.qwerty.qrcodeejemplo.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.Model;
import com.example.qwerty.qrcodeejemplo.model.User;
import com.google.android.gms.vision.barcode.Barcode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    private Button btnScan, btnCons, logout;
    private ProgressBar progress;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    public static final int CHRONOMETER = 300;
    public static final int PDF = 400;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btnProceso);
        btnCons = findViewById(R.id.btnConsulta);
        progress = findViewById(R.id.progressBar);
        logout = findViewById(R.id.logoutButton);

        type = 0;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        btnScan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ProjectsMain.class);
            startActivity(intent);
            type = CHRONOMETER;
            progress.setVisibility(VISIBLE);
            finish();
           }
        });
        btnCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            progress.setVisibility(VISIBLE);
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            type = PDF;
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.setId("",MainActivity.this);
                User.setPassword("",MainActivity.this);
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progress.setVisibility(View.INVISIBLE);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            final Barcode barcode = data.getParcelableExtra("barcode");
            String[] values = barcode.displayValue.split(",");
            //17(año)-66(tipo de trabajo)-037(num cliente)-461(proyecto)-003(ensamble)-001(pieza)
            if (type == CHRONOMETER && validateData(values)) {
                RequestParams params = new RequestParams();
                params.put("project_id", values[0]);
                params.put("model_id", values[1]);
                params.put("user_id", User.getId(this));
                Log.d("debug", values[0] + ", " + values[1] + ", " + User.getId(this));
                getModel(params);
            }
            else if(type == PDF){
                Intent intent = new Intent(MainActivity.this, PDFActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }

    private void getModel(RequestParams params) {
        RestClient.get("get-model.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Model.saveFromJSON(response, getApplicationContext());
                Intent intent = new Intent(MainActivity.this, Cronometro.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), "Ups parece que ha habido un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateData(String[] values) {
        if (values.length == 2) {
            Integer.parseInt(values[0]);
            Integer.parseInt(values[1]);
            return true;
        } else {
            Toast.makeText(this, "Código QR no valido", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
