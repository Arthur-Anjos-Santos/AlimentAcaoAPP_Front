package com.example.alimentacao.ui.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alimentacao.api.ApiService;

public class AuthViewModelFactory implements ViewModelProvider.Factory {
    private final ApiService apiService;

    public AuthViewModelFactory(ApiService apiService) {
        this.apiService = apiService;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(apiService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
