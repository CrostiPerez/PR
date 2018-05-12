package com.example.qwerty.qrcodeejemplo;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
    private int muertes;

    public Pieza(int id, int model_id, String name, JSONArray processes, int muertes) {
        this.id = id;
        this.name = name;
        this.model_id = model_id;
        this.processes = processes;
        this.muertes = muertes;
    }

    public Pieza() {
    }

    public static Pieza fromJSON(JSONObject jsonObject) {
        try {
            return new Pieza(jsonObject.getInt("piece_id"),
                    jsonObject.getInt("model_id"),
                    jsonObject.getString("piece_name"),
                    new JSONArray(jsonObject.getString("piece_processes")),
                    jsonObject.getInt("muertes")
            );
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
                    new JSONArray(intent.getStringExtra("piece_processes")),
                    intent.getIntExtra("muertes", 0)
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

    public void setProcesses(JSONArray processes) { this.processes = processes; }

    public int getMuertes() { return this.muertes; }
}
