package com.example.alimentacao.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.alimentacao.R;
import com.example.alimentacao.api.models.RegisterResponse;
import com.example.alimentacao.databinding.ActivityRegisterBinding;
import com.example.alimentacao.ui.main.MainActivity;
import com.example.alimentacao.utils.Result;
import com.example.alimentacao.utils.SessionManager;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa o View Binding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        setupUI();
        observeViewModel();
    }

    private void setupUI() {
        binding.btnRegister.setOnClickListener(v -> attemptRegister());

        binding.tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void attemptRegister() {
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String passwordConfirmation = binding.etPasswordConfirmation.getText().toString().trim();

        if (validateInputs(name, email, password, passwordConfirmation)) {
            viewModel.register(name, email, password, passwordConfirmation);
        }
    }

    private boolean validateInputs(String name, String email, String password, String passwordConfirmation) {
        boolean isValid = true;

        if (name.isEmpty()) {
            binding.etName.setError(getString(R.string.error_name_required));
            isValid = false;
        }

        if (email.isEmpty()) {
            binding.etEmail.setError(getString(R.string.error_email_required));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError(getString(R.string.error_invalid_email));
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.etPassword.setError(getString(R.string.error_password_required));
            isValid = false;
        } else if (password.length() < 6) {
            binding.etPassword.setError(getString(R.string.error_short_password));
            isValid = false;
        }

        if (!password.equals(passwordConfirmation)) {
            binding.etPasswordConfirmation.setError(getString(R.string.error_password_mismatch));
            isValid = false;
        }

        return isValid;
    }

    private void observeViewModel() {
        viewModel.getRegisterResult().observe(this, result -> {
            if (result instanceof Result.Success) {
                handleRegisterSuccess(((Result.Success<RegisterResponse>) result).data);
            } else if (result instanceof Result.Error) {
                handleRegisterError(((Result.Error<RegisterResponse>) result).message);
            } else if (result instanceof Result.Loading) {
                showLoading(true);
            }
        });
    }

    private void handleRegisterSuccess(RegisterResponse response) {
        showLoading(false);

        if (response.isSuccess()) {
            SessionManager.saveAuthToken(response.getToken());

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleRegisterError(String errorMessage) {
        showLoading(false);
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private void showLoading(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.btnRegister.setEnabled(!isLoading);
    }
}