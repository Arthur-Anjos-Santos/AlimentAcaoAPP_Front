package com.example.alimentacao.ui.auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alimentacao.R;

public class QrCodeActivity extends AppCompatActivity {

    private TextView txtRenda, txtMensagem;
    private ImageView imgQrCode;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        txtRenda = findViewById(R.id.txtRenda);
        imgQrCode = findViewById(R.id.imgQrCode);
        txtMensagem = findViewById(R.id.txtMensagem);
        btnVoltar = findViewById(R.id.btnVoltar);

        double rendaPerCapita = getIntent().getDoubleExtra("rendaPerCapita", 0.0);
        String qrCodeBase64 = getIntent().getStringExtra("qrCode");

        String rendaFormatada = String.format("R$ %.2f", rendaPerCapita);
        txtRenda.setText("Sua renda per capita é: " + rendaFormatada);

        if (qrCodeBase64 != null && !qrCodeBase64.isEmpty()) {
            // Usuário pode receber: exibir QR Code
            byte[] decodedString = Base64.decode(qrCodeBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgQrCode.setImageBitmap(decodedByte);
            imgQrCode.setVisibility(ImageView.VISIBLE);

            txtRenda.append("\n\nApresente o QR Code abaixo no ponto de coleta mais próximo para receber o benefício.");
        } else {
            // Usuário não pode receber: exibir mensagem de inelegibilidade
            txtMensagem.setVisibility(TextView.VISIBLE);
        }

        // Botão OK sempre visível
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(QrCodeActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
