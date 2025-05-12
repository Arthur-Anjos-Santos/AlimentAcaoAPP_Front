package com.example.alimentacao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alimentacao.api.models.LoginResponse;
import com.google.gson.Gson;

public class SessionManager {
    private static final String PREF_NAME = "AlimentacaoPref";
    private static final String KEY_USER = "user";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private Gson gson = new Gson();

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveUser(LoginResponse.UserData user) {
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER, userJson);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public LoginResponse.UserData getUser() {
        String userJson = pref.getString(KEY_USER, null);
        return gson.fromJson(userJson, LoginResponse.UserData.class);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}