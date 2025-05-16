package com.example.alimentacao.api.models;

public class LoginResponse {
    private String token;
    private int id;

    public String getToken() {
        return token;
    }

    public long getId() {
        return id;
    }

    public boolean isSuccess() {
        return token != null && !token.isEmpty() && id > 0;
    }
}
