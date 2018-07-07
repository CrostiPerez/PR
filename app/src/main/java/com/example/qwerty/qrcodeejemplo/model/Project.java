package com.example.qwerty.qrcodeejemplo.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.qwerty.qrcodeejemplo.database.DbSchema;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.qwerty.qrcodeejemplo.database.DbSchema.*;

public class Project {
    public static final String PROJECT_DATA = "project_data";
    int mProjectID;
    private String mProjectName;

    public Project(int projectID, String projectName) {
        this.mProjectID = projectID;
        this.mProjectName = projectName;
    }

    public int getProjectID() {
        return mProjectID;
    }

    public void setProjectID(int projectID) {
        this.mProjectID = projectID;
    }

    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String projectName) {
        this.mProjectName = projectName;
    }

    public static void saveFromJSON(JSONObject jsonObject, Context context) {
        try {
            setProjectID(jsonObject.getInt(ProjectTable.Cols.ID), context);
            setProjectName(jsonObject.getString(ProjectTable.Cols.NAME), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Project fromSharedPreferences(Context context) {
        try {
            return new Project(
                    getProjectID(context),
                    getProjectName(context)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveOnSharedPreferences(int projectID, String projectName, Context context) {
        setProjectID(projectID, context);
        setProjectName(projectName, context);
    }


    public static int getProjectID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PROJECT_DATA, Context.MODE_PRIVATE);
        return preferences.getInt("project_data_1", -1);
    }

    public static void setProjectID(int modelID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PROJECT_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("project_data_1", modelID);
        editor.apply();
    }

    public static String getProjectName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PROJECT_DATA, Context.MODE_PRIVATE);
        return preferences.getString("project_data_2", null);
    }

    public static void setProjectName(String modelName, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PROJECT_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("project_data_2", modelName);
        editor.apply();
    }
}
