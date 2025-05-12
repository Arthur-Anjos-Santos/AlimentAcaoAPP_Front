package com.example.alimentacao.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alimentacao.R;
import com.example.alimentacao.api.ApiClient;
import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.utils.SessionManager;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etCpf, etEmail, etPassword;
    private Button btnRegister;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializa ViewModel
        ApiService apiService = ApiClient.getApiService(); // correto
        AuthViewModelFactory factory = new AuthViewModelFactory(apiService);
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        // Binding dos componentes
        etName = findViewById(R.id.etName);
        etCpf = findViewById(R.id.etCpf);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Configura listener
        btnRegister.setOnClickListener(v -> attemptRegister());

        // Observa resultados do registro
        observeRegisterResult();
    }

    private void attemptRegister() {
        String name = etName.getText().toString().trim();
        String cpf = etCpf.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (validateInputs(name, cpf, email, password)) {
            authViewModel.register(name, cpf, email, password);
        }
    }

    private boolean validateInputs(String name, String cpf, String email, String password) {
        boolean isValid = true;

        if (name.isEmpty()) {
            etName.setError("Nome é obrigatório");
            isValid = false;
        }

        if (cpf.isEmpty()) {
            etCpf.setError("CPF é obrigatório");
            isValid = false;
        } else if (cpf.length() != 11) {
            etCpf.setError("CPF deve ter 11 dígitos");
            isValid = false;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email é obrigatório");
            isValid = false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Senha é obrigatória");
            isValid = false;
        } else if (password.length() < 6) {
            etPassword.setError("Senha deve ter pelo menos 6 caracteres");
            isValid = false;
        }

        return isValid;
    }

    private void observeRegisterResult() {
        authViewModel.getRegisterResult().observe(this, result -> {
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
                handleSuccessfulRegister();
            } else {
                showError("Falha no cadastro");
            }
        });
    }

    private void handleSuccessfulRegister() {
        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
        finish(); // Volta para a tela de login
    }

    private void showLoading(boolean isLoading) {
        // Implemente sua lógica de loading (progress bar)
        btnRegister.setEnabled(!isLoading);
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}