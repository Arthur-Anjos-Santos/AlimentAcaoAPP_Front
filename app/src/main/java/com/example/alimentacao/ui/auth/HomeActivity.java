package com.example.alimentacao.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alimentacao.R;
import com.example.alimentacao.ui.fragments.CarteiraFragment;
import com.example.alimentacao.ui.fragments.DoacaoFragment;
import com.example.alimentacao.ui.fragments.HomeFragment;
import com.example.alimentacao.ui.fragments.MenuFragment;
import com.example.alimentacao.ui.fragments.MapsFragment;
import com.example.alimentacao.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Fragmento padrão ao abrir a tela
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            Log.d("NAVIGATION", "Clicou em: " + item.getItemId());

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_doacao) {
                selectedFragment = new DoacaoFragment();
            } else if (itemId == R.id.nav_qrcode) {
                selectedFragment = new MapsFragment();
            } else if (itemId == R.id.nav_wallet) {
                selectedFragment = new CarteiraFragment();
            } else if (itemId == R.id.nav_menu) {
                selectedFragment = new MenuFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });


        // Configura o clique no ícone de perfil para abrir o menu de logout
        ImageView ivProfile = findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(this::showProfileMenu);
    }

    private void showProfileMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                performLogout();
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void performLogout() {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.logout();
        Toast.makeText(this, "Logout realizado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
