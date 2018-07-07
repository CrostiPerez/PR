package com.example.qwerty.qrcodeejemplo.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.qwerty.qrcodeejemplo.database.DbSchema.*;

/**
 * Created by qwerty on 26/05/18.
 */

public class Model {

    private static final String MODEL_DATA = "model_data";
    String mModelID;
    String mModelName;

    public String getModelID() {
        return mModelID;
    }

    public String getModelName() {
        return mModelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    String modelDescription;

    public Model(String modelID, String modelName, String modelDescription) {
        this.mModelID = modelID;
        this.mModelName = modelName;
        this.modelDescription = modelDescription;
    }

    public static void saveFromJSON(JSONObject jsonObject, Context context) {
        try {
            setModelID(jsonObject.getString(ModelTable.Cols.ID), context);
            setModelName(jsonObject.getString(ModelTable.Cols.NAME), context);
            setModelDescription(jsonObject.getString(ModelTable.Cols.DESCRIPTION), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Model fromSharedPreferences(Context context) {
        try {
            return new Model(
                    getModelID(context),
                    getModelName(context),
                    getModelDescription(context)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveOnSharedPreferences(String modelID, String modelName, String modelDescription, Context context) {
        setModelID(modelID, context);
        setModelName(modelName, context);
        setModelDescription(modelDescription, context);
    }

    public static String getModelID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(MODEL_DATA, Context.MODE_PRIVATE);
        return preferences.getString("model_data_1", null);
    }

    public static void setModelID(String modelID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(MODEL_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("model_data_1", modelID);
        editor.apply();
    }

    public static String getModelName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(MODEL_DATA, Context.MODE_PRIVATE);
        return preferences.getString("model_data_2", null);
    }

    public static void setModelName(String modelName, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(MODEL_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("model_data_2", modelName);
        editor.apply();
    }

    public static String getModelDescription(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(MODEL_DATA, Context.MODE_PRIVATE);
        return preferences.getString("model_data_3", null);
    }

    public static void setModelDescription(String modelDescription, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("model_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("model_data_3", modelDescription);
        editor.apply();
    }

}
