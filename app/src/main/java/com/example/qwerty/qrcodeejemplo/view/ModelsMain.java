package com.example.qwerty.qrcodeejemplo.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.Toast;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.adapter.ModelsAdapter;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.ModelsList;
import com.example.qwerty.qrcodeejemplo.model.Project;
import com.example.qwerty.qrcodeejemplo.model.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ModelsMain extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ModelsAdapter mAdapter;

    ArrayList<ModelsList> mModelsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models_main);

        RequestParams params = new RequestParams();
        params.put("project_id", Project.getProjectID(this));
        params.put("user_id", User.getId(this));

        getModels(params);

        mRecyclerView = findViewById(R.id.modelsRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ModelsAdapter(mModelsList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));

    }

    private void getModels(RequestParams params) {
        RestClient.get(RestClient.FILE_GET_MODELS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject responseObj;
                try {
                    for (int i = 0; i < response.length(); i ++) {
                        responseObj = response.getJSONObject(i);
                        mModelsList.add(new ModelsList(
                                        responseObj.getString("model_id"),
                                        responseObj.getString("model_name"),
                                        responseObj.getString("model_description")
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
                                    response.getString("model_id"),
                                    response.getString("model_name"),
                                    response.getString("model_description")
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
