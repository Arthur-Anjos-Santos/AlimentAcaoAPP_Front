package com.example.alimentacao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alimentacao.MyApp;

public class SessionManager {
    private static final String TOKEN_KEY = "auth_token";
    private static final String USER_NAME_KEY = "user_name";
    private static final String USER_EMAIL_KEY = "user_email";
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

    // Funções adicionadas abaixo

    public static void saveUserName(String name) {
        SharedPreferences sharedPref = MyApp.getContext().getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().putString(USER_NAME_KEY, name).apply();
    }

    public static String getUserName() {
        SharedPreferences sharedPref = MyApp.getContext().getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_NAME_KEY, "");
    }

    public static void saveUserEmail(String email) {
        SharedPreferences sharedPref = MyApp.getContext().getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().putString(USER_EMAIL_KEY, email).apply();
    }

    public static String getUserEmail() {
        SharedPreferences sharedPref = MyApp.getContext().getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_EMAIL_KEY, "");
    }

    public static void clearSession() {
        SharedPreferences sharedPref = MyApp.getContext().getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPref.edit()
                .remove(TOKEN_KEY)
                .remove(USER_NAME_KEY)
                .remove(USER_EMAIL_KEY)
                .apply();
    }
}