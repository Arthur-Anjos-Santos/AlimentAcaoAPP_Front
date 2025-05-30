package com.example.alimentacao.ui.auth;

import androidx.annotation.ReturnThis;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alimentacao.R;
import com.example.alimentacao.api.ApiClient;
import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.api.models.LoginResponse;
import com.example.alimentacao.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private AuthViewModel authViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        // Redireciona se já estiver logado
        if (sessionManager.isLoggedIn()) {
            navigateToHome();
            return;
        }

        // Inicializa API e ViewModel
        ApiService apiService = ApiClient.getApiService(this);
        AuthViewModelFactory factory = new AuthViewModelFactory(getApplication());
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        // Bind views
        etLogin = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Login
        btnLogin.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (login.isEmpty() || password.isEmpty()) {
                showError("Preencha todos os campos.");
                return;
            }

            authViewModel.login(login, password);
        });

        // Cadastro
        tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        // Observador da resposta de login
        authViewModel.getLoginResult().observe(this, result -> {
            if (result == null) return;

            showLoading(result.isLoading());

            if (result.getError() != null) {
                showError(result.getError());
                return;
            }

            if (result.getData() != null) {
                handleSuccessfulLogin(result.getData());
            }
        });
    }

    private void handleSuccessfulLogin(LoginResponse loginResponse) {
        sessionManager.saveUser(loginResponse.getToken(), loginResponse.getId());
        navigateToHome();
    }

    private void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void showLoading(boolean isLoading) {
        btnLogin.setEnabled(!isLoading);
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
