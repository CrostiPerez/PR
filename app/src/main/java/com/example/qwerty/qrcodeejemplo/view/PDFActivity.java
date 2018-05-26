package com.example.qwerty.qrcodeejemplo.view;

import android.annotation.SuppressLint;
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
    PDFView pdfView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        progressBar = findViewById(R.id.progressBar2);
        pdfView = findViewById(R.id.pdf);
        //pdfView.fromAsset("CCA-Certificate-Java Level 1.pdf").load();
        new RetrievePDFStream().execute("https://www.rstudio.com/wp-content/uploads/2015/03/rmarkdown-spanish.pdf");
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
            pdfView.fromStream(inputStream).load();
           progressBar.setVisibility(View.GONE);
        }
    }
}
