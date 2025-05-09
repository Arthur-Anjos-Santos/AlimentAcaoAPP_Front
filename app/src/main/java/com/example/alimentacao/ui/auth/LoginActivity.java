package com.example.alimentacao.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.alimentacao.api.models.LoginResponse;
import com.example.alimentacao.databinding.ActivityLoginBinding;
import com.example.alimentacao.ui.main.MainActivity;
import com.example.alimentacao.utils.Result;
import com.example.alimentacao.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();

            if (validateInputs(email, password)) {
                viewModel.login(email, password);
            }
        });

        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        observeViewModel();
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Email inválido");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            binding.etPassword.setError("Senha deve ter pelo menos 6 caracteres");
            return false;
        }

        return true;
    }

    private void observeViewModel() {
        viewModel.getLoginResult().observe(this, result -> {
            if (result instanceof Result.Success) {
                LoginResponse response = ((Result.Success<LoginResponse>) result).data;

                // Salvar todas as informações na sessão
                SessionManager.saveAuthToken(response.getToken());
                SessionManager.saveUserEmail(response.getEmail());
                SessionManager.saveUserName(response.getName());

                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if (result instanceof Result.Error) {
                String message = ((Result.Error<LoginResponse>) result).message;
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}