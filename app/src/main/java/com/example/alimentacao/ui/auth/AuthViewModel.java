package com.example.alimentacao.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.alimentacao.api.ApiClient;
import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.api.models.LoginRequest;
import com.example.alimentacao.api.models.LoginResponse;
import com.example.alimentacao.api.models.RegisterRequest;
import com.example.alimentacao.api.models.RegisterResponse;
import com.example.alimentacao.utils.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private final MutableLiveData<Result<LoginResponse>> loginResult = new MutableLiveData<>();
    private final MutableLiveData<Result<RegisterResponse>> registerResult = new MutableLiveData<>();

    private final ApiService apiService = ApiClient.getApiService();

    // Login methods
    public LiveData<Result<LoginResponse>> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        loginResult.setValue(new Result.Loading<>());

        apiService.login(new LoginRequest(email, password))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            loginResult.setValue(new Result.Success<>(response.body()));
                        } else {
                            String errorMessage = "Falha no login";
                            if (response.errorBody() != null) {
                                try {
                                    errorMessage = response.errorBody().string();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            loginResult.setValue(new Result.Error<>(errorMessage));
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        loginResult.setValue(new Result.Error<>(t.getMessage() != null ?
                                t.getMessage() : "Erro desconhecido"));
                    }
                });
    }

    // Register methods
    public LiveData<Result<RegisterResponse>> getRegisterResult() {
        return registerResult;
    }

    public void register(String name, String email, String password, String passwordConfirmation) {
        registerResult.setValue(new Result.Loading<>());

        apiService.register(new RegisterRequest(name, email, password, passwordConfirmation))
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            registerResult.setValue(new Result.Success<>(response.body()));
                        } else {
                            String errorMessage = "Falha no registro";
                            if (response.errorBody() != null) {
                                try {
                                    errorMessage = response.errorBody().string();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            registerResult.setValue(new Result.Error<>(errorMessage));
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        registerResult.setValue(new Result.Error<>(t.getMessage() != null ?
                                t.getMessage() : "Erro desconhecido"));
                    }
                });
    }

    // Reset password methods (opcional)
    public void resetPassword(String email) {
        // Implementação similar para redefinição de senha
    }
}