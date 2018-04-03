package com.example.qwerty.qrcodeejemplo;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwerty on 3/04/18.
 */

public class Pieza {
    private String id;
    private String name;
    private long time;

    public Pieza(String id, String name, long time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public Pieza() {
    }

    public static Pieza fromJSON(JSONObject jsonObject) {
        try {
            return new Pieza(jsonObject.getString("id"),
                    jsonObject.getString("nombre"),
                    jsonObject.getLong("tiempo"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Pieza fromIntent(Intent intent) {
        try {
            return new Pieza(intent.getStringExtra("id"),
                    intent.getStringExtra("name"),
                    intent.getLongExtra("time", 0));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }
}
