package com.example.qwerty.qrcodeejemplo;

import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwerty on 3/04/18.
 */

public class Pieza {
    private int id;
    private int model_id;
    private String name;
    private JSONArray processes;

    public Pieza(int id, int model_id, String name, JSONArray processes) {
        this.id = id;
        this.name = name;
        this.model_id = model_id;
        this.processes = processes;
    }

    public Pieza() {
    }

    public static Pieza fromJSON(JSONObject jsonObject) {
        try {
            Pieza pieza = new Pieza(jsonObject.getInt("piece_id"),
                    jsonObject.getInt("model_id"),
                    jsonObject.getString("piece_name"),
                    new JSONArray(jsonObject.getString("piece_processes"))
            );
            return pieza;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Pieza fromIntent(Intent intent) {
        try {
            return new Pieza(intent.getIntExtra("piece_id", 0),
                    intent.getIntExtra("model_id", 0),
                    intent.getStringExtra("piece_name"),
                    new JSONArray(intent.getStringExtra("piece_processes"))
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public int getModel_id() {
        return model_id;
    }

    public String getName() {
        return name;
    }

    public JSONArray getProcesses() {
        return processes;
    }
}
