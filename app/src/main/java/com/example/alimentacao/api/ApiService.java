package com.example.alimentacao.api;

import com.example.alimentacao.api.models.EnderecoResponse;
import com.example.alimentacao.api.models.LoginRequest;
import com.example.alimentacao.api.models.LoginResponse;
import com.example.alimentacao.api.models.RegisterRequest;
import com.example.alimentacao.api.models.RegisterResponse;
import com.example.alimentacao.api.models.RendaRequest;
import com.example.alimentacao.api.models.RendaResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("usuario")
    Call<ResponseBody> register(@Body RegisterRequest registerRequest);

    @PUT("usuario/{id}")
    Call<RendaResponse> verificarRenda(@Path("id") Long id, @Body RendaRequest request);

    @GET("/endereco/cep/{cep}")
    Call<EnderecoResponse> buscarEnderecoPorCep(@Path("cep") String cep);

}
