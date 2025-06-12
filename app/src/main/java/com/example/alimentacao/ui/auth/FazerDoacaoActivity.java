package com.example.alimentacao.ui.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alimentacao.R;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FazerDoacaoActivity extends AppCompatActivity {

    private EditText edtAlimento, edtQuantidade, edtComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_doacao);

        edtAlimento = findViewById(R.id.edtAlimento);
        edtQuantidade = findViewById(R.id.edtQuantidade);
        edtComentarios = findViewById(R.id.edtComentarios);
        Button btnEnviar = findViewById(R.id.btnEnviarDoacao);

        btnEnviar.setOnClickListener(v -> {
            String alimento = edtAlimento.getText().toString().trim();
            String quantidade = edtQuantidade.getText().toString().trim();
            String comentarios = edtComentarios.getText().toString().trim();

            if (alimento.isEmpty() || quantidade.isEmpty()) {
                Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            enviarDoacaoParaAPI(alimento, quantidade, comentarios);
        });
    }

    private void enviarDoacaoParaAPI(String alimento, String quantidade, String comentarios) {
        new Thread(() -> {
            try {
                URL url = new URL("https://sua-api.com/doacoes"); // Substitua pela URL da sua API
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("alimento", alimento);
                json.put("quantidade", quantidade);
                json.put("comentarios", comentarios);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.toString().getBytes("UTF-8"));
                }

                int responseCode = conn.getResponseCode();
                runOnUiThread(() -> {
                    if (responseCode == 200 || responseCode == 201) {
                        Toast.makeText(this, "Obrigado pela doação! Vá até o ponto de coleta mais próximo.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erro ao enviar doação. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Erro de conexão com a API", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}
