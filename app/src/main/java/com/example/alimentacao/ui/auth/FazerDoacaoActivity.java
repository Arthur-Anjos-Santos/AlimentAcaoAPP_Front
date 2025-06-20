package com.example.alimentacao.ui.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alimentacao.R;
import com.example.alimentacao.api.ApiClient;
import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.api.models.DoacaoRequest;
import com.example.alimentacao.api.models.DoacaoResponse;
import com.example.alimentacao.utils.SessionManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FazerDoacaoActivity extends AppCompatActivity {

    private EditText edtAlimento, edtQuantidade, edtComentarios;
    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_doacao);

        edtAlimento = findViewById(R.id.edtAlimento);
        edtQuantidade = findViewById(R.id.edtQuantidade);
        edtComentarios = findViewById(R.id.edtComentarios);
        Button btnEnviar = findViewById(R.id.btnEnviarDoacao);

        // Inicialização Retrofit + Token
        sessionManager = new SessionManager(getApplicationContext());
        apiService = ApiClient.getApiService(this);

        btnEnviar.setOnClickListener(v -> {
            String alimento = edtAlimento.getText().toString().trim();
            String quantidadeStr = edtQuantidade.getText().toString().trim();
            String comentario = edtComentarios.getText().toString().trim();

            if (alimento.isEmpty() || quantidadeStr.isEmpty()) {
                Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantidade;
            try {
                quantidade = Integer.parseInt(quantidadeStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show();
                return;
            }

            DoacaoRequest request = new DoacaoRequest(alimento, quantidade, comentario);
            enviarDoacaoParaAPI(request);
        });
    }

    private void enviarDoacaoParaAPI(DoacaoRequest request) {
        Call<ResponseBody> call = apiService.realizarDoacao(request);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String mensagem = response.body().string();
                        Toast.makeText(FazerDoacaoActivity.this, mensagem, Toast.LENGTH_LONG).show();
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(FazerDoacaoActivity.this, "Erro ao ler resposta da API", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FazerDoacaoActivity.this, "Erro ao realizar doação", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(FazerDoacaoActivity.this, "Erro de conexão com a API", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
