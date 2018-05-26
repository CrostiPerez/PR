package com.example.qwerty.qrcodeejemplo.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwerty on 3/04/18.
 */

public class Piece {
    private int id;
    private int model_id;
    private String name;
    private JSONArray processes;
    private int muertes;

    public Piece(int id, int model_id, String name, JSONArray processes, int muertes) {
        this.id = id;
        this.name = name;
        this.model_id = model_id;
        this.processes = processes;
        this.muertes = muertes;
    }

    public Piece() {
    }

    public static void saveFromJSON(JSONObject jsonObject, Context context) {
        try {
            setId(jsonObject.getInt("piece_id"), context);
            setModel_id(jsonObject.getInt("model_id"), context);
            setName(jsonObject.getString("piece_name"), context);
            if (jsonObject.getString("piece_processes").isEmpty()) {
                setProcesses(new JSONArray().toString(), context);
            } else {
                setProcesses(jsonObject.getString("piece_processes"), context);
            }
            setMuertes(jsonObject.getInt("muertes"), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Piece fromSharedPreferences(Context context) {
        try {
            return new Piece(
                    getId(context),
                    getModel_id(context),
                    getName(context),
                    getProcesses(context),
                    getMuertes(context)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        return preferences.getInt("piece_data_1", -1);
    }

    public static int getModel_id(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        return preferences.getInt("piece_data_2", -1);
    }

    public static String getName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        return preferences.getString("piece_data_3", null);
    }

    public static JSONArray getProcesses(Context context) throws JSONException {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        return new JSONArray(preferences.getString("piece_data_4", null));
    }

    public static int getMuertes(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        return preferences.getInt("piece_data_5", -1);
    }

    public static void setId(int id, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("piece_data_1", id);
        editor.commit();
    }

    public static void setModel_id(int id, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("piece_data_2", id);
        editor.commit();
    }

    public static void setName(String name, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("piece_data_3", name);
        editor.commit();
    }

    public static void setProcesses(String processes, Context context) throws JSONException {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("piece_data_4", processes);
        editor.commit();
    }

    public static void setMuertes(int muertes, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("piece_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("piece_data_5", muertes);
        editor.commit();
    }

}
