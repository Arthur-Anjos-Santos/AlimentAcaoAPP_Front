package com.example.alimentacao.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alimentacao.R;
import com.example.alimentacao.api.ApiClient;
import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.api.models.RendaRequest;
import com.example.alimentacao.api.models.RendaResponse;
import com.example.alimentacao.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceberDoacaoActivity extends AppCompatActivity {

    private EditText edtRendaTotal, edtQuantidadePessoas;
    private Button btnVerificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_doacao);

        edtRendaTotal = findViewById(R.id.edtRendaTotal);
        edtQuantidadePessoas = findViewById(R.id.edtQuantidadePessoas);
        btnVerificar = findViewById(R.id.btnVerificar);

        btnVerificar.setOnClickListener(view -> verificarBeneficio());
    }

    private void verificarBeneficio() {
        double rendaTotal = Double.parseDouble(edtRendaTotal.getText().toString());
        int quantidade = Integer.parseInt(edtQuantidadePessoas.getText().toString());

        RendaRequest request = new RendaRequest(rendaTotal, quantidade);

        SessionManager sessionManager = new SessionManager(this);
        Long userId = sessionManager.getUserId();

        if (userId == null) {
            Toast.makeText(this, "Erro: usuário não identificado.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getApiService(this);
        apiService.verificarRenda(userId, request).enqueue(new Callback<RendaResponse>() {
            @Override
            public void onResponse(Call<RendaResponse> call, Response<RendaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean podeReceber = response.body().isEhBeneficiario();
                    double rendaPerCapita = response.body().getRendaPerCapita();
                    String qrCode = response.body().getQrCode();

                    if (podeReceber && qrCode != null) {
                        Intent intent = new Intent(ReceberDoacaoActivity.this, QrCodeActivity.class);
                        intent.putExtra("rendaPerCapita", rendaPerCapita);
                        intent.putExtra("qrCode", qrCode);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ReceberDoacaoActivity.this, "Você não se qualifica. Renda per capita: R$ " + rendaPerCapita, Toast.LENGTH_LONG).show();
                        finish(); // Fecha a tela
                    }
                } else {
                    Toast.makeText(ReceberDoacaoActivity.this, "Erro ao verificar benefício.", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<RendaResponse> call, Throwable t) {
                Toast.makeText(ReceberDoacaoActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
