package com.example.alimentacao.api;

import android.content.Context;
import android.util.Log;

import com.example.alimentacao.utils.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private final SessionManager sessionManager;

    public TokenInterceptor(Context context) {
        this.sessionManager = new SessionManager(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = sessionManager.getToken();

        Request original = chain.request();
        Request.Builder builder = original.newBuilder();

        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
            Log.d("TOKEN_INTERCEPTOR", "Token enviado: " + token); // Para depuração
        } else {
            Log.d("TOKEN_INTERCEPTOR", "Token é nulo");
        }

        Request request = builder.build();
        return chain.proceed(request);
    }
}
