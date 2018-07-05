package com.example.qwerty.qrcodeejemplo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.example.qwerty.qrcodeejemplo.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFActivity extends AppCompatActivity {

    private static final String EXTRA_MODEL_ID = "model_id";

    private PDFView mPDFView;
    private ProgressBar mProgressBar;

    public static Intent newIntent(Context packageContext, String modelId) {
        Intent intent = new Intent(packageContext, PDFActivity.class);
        intent.putExtra(EXTRA_MODEL_ID, modelId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        String modelId = getIntent().getStringExtra(EXTRA_MODEL_ID);

        mProgressBar = findViewById(R.id.progressBar2);
        mPDFView = findViewById(R.id.pdf);
        //mPDFView.fromAsset("CCA-Certificate-Java Level 1.pdf").load();
        new RetrievePDFStream().execute(modelId);
    }

   @SuppressLint("StaticFieldLeak")
   class RetrievePDFStream extends AsyncTask <String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e) {
                return null;
            }
            return inputStream;
        }

       @Override
       protected void onPreExecute() {

       }

       @Override
        protected void onPostExecute(InputStream inputStream) {
            mPDFView.fromStream(inputStream).load();
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
