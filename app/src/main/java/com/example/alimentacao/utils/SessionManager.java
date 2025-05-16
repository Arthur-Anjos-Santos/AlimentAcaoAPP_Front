package com.example.alimentacao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SessionManager {

    private static final String PREF_NAME = "AlimentacaoEncryptedPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "auth_user_id";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            pref = EncryptedSharedPreferences.create(
                    PREF_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            editor = pref.edit();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Erro ao inicializar EncryptedSharedPreferences", e);
        }
    }

    public void saveUser(String token, Long userId) {
        if (token != null) {
            editor.putString(KEY_TOKEN, token);
        }

        if (userId != null) {
            editor.putLong(KEY_USER_ID, userId);
        } else {
            editor.remove(KEY_USER_ID);
        }

        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public Long getUserId() {
        long id = pref.getLong(KEY_USER_ID, -1);
        return id != -1 ? id : null;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear().apply();
    }
}
