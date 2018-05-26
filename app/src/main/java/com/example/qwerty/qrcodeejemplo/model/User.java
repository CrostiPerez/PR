package com.example.qwerty.qrcodeejemplo.model;

import android.content.Context;
import android.content.SharedPreferences;

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
