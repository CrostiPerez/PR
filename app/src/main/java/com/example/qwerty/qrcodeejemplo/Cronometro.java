package com.example.qwerty.qrcodeejemplo;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;
import static com.example.qwerty.qrcodeejemplo.R.drawable.ic_pause_black_24dp;
import static com.example.qwerty.qrcodeejemplo.R.drawable.ic_play_arrow_black_24dp;


public class Cronometro extends AppCompatActivity {
    Chronometer chronometer;
    TextView finalTime, finalTimeLabel, txtName;
    ImageButton playPause, stop, death;
    boolean isPlay = false, restart = false, isDeath =false;
    long cont, contDeath, deathCounter;
    String result;
    Pieza pieza;
    int processID;
    int loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        loginID = Integer.parseInt(getIntent().getStringExtra("login_id"));
        RequestParams params = new RequestParams();
        params.put("login_id", loginID);
        getProcess(params);

        pieza = Pieza.fromIntent(getIntent());
        txtName = findViewById(R.id.name);
        txtName.setText(pieza.getName());

        chronometer = findViewById(R.id.Cronometro);
        playPause = findViewById(R.id.playPause);
        stop = findViewById(R.id.stop);
        finalTime = findViewById(R.id.tiempoFinal);
        finalTimeLabel = findViewById(R.id.tiempoFinalLabel);
        death = findViewById(R.id.death);

        playPause.setEnabled(true);
        stop.setEnabled(false);
        death.setEnabled(false);
        finalTime.setAlpha(0.0f);
        finalTimeLabel.setAlpha(0.0f);


        playPause.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(!isDeath) {
                    if (!isPlay) {
                        playPause.setImageDrawable(getDrawable(ic_pause_black_24dp));
                        isPlay = true;
                        restart = true;
                        stop.setEnabled(true);
                        death.setEnabled(true);
                        finalTime.setAlpha(0.0f);
                        finalTimeLabel.setAlpha(0.0f);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                    } else {
                        if (restart) {
                            playPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
                            cont = SystemClock.elapsedRealtime();
                            chronometer.stop();
                            restart = false;
                        } else {
                            playPause.setImageDrawable(getDrawable(ic_pause_black_24dp));
                            chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - cont);
                            chronometer.start();
                            restart = true;
                        }
                    }
                }
                else{
                    if (!isPlay) {
                        playPause.setImageDrawable(getDrawable(ic_pause_black_24dp));
                        isPlay = true;
                        restart = true;
                        stop.setEnabled(true);
                        death.setEnabled(true);
                        finalTime.setAlpha(0.0f);
                        finalTimeLabel.setAlpha(0.0f);
                        chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - contDeath);
                        chronometer.start();
                    }
                    else {
                        if (restart) {
                            playPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
                            cont = SystemClock.elapsedRealtime();
                            chronometer.stop();
                            restart = false;
                        }
                        else {
                            playPause.setImageDrawable(getDrawable(ic_pause_black_24dp));
                            chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - cont);
                            chronometer.start();
                            restart = true;
                        }
                    }
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                playPause.setEnabled(true);
                stop.setEnabled(false);
                death.setEnabled(false);
                result = (String) chronometer.getContentDescription();
                finalTime.setText(result + "\nLa pieza murio " + deathCounter + " veces.");

                RequestParams params = new RequestParams();
                params.put("muertes", deathCounter + pieza.getMuertes());

                finalTime.setTextColor(BLACK);
                finalTime.setAlpha(1.0f);
                finalTimeLabel.setAlpha(1.0f);
                chronometer.stop();
                playPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
                isPlay = false;
                isDeath = false;
                deathCounter = 0;


                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                params.put("id", pieza.getId());

                try {
                    JSONObject processData = new JSONObject();
                    processData.put("process_id", processID);
                    processData.put("staff_id", loginID);
                    processData.put("time", time);
                    params.put("json", processData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                doSomeNetworking(params);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Cronometro.this, R.style.MyDialogTheme);
                    builder.setTitle("PR Calibradores");
                    builder.setMessage("¿Quiere hacer otra pieza igual?");

                    String positiveText = "Sí";
                    builder.setPositiveButton(positiveText,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // positive button logic
                                }
                            });

                    String negativeText = "No";
                    builder.setNegativeButton(negativeText,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // negative button logic
                                }
                            });

                    AlertDialog dialog = builder.create();
                    // display dialog
                    dialog.show();
                }
        });

        death.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view){
                isDeath = true;
                death.setEnabled(false);
                playPause.setEnabled(true);
                stop.setEnabled(false);
                playPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
                chronometer.stop();
                result = (String) chronometer.getContentDescription();
                finalTime.setText("La pieza murio al tiempo: \n" + result);
                finalTime.setTextColor(RED);
                finalTime.setAlpha(1.0f);
                isPlay = false;
                contDeath = SystemClock.elapsedRealtime();
                deathCounter++;
            }
        });

        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
    }

    private void doSomeNetworking(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.prcalibradores.com/app/set-write.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "Se ha finalizado el proceso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getProcess(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("https://www.prcalibradores.com/app/get-process.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    processID = Integer.parseInt(response.getJSONObject(0).getString("process_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Se ha seleccionado el proceso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}