package com.example.alimentacao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alimentacao.MyApp;

public class SessionManager {
    private static final String TOKEN_KEY = "auth_token";
    private static final String SHARED_PREF_NAME = "my_app";

    public static void saveAuthToken(String token) {
        SharedPreferences sharedPref = MyApp.getContext().getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().putString(TOKEN_KEY, token).apply();
    }

    public static String getAuthToken() {
        SharedPreferences sharedPref = MyApp.getContext().getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(TOKEN_KEY, null);
    }

    public static void clearAuthToken() {
        SharedPreferences sharedPref = MyApp.getContext().getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().remove(TOKEN_KEY).apply();
    }
}
