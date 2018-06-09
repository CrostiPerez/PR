package com.example.qwerty.qrcodeejemplo.view;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.Model;
import com.example.qwerty.qrcodeejemplo.model.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import com.example.qwerty.qrcodeejemplo.model.Piece;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;
import static com.example.qwerty.qrcodeejemplo.R.drawable.ic_pause_black_24dp;
import static com.example.qwerty.qrcodeejemplo.R.drawable.ic_play_arrow_black_24dp;

public class Cronometro extends AppCompatActivity {
    private Chronometer chronometer;
    private TextView mFinalTime, mFinalTimeLabel, mTxtName, mTextView;
    private ImageButton playPause, stop, death;
    private boolean isPlay = false, restart = false, isDeath =false;
    private long cont, contDeath, deathCounter;

    private String result;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        model = Model.fromSharedPreferences(this);

        RequestParams params = new RequestParams();
        params.put("model_id", model.getModelID() + "");
        params.put("process_id", User.getProcessId(this));
        setNewPiece(params);

        mTextView = findViewById(R.id.cronometroLabel);
        mTextView.setText(User.getProcessName(this));

        mTxtName = findViewById(R.id.name);
        mTxtName.setText(model.getModelName());

        chronometer = findViewById(R.id.Cronometro);
        playPause = findViewById(R.id.playPause);
        stop = findViewById(R.id.stop);
        mFinalTime = findViewById(R.id.tiempoFinal);
        mFinalTimeLabel = findViewById(R.id.tiempoFinalLabel);
        death = findViewById(R.id.death);

        playPause.setEnabled(true);
        stop.setEnabled(false);
        death.setEnabled(false);
        mFinalTime.setAlpha(0.0f);
        mFinalTimeLabel.setAlpha(0.0f);

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
                    mFinalTime.setAlpha(0.0f);
                    mFinalTimeLabel.setAlpha(0.0f);
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
                    mFinalTime.setAlpha(0.0f);
                    mFinalTimeLabel.setAlpha(0.0f);
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
            mFinalTime.setText(result + "\nLa pieza murio " + deathCounter + " veces.");

            RequestParams params = new RequestParams();
            params.put("muertes", deathCounter + Piece.getMuertes(getApplicationContext()));

            mFinalTime.setTextColor(BLACK);
            mFinalTime.setAlpha(1.0f);
            mFinalTimeLabel.setAlpha(1.0f);
            chronometer.stop();
            playPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
            isPlay = false;
            isDeath = false;
            deathCounter = 0;

            long time = SystemClock.elapsedRealtime() - chronometer.getBase();
            params.put("id", Piece.getId(Cronometro.this));

            try {
                JSONObject json = new JSONObject();
                json.put("process_id", User.getProcessId(Cronometro.this));
                json.put("staff_id", User.getId(Cronometro.this));
                json.put("time", time);
                Toast.makeText(Cronometro.this, json.toString(), Toast.LENGTH_LONG).show();
                params.put("json", json.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setTime(params);

            AlertDialog.Builder builder = new AlertDialog.Builder(Cronometro.this, R.style.MyDialogTheme);
            builder.setTitle("PR Calibradores");
            builder.setMessage("¿Quiere hacer otra pieza igual?");

            String positiveText = "Sí";
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                // positive button logic
                Intent intent = new Intent(getApplicationContext(), Cronometro.class);
                startActivity(getIntent());
                finish();
                }
            });

            String negativeText = "No";
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                finish();
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
            mFinalTime.setText("La pieza murio al tiempo: \n" + result);
            mFinalTime.setTextColor(RED);
            mFinalTime.setAlpha(1.0f);
            isPlay = false;
            contDeath = SystemClock.elapsedRealtime();
            deathCounter++;
            }
        });

        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setTime(RequestParams params) {
        RestClient.post("set-time.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(Cronometro.this, "Se ha finalizado el proceso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Cronometro.this, "Ha habido un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNewPiece(RequestParams params) {
        RestClient.get("set-piece.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Piece.saveFromJSON(response, Cronometro.this);
                Toast.makeText(Cronometro.this, "ID de pieza a procesar: " + Piece.getId(Cronometro.this), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(Cronometro.this, "Ha habido un error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}