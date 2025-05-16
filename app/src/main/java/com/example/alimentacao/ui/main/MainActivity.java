package com.example.alimentacao.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
        setContentView(R.layout.activity_mainnaousa);

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
        setupOptionsMenu();

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

    private void setupOptionsMenu() {
        ImageView ivOptions = findViewById(R.id.ivOptions);
        ivOptions.setOnClickListener(v -> showPopupMenu(v));
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                logout();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void displayUserInfo() {
        Long userId = sessionManager.getUserId();
        if (userId != null) {
            tvWelcome.setText(String.format("Bem-vindo, usuário #%d!", userId));
        } else {
            tvWelcome.setText("Bem-vindo!");
        }
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
