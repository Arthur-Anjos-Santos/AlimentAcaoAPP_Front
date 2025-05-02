package com.example.alimentacao.api;

import com.example.alimentacao.api.models.LoginRequest;
import com.example.alimentacao.api.models.LoginResponse;
import com.example.alimentacao.api.models.RegisterRequest;
import com.example.alimentacao.api.models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}
