package com.example.alimentacao.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alimentacao.R;
import com.example.alimentacao.databinding.ActivityMainBinding;
import com.example.alimentacao.ui.auth.LoginActivity;
import com.example.alimentacao.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkUserSession();
        setupUI();
    }

    private void checkUserSession() {
        if (SessionManager.getAuthToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void setupUI() {
        setSupportActionBar(binding.toolbar);

        // Exibir informações do usuário
        binding.tvWelcome.setText(String.format(
                getString(R.string.welcome_message),
                SessionManager.getUserName()
        ));

        binding.tvEmail.setText(SessionManager.getUserEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SessionManager.clearSession();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
