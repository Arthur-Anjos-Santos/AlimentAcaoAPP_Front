package com.example.alimentacao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alimentacao.R;
import com.example.alimentacao.ui.auth.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    // Tempo que a splash screen ficará visível (em milissegundos)
    private static final int SPLASH_DELAY = 2000; // 2 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Usa um Handler para postergar a mudança de tela
        new Handler().postDelayed(() -> {
            // Inicia a LoginActivity após o tempo definido
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

            // Finaliza a SplashActivity para que o usuário não possa voltar para ela
            finish();

            // Adiciona transição suave entre as atividades
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, SPLASH_DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove as animações quando a activity perde foco
        overridePendingTransition(0, 0);
    }
}