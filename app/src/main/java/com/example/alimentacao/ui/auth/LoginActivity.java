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
import com.example.alimentacao.ui.auth.RegisterActivity;
import com.example.alimentacao.api.ApiClient;
import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.api.models.LoginResponse;
import com.example.alimentacao.ui.main.MainActivity;
import com.example.alimentacao.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
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

        // Verifica se usuário já está logado
        if (sessionManager.isLoggedIn()) {
            navigateToMain();
            return;
        }

        // Inicializa ViewModel com Factory
        ApiService apiService = ApiClient.getApiService();
        AuthViewModelFactory factory = new AuthViewModelFactory(apiService);
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        // Binding dos componentes
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Configura listeners
        btnLogin.setOnClickListener(v -> attemptLogin());
        tvRegister.setOnClickListener(v -> navigateToRegister());

        // Observa resultados do login
        observeLoginResult();
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (validateInputs(email, password)) {
            authViewModel.login(email, password);
        }
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            etEmail.setError("Email é obrigatório");
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Senha é obrigatória");
            return false;
        }

        return true;
    }

    private void observeLoginResult() {
        authViewModel.getLoginResult().observe(this, result -> {
            if (result == null) return;

            if (result.isLoading()) {
                showLoading(true);
                return;
            }

            showLoading(false);

            if (result.getError() != null) {
                showError(result.getError());
                return;
            }

            if (result.getData() != null && result.getData().isSuccess()) {
                handleSuccessfulLogin(result.getData());
            } else {
                showError("Login falhou");
            }
        });
    }

    private void handleSuccessfulLogin(LoginResponse loginResponse) {
        sessionManager.saveUser(loginResponse.getData());
        navigateToMain();
    }

    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void navigateToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void showLoading(boolean isLoading) {
        btnLogin.setEnabled(!isLoading);
        // Aqui você pode adicionar lógica para mostrar/esconder ProgressBar, se quiser
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
