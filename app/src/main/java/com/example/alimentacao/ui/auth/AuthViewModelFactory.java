package com.example.alimentacao.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alimentacao.api.ApiClient;
import com.example.alimentacao.api.ApiService;

public class AuthViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public AuthViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            ApiService apiService = ApiClient.getApiService(application.getApplicationContext());
            return (T) new AuthViewModel(application, apiService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
