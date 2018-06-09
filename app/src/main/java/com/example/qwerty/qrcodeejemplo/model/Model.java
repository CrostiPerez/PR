package com.example.qwerty.qrcodeejemplo.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwerty on 26/05/18.
 */

public class Model {

    int modelID;
    String modelName;

    public int getModelID() {
        return modelID;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    String modelDescription;

    public Model(int modelID, String modelName, String modelDescription) {
        this.modelID = modelID;
        this.modelName = modelName;
        this.modelDescription = modelDescription;
    }

    public static void saveFromJSON(JSONObject jsonObject, Context context) {
        try {
            setModelID(jsonObject.getInt("model_id"), context);
            setModelName(jsonObject.getString("model_name"), context);
            setModelDescription(jsonObject.getString("model_description"), context);
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

    public static int getModelID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("model_data", Context.MODE_PRIVATE);
        return preferences.getInt("model_data_1", -1);
    }

    public static void setModelID(int modelID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("model_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("model_data_1", modelID);
        editor.commit();
    }

    public static String getModelName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("model_data", Context.MODE_PRIVATE);
        return preferences.getString("model_data_2", null);
    }

    public static void setModelName(String modelName, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("model_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("model_data_2", modelName);
        editor.commit();
    }

    public static String getModelDescription(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("model_data", Context.MODE_PRIVATE);
        return preferences.getString("model_data_3", null);
    }

    public static void setModelDescription(String modelDescription, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("model_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("model_data_3", modelDescription);
        editor.commit();
    }

}
