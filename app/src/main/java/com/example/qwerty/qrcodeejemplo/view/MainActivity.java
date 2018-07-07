package com.example.qwerty.qrcodeejemplo.view;

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
import static com.example.qwerty.qrcodeejemplo.database.RestClient.*;

public class MainActivity extends AppCompatActivity {
    private Button btnScan;
    private Button btnCons;
    private Button logout;
    private ProgressBar progress;
    private static final int PERMISSION_REQUEST = 200;
    private static final int REQUEST_CHRONOMETER = 483;
    private static final int REQUEST_SCANNER = 43;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btnProceso);
        btnCons = findViewById(R.id.btnConsulta);
        progress = findViewById(R.id.progressBar);
        logout = findViewById(R.id.logoutButton);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        btnScan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ProjectsMain.class);
            startActivity(intent);
            progress.setVisibility(VISIBLE);
           }
        });
        btnCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            progress.setVisibility(VISIBLE);
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            startActivityForResult(intent, REQUEST_SCANNER);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.setId("",MainActivity.this);
                User.setPassword("",MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progress.setVisibility(View.INVISIBLE);
        if (resultCode != RESULT_OK) {
            return;
        }
        Barcode barcode = data.getParcelableExtra(ScanActivity.EXTRA_BARCODE);
        String[] values = barcode.displayValue.split("-");

        if (!isValidData(values)) {
            return;
        }
        //17(año)-66(tipo de trabajo)-037(num cliente)-461(proyecto)-003(ensamble)-001(pieza)

        if (requestCode == REQUEST_CHRONOMETER) {
            getModel(values[4], values[5], User.getId(this));
        }
        else if(requestCode == REQUEST_SCANNER){
            Intent intent = PDFActivity.newIntent(MainActivity.this, barcode.displayValue);
            startActivity(intent);
        }
    }

    private void getModel(String projectId, String modelId, String userId) {

        RequestParams params = new RequestParams();
        params.put(GetModelScript.Params.$1, projectId);
        params.put(GetModelScript.Params.$2, modelId);
        params.put(GetModelScript.Params.$3, userId);

        get(GetModelScript.FILE_NAME, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Model.saveFromJSON(response, getApplicationContext());
                Intent intent = new Intent(MainActivity.this, CronometroActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                String message = getResources().getString(R.string.exception_get_model);
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.exception_network, message),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidData(String... values) {
        try {
            if (!(values.length == 6)) throw new NotValidDataException(R.string.exception_number_values);

            for (String value : values) {
                if (!isNumber(value)) throw new NotValidDataException(R.string.exception_not_number);
            }
        } catch (NotValidDataException e) {
            return false;
        }
        return true;
    }

    private boolean isNumber(String string) {
        try {
            int i = Integer.parseInt(string);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    private class NotValidDataException extends Exception {
        NotValidDataException (int messageRes) {
            String message = getResources().getString(messageRes);
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.exception_qr, message),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
