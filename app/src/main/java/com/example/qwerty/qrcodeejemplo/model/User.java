package com.example.qwerty.qrcodeejemplo.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.qwerty.qrcodeejemplo.MainActivity;
import com.example.qwerty.qrcodeejemplo.database.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by qwerty on 28/04/18.
 */

public class User {
    public static String getPassword(Context context) throws NullPointerException {
        SharedPreferences preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return preferences.getString("user_data_2", null);
    }
    public static String getId(Context context) throws NullPointerException {
        SharedPreferences preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return preferences.getString("user_data_1", null);
    }
    public static void setPassword(String pass, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_data_2", pass);
        editor.commit();
    }
    public static void setId(String id,Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_data_1", id);
        editor.commit();
    }
}
