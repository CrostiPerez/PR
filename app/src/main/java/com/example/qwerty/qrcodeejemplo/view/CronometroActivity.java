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

public class CronometroActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private TextView mFinalTime;
    private TextView mFinalTimeLabel;
    private TextView mTxtName;
    private TextView mTextView;
    private ImageButton mPlayPause;
    private ImageButton mStop;
    private ImageButton mDeath;
    private boolean mIsPlay = false;
    private boolean mRestart = false;
    private boolean mIsDeath =false;
    private long mCont;
    private long mContDeath;
    private long mDeathCounter;
    private String mResult;
    private Model mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        mModel = Model.fromSharedPreferences(this);

        RequestParams params = new RequestParams();
        params.put("model_id", mModel.getModelID() + "");
        params.put("process_id", User.getProcessId(this));
        setNewPiece(params);

        mTextView = findViewById(R.id.cronometroLabel);
        mTextView.setText(User.getProcessName(this));

        mTxtName = findViewById(R.id.name);
        mTxtName.setText(mModel.getModelName());

        chronometer = findViewById(R.id.Cronometro);
        mPlayPause = findViewById(R.id.playPause);
        mStop = findViewById(R.id.stop);
        mFinalTime = findViewById(R.id.tiempoFinal);
        mFinalTimeLabel = findViewById(R.id.tiempoFinalLabel);
        mDeath = findViewById(R.id.death);

        mPlayPause.setEnabled(true);
        mStop.setEnabled(false);
        mDeath.setEnabled(false);
        mFinalTime.setAlpha(0.0f);
        mFinalTimeLabel.setAlpha(0.0f);

        mPlayPause.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
            if(!mIsDeath) {
                if (!mIsPlay) {
                    mPlayPause.setImageDrawable(getDrawable(ic_pause_black_24dp));
                    mIsPlay = true;
                    mRestart = true;
                    mStop.setEnabled(true);
                    mDeath.setEnabled(true);
                    mFinalTime.setAlpha(0.0f);
                    mFinalTimeLabel.setAlpha(0.0f);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                } else {
                    if (mRestart) {
                        mPlayPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
                        mCont = SystemClock.elapsedRealtime();
                        chronometer.stop();
                        mRestart = false;
                    } else {
                        mPlayPause.setImageDrawable(getDrawable(ic_pause_black_24dp));
                        chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - mCont);
                        chronometer.start();
                        mRestart = true;
                    }
                }
            }
            else{
                if (!mIsPlay) {
                    mPlayPause.setImageDrawable(getDrawable(ic_pause_black_24dp));
                    mIsPlay = true;
                    mRestart = true;
                    mStop.setEnabled(true);
                    mDeath.setEnabled(true);
                    mFinalTime.setAlpha(0.0f);
                    mFinalTimeLabel.setAlpha(0.0f);
                    chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - mContDeath);
                    chronometer.start();
                }
                else {
                    if (mRestart) {
                        mPlayPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
                        mCont = SystemClock.elapsedRealtime();
                        chronometer.stop();
                        mRestart = false;
                    }
                    else {
                        mPlayPause.setImageDrawable(getDrawable(ic_pause_black_24dp));
                        chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - mCont);
                        chronometer.start();
                        mRestart = true;
                    }
                }
            }
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
            mPlayPause.setEnabled(true);
            mStop.setEnabled(false);
            mDeath.setEnabled(false);
            mResult = (String) chronometer.getContentDescription();
            mFinalTime.setText(mResult + "\nLa pieza murio " + mDeathCounter + " veces.");

            mFinalTime.setTextColor(BLACK);
            mFinalTime.setAlpha(1.0f);
            mFinalTimeLabel.setAlpha(1.0f);
            chronometer.stop();
            mPlayPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
            mIsPlay = false;
            mIsDeath = false;
            mDeathCounter = 0;

            long time = SystemClock.elapsedRealtime() - chronometer.getBase();

            JSONObject json = null;
            try {
                json = new JSONObject();
                json.put("process_id", User.getProcessId(CronometroActivity.this));
                json.put("staff_id", User.getId(CronometroActivity.this));
                json.put("time", time);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            long muertes = mDeathCounter + Piece.getMuertes(getApplicationContext());
            setTime(Piece.getId(getApplicationContext()), json, muertes);

                }
        });

        mDeath.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view){
                mIsDeath = true;
                mDeath.setEnabled(false);
                mPlayPause.setEnabled(true);
                mStop.setEnabled(false);
                mPlayPause.setImageDrawable(getDrawable(ic_play_arrow_black_24dp));
                chronometer.stop();
                mResult = (String) chronometer.getContentDescription();
                mFinalTime.setText("La pieza murio al tiempo: \n" + mResult);
                mFinalTime.setTextColor(RED);
                mFinalTime.setAlpha(1.0f);
                mIsPlay = false;
                mContDeath = SystemClock.elapsedRealtime();
                mDeathCounter++;
            }
        });

        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setTime(int id, JSONObject json, long muertes) {
        RequestParams params = new RequestParams();
        params.put("muertes", muertes);
        params.put("id", id);
        params.put("json", json.toString());
        Log.d("Cronometro", "Muertes: " + muertes);
        Log.d("Cronometro", "ID: " + id);
        Log.d("Cronometro", "Json: " + json);
        RestClient.post(RestClient.FILE_SET_TIME, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(CronometroActivity.this, "Se ha finalizado el proceso", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(CronometroActivity.this, R.style.MyDialogTheme);
                builder.setTitle(R.string.dialog_title);
                builder.setMessage(getDialogMessage(response));

                builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        startActivity(getIntent());
                        finish();
                    }
                });

                builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CronometroActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                String message =
                        getString(R.string.exception_network,
                                getResources().getString(R.string.exception_set_time));

                Toast.makeText(CronometroActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNewPiece(RequestParams params) {
        RestClient.post(RestClient.FILE_SET_PIECE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Piece.saveFromJSON(response, CronometroActivity.this);
                Toast.makeText(CronometroActivity.this, "ID de pieza a procesar: " + Piece.getId(CronometroActivity.this), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                String message =
                        getString(R.string.exception_network,
                            getResources().getString(R.string.exception_set_piece));

                Toast.makeText(CronometroActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getDialogMessage(JSONObject response) {
        String nextProcessName = null;
        try {
            nextProcessName = response.getString("process_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String message = getString(R.string.dialog_message, nextProcessName);

        if (!nextProcessName.equals(R.string.dialog_processes_terminated)) {
            message = getResources().getString(R.string.dialog_next_process) + message;
        }

        return message;
    }
    
}