package com.example.alimentacao.ui.auth;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alimentacao.R;
import com.example.alimentacao.ui.fragments.CarteiraFragment;
import com.example.alimentacao.ui.fragments.DoacaoFragment;
import com.example.alimentacao.ui.fragments.HomeFragment;
import com.example.alimentacao.ui.fragments.MenuFragment;
import com.example.alimentacao.ui.fragments.QrCodeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Fragmento padrão ao abrir a tela
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_doacao) {
                selectedFragment = new DoacaoFragment();
            } else if (itemId == R.id.nav_qrcode) {
                selectedFragment = new QrCodeFragment();
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
    }
}
