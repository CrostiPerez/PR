package com.example.qwerty.qrcodeejemplo.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.qwerty.qrcodeejemplo.database.DbSchema.*;

/**
 * Created by qwerty on 3/04/18.
 */

public class Piece {
    private static final String PIECE_DATA = "piece_data";
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
            setId(jsonObject.getInt(PieceTable.Cols.ID), context);
            setModel_id(jsonObject.getInt(ModelTable.Cols.ID), context);
            setName(jsonObject.getString(PieceTable.Cols.NAME), context);
            if (jsonObject.getString(PieceTable.Cols.PROCESSES).isEmpty()) {
                setProcesses(new JSONArray().toString(), context);
            } else {
                setProcesses(jsonObject.getString(PieceTable.Cols.PROCESSES), context);
            }
            setMuertes(jsonObject.getInt(PieceTable.Cols.MUERTES), context);
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
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        return preferences.getInt("piece_data_1", -1);
    }

    public static int getModel_id(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        return preferences.getInt("piece_data_2", -1);
    }

    public static String getName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        return preferences.getString("piece_data_3", null);
    }

    public static JSONArray getProcesses(Context context) throws JSONException {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        return new JSONArray(preferences.getString("piece_data_4", null));
    }

    public static int getMuertes(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        return preferences.getInt("piece_data_5", -1);
    }

    public static void setId(int id, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("piece_data_1", id);
        editor.apply();
    }

    public static void setModel_id(int id, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("piece_data_2", id);
        editor.apply();
    }

    public static void setName(String name, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("piece_data_3", name);
        editor.apply();
    }

    public static void setProcesses(String processes, Context context) throws JSONException {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("piece_data_4", processes);
        editor.apply();
    }

    public static void setMuertes(int muertes, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PIECE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("piece_data_5", muertes);
        editor.apply();
    }

}
