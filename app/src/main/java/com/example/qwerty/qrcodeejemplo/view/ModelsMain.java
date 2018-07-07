package com.example.qwerty.qrcodeejemplo.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.Toast;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.adapter.ModelsAdapter;
import com.example.qwerty.qrcodeejemplo.database.DbSchema;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.Model;
import com.example.qwerty.qrcodeejemplo.model.ModelsList;
import com.example.qwerty.qrcodeejemplo.model.Project;
import com.example.qwerty.qrcodeejemplo.model.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.qwerty.qrcodeejemplo.database.DbSchema.*;
import static com.example.qwerty.qrcodeejemplo.database.RestClient.*;

public class ModelsMain extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ModelsAdapter mAdapter;

    ArrayList<ModelsList> mModelsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models_main);

        getModels(Project.getProjectID(this), User.getId(this));

        mRecyclerView = findViewById(R.id.modelsRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ModelsAdapter(mModelsList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));

    }

    private void getModels(int projectId, String userId) {

        RequestParams params = new RequestParams();
        params.put(GetModelsScript.Params.$1, projectId);
        params.put(GetModelsScript.Params.$2, userId);

        get(GetModelsScript.FILE_NAME, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject responseObj;
                try {
                    for (int i = 0; i < response.length(); i ++) {
                        responseObj = response.getJSONObject(i);
                        mModelsList.add(new ModelsList(
                                        responseObj.getString(ModelTable.Cols.ID),
                                        responseObj.getString(ModelTable.Cols.NAME),
                                        responseObj.getString(ModelTable.Cols.DESCRIPTION)
                                )
                        );
                    }
                    mAdapter.setModelsList(mModelsList);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject responseObj;
                try {
                    mModelsList.add(new ModelsList(
                                    response.getString(ModelTable.Cols.ID),
                                    response.getString(ModelTable.Cols.NAME),
                                    response.getString(ModelTable.Cols.DESCRIPTION)
                            )
                    );
                    mAdapter.setModelsList(mModelsList);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                String message = getString(
                        R.string.exception_network,
                        getResources().getString(R.string.exception_get_models));
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
