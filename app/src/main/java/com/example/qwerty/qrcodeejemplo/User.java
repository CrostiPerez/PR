package com.example.qwerty.qrcodeejemplo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwerty on 28/04/18.
 */

public class User {
    private String username;
    private String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public static User fromJSON(JSONObject json) {
        try {
            return new User(json.getString("login_username"),
                    json.getString("login_password"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
