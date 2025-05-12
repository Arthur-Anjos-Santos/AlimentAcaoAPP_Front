package com.example.alimentacao.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.alimentacao.R;
import com.example.alimentacao.ui.auth.LoginActivity;
import com.example.alimentacao.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa SessionManager
        sessionManager = new SessionManager(this);

        // Configura a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Configura os componentes
        setupViews();
        setupNavigation();
        setupBottomNavigation();

        // Exibe o nome do usuário
        displayUserInfo();
    }

    private void setupViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        drawerLayout = findViewById(R.id.drawerLayout);
    }

    private void setupNavigation() {
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        // Configura o ícone do menu hamburguer
        findViewById(R.id.ivMenu).setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                showToast("Home selecionado");
                return true;
            } else if (id == R.id.nav_diet) {
                showToast("Dieta selecionada");
                return true;
            } else if (id == R.id.nav_profile) {
                showToast("Perfil selecionado");
                return true;
            }

            return false;
        });
    }


    private void displayUserInfo() {
        String userName = sessionManager.getUser().getNome();
        tvWelcome.setText(String.format("Bem-vindo, %s!", userName));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            showToast("Configurações selecionadas");
        } else if (id == R.id.nav_help) {
            showToast("Ajuda selecionada");
        } else if (id == R.id.nav_about) {
            showToast("Sobre selecionado");
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        sessionManager.logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}