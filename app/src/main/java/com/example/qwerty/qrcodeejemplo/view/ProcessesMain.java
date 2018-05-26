package com.example.qwerty.qrcodeejemplo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.adapter.ProcessesAdapter;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import com.example.qwerty.qrcodeejemplo.model.Piece;
import com.example.qwerty.qrcodeejemplo.model.ProcessesList;

public class ProcessesMain extends AppCompatActivity {

    ArrayList<ProcessesList> processesList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ProcessesAdapter adapter;
    Piece pieza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processes_main);

        getProcesses(new RequestParams());

        pieza = Piece.fromSharedPreferences(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.procesessRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new ProcessesAdapter(prepareData(), pieza, User.getId(getApplicationContext()), this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));
    }

    public ArrayList<ProcessesList> prepareData() {
        return processesList;
    }

    private void getProcesses(RequestParams params) {
        RestClient.get("get-processes.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject responseObj;
                try {
                    for (int i = 0; i < response.length(); i ++) {
                        responseObj = response.getJSONObject(i);
                        processesList.add(new ProcessesList(Integer.parseInt(responseObj.getString("process_id")),
                                responseObj.getString("process_name")));
                    }
                    adapter.setProcessesList(prepareData());
                    recyclerView.setAdapter(adapter);
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