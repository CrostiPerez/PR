package com.example.qwerty.qrcodeejemplo.view;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.adapter.ProjectsAdapter;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.model.ProjectsList;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ProjectsMain extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProjectsAdapter mAdapter;

    private ArrayList<ProjectsList> mProjectsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_main);

        getProjects(new RequestParams());

        mRecyclerView = findViewById(R.id.projectsRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProjectsAdapter(mProjectsList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));
    }

    private void getProjects(RequestParams params) {
        RestClient.get(RestClient.FILE_GET_PROJECTS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject responseObj;
                try {
                    for (int i = 0; i < response.length(); i ++) {
                        responseObj = response.getJSONObject(i);
                        mProjectsList.add(new ProjectsList(
                                responseObj.getString("project_id"),
                                responseObj.getString("project_name"),
                                responseObj.getString("project_status"),
                                responseObj.getString("project_description"),
                                new Date(responseObj.getString("project_startDate")),
                                new Date(responseObj.getString("project_deadLine"))
                                )
                        );
                    }
                    mAdapter.setProjectsList(mProjectsList);
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
                        getResources().getString(R.string.exception_get_projects));
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
