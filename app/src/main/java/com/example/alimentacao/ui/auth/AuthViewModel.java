package com.example.alimentacao.ui.auth;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.api.models.*;

import com.example.alimentacao.utils.Result;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {
    private final ApiService apiService;
    private final MutableLiveData<Result<LoginResponse>> loginResult = new MutableLiveData<>();
    private final MutableLiveData<Result<String>> registerResult = new MutableLiveData<>();

    public AuthViewModel(Application application, ApiService apiService) {
        super(application);
        this.apiService = apiService;
    }

    public LiveData<Result<LoginResponse>> getLoginResult() {
        return loginResult;
    }

    public LiveData<Result<String>> getRegisterResult() {
        return registerResult;
    }

    public void login(String email, String password) {
        loginResult.setValue(Result.loading());
        Call<LoginResponse> call = apiService.login(new LoginRequest(email, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        saveToken(loginResponse.getToken());
                        loginResult.setValue(Result.success(loginResponse));
                    } else {
                        loginResult.setValue(Result.error("Resposta inválida do servidor"));
                    }
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

    private void saveToken(String token) {
        SharedPreferences prefs = getApplication().getSharedPreferences("auth", Application.MODE_PRIVATE);
        prefs.edit().putString("jwt_token", token).apply();
    }

    public void register(UsuarioRequest usuario, EnderecoRequest endereco) {
        registerResult.setValue(Result.loading());

        RegisterRequest request = new RegisterRequest(usuario, endereco);
        Call<ResponseBody> call = apiService.register(request);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseText = response.body().string();
                        registerResult.setValue(Result.success(responseText));
                    } catch (Exception e) {
                        registerResult.setValue(Result.error("Erro ao ler resposta"));
                    }
                } else {
                    registerResult.setValue(Result.error("Falha no cadastro"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                registerResult.setValue(Result.error(t.getMessage()));
            }
        });
    }
}
