package com.example.qwerty.qrcodeejemplo.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.qwerty.qrcodeejemplo.database.DbSchema;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.example.qwerty.qrcodeejemplo.view.MainActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.qwerty.qrcodeejemplo.database.DbSchema.*;

/**
 * Created by qwerty on 28/04/18.
 */

public class User {

    public static final String USER_DATA = "user_data";

    public static void saveFromJSON(JSONObject jsonObject, Context context) {
        try {
            setId(jsonObject.getString(UserTable.Cols.ID), context);
            setPassword(jsonObject.getString(UserTable.Cols.PASSWORD), context);
            setProcessId(jsonObject.getString(ProcessTable.Cols.ID), context);
            setProcessName(jsonObject.getString(ProcessTable.Cols.NAME), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static String getId(Context context) throws NullPointerException {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        return preferences.getString("user_data_1", null);
    }
    public static String getPassword(Context context) throws NullPointerException {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        return preferences.getString("user_data_2", null);
    }
    public static String getProcessId(Context context) throws NullPointerException {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        return preferences.getString("user_data_3", null);
    }
    public static String getProcessName(Context context) throws NullPointerException {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        return preferences.getString("user_data_4", null);
    }
    public static void setId(String id,Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_data_1", id);
        editor.apply();
    }
    public static void setPassword(String pass, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_data_2", pass);
        editor.apply();
    }
    public static void setProcessId(String process,Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_data_3", process);
        editor.apply();
    }
    public static void setProcessName(String process,Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_data_4", process);
        editor.apply();
    }
}
