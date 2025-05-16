package com.example.alimentacao.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.alimentacao.ui.auth.HomeActivity;
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

        // Inicializa SessionManager
        sessionManager = new SessionManager(this);

        // Verifica se o usuário já está logado
        if (sessionManager.isLoggedIn()) {
            navigateToHome();
            return;
        }

        // Inicializa ViewModel com Factory
        ApiService apiService = ApiClient.getApiService();
        AuthViewModelFactory factory = new AuthViewModelFactory(apiService);
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        // Binding dos componentes
        etLogin = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Ações
        btnLogin.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            authViewModel.login(login, password);
        });

        tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        // Observa resposta da API
        authViewModel.getLoginResult().observe(this, result -> {
            if (result == null) return;

            showLoading(result.isLoading());

            if (result.getError() != null) {
                showError(result.getError());
                return;
            }

            if (result.getData() != null && result.getData().isSuccess()) {
                handleSuccessfulLogin(result.getData());
            } else {
                showError("Falha no login. Verifique suas credenciais.");
            }
        });
    }

    private void handleSuccessfulLogin(LoginResponse loginResponse) {
        // Salvando token e id (sem nome de usuário)
        sessionManager.saveUser(loginResponse.getToken(), loginResponse.getId());

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
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
