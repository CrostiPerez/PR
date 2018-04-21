package com.example.qwerty.qrcodeejemplo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    Button btnScan, btnCons;
    ProgressBar progress;
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
        type = 0;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        btnScan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, ScanActivity.class);
               startActivityForResult(intent, REQUEST_CODE);
               type = 300;
               progress.setVisibility(VISIBLE);
           }
        });
        btnCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(VISIBLE);
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                type = 400;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progress.setVisibility(View.INVISIBLE);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && type == CHRONOMETER) {
            if (data != null) {
                final Barcode barcode =data.getParcelableExtra("barcode");
                RequestParams params = new RequestParams();
                params.put("id", barcode.displayValue);
                doSomeNetworking(params);
            }
        }
        else if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && type == PDF){
            Intent intent = new Intent(MainActivity.this, PDFActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private void doSomeNetworking(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://www.prcalibradores.com/plattform/DataBase/qr-prueba.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Pieza pieza = null;
                try {
                    if (response.length() == 1) {
                        pieza = Pieza.fromJSON(response.getJSONObject(0));
                        Intent intent = new Intent(getApplicationContext(), Cronometro.class);
                        intent.putExtra("piece_id", pieza.getId());
                        intent.putExtra("model_id", pieza.getModel_id());
                        intent.putExtra("piece_name", pieza.getName());
                        intent.putExtra("piece_processes", pieza.getProcesses().toString());
                        intent.putExtra("muertes", pieza.getMuertes());
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), "Ups parece que ha habido un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
