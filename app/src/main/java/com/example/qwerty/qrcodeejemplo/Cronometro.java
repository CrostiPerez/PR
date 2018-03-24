package com.example.qwerty.qrcodeejemplo;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.vision.barcode.Barcode;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;
import static com.example.qwerty.qrcodeejemplo.R.drawable.ic_pause_black_24dp;
import static com.example.qwerty.qrcodeejemplo.R.drawable.ic_play_arrow_black_24dp;


public class Cronometro extends AppCompatActivity {
    public static final int REQUEST_COoDE = 100;
    Chronometer chronometer;
    TextView finalTime, finalTimeLabel;
    ImageButton playPause, stop, death;
    boolean isPlay = false, restart = false, isDeath =false;
    long cont, contDeath, deathCounter;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        chronometer = (Chronometer)findViewById(R.id.Cronometro);
        playPause = (ImageButton)findViewById(R.id.playPause);
        stop = (ImageButton)findViewById(R.id.stop);
        finalTime = (TextView)findViewById(R.id.tiempoFinal);
        finalTimeLabel = (TextView)findViewById(R.id.tiempoFinalLabel);
        death = (ImageButton) findViewById(R.id.death);

        playPause.setEnabled(true);
        stop.setEnabled(false);
        death.setEnabled(false);
        finalTime.setAlpha(0.0f);
        finalTimeLabel.setAlpha(0.0f);


        playPause.setOnClickListener(new View.OnClickListener() {
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
                finalTime.setTextColor(BLACK);
                finalTime.setAlpha(1.0f);
                finalTimeLabel.setAlpha(1.0f);
                chronometer.stop();
                playPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
                isPlay = false;
                isDeath = false;
                deathCounter = 0;
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

    protected void Result(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_COoDE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode =data.getParcelableExtra("barcode");
                Toast qr = Toast.makeText(getApplicationContext(),barcode.displayValue, Toast.LENGTH_SHORT);
                qr.show();
            }
        }
    }
}