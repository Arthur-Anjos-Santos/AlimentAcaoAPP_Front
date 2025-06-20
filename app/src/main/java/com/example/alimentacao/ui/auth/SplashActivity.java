package com.example.alimentacao.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alimentacao.R;

public class SplashActivity extends AppCompatActivity {

    private static final int DURATION = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.imgLogo);

        // Animação de fade-in
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        logo.startAnimation(animation);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, LoginActivity.class)); // ou MainActivity
            finish();
        }, DURATION);
    }
}
