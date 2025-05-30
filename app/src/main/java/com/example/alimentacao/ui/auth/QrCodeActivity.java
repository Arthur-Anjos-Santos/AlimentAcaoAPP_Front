package com.example.alimentacao.ui.auth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alimentacao.R;

public class QrCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        double rendaPerCapita = getIntent().getDoubleExtra("rendaPerCapita", 0.0);
        String qrCodeBase64 = getIntent().getStringExtra("qrCode");

        TextView txtRenda = findViewById(R.id.txtRenda);
        ImageView imgQrCode = findViewById(R.id.imgQrCode);

        String rendaFormatada = String.format("R$ %.2f", rendaPerCapita);
        txtRenda.setText("Sua renda per capita é: " + rendaFormatada +
                "\n\nApresente o QR Code abaixo no ponto de coleta mais próximo para receber o benefício.");

        if (qrCodeBase64 != null) {
            byte[] decodedString = Base64.decode(qrCodeBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgQrCode.setImageBitmap(decodedByte);
        }
    }

}
