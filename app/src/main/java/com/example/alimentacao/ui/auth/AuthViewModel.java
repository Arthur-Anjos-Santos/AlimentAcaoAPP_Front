package com.example.alimentacao.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.api.models.EnderecoRequest;
import com.example.alimentacao.api.models.LoginRequest;
import com.example.alimentacao.api.models.LoginResponse;
import com.example.alimentacao.api.models.RegisterRequest;
import com.example.alimentacao.api.models.RegisterResponse;
import com.example.alimentacao.api.models.UsuarioRequest;
import com.example.alimentacao.utils.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private final ApiService apiService;
    private final MutableLiveData<Result<LoginResponse>> loginResult = new MutableLiveData<>();
    private final MutableLiveData<Result<RegisterResponse>> registerResult = new MutableLiveData<>();

    public AuthViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<Result<LoginResponse>> getLoginResult() {
        return loginResult;
    }

    public LiveData<Result<RegisterResponse>> getRegisterResult() {
        return registerResult;
    }

    public void login(String email, String password) {
        loginResult.setValue(Result.loading());
        Call<LoginResponse> call = apiService.login(new LoginRequest(email, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginResult.setValue(Result.success(response.body()));
                } else {
                    loginResult.setValue(Result.error("Credenciais inválidas"));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResult.setValue(Result.error(t.getMessage()));
            }
        });
    }

    public void register(UsuarioRequest usuario, EnderecoRequest endereco) {
        registerResult.setValue(Result.loading());

        RegisterRequest request = new RegisterRequest(usuario, endereco);

        Call<RegisterResponse> call = apiService.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registerResult.setValue(Result.success(response.body()));
                } else {
                    registerResult.setValue(Result.error("Falha no cadastro"));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                registerResult.setValue(Result.error(t.getMessage()));
            }
        });
    }
}
