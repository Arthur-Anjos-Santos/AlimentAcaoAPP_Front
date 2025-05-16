package com.example.alimentacao.ui.auth;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alimentacao.R;
import com.example.alimentacao.api.ApiClient;
import com.example.alimentacao.api.ApiService;
import com.example.alimentacao.api.models.EnderecoRequest;
import com.example.alimentacao.api.models.RegisterRequest;
import com.example.alimentacao.api.models.RegisterResponse;
import com.example.alimentacao.api.models.UsuarioRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etCpf, etEmail, etPassword, etDateOfBirth;
    private EditText etStreet, etNumber, etDistrict, etCity, etState, etCep, etComplement;
    private Button btnNext, btnRegister;
    private LinearLayout layoutUser, layoutAddress;

    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Dados do usuário
        etName = findViewById(R.id.etName);
        etCpf = findViewById(R.id.etCpf);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);

        // Dados de endereço
        etStreet = findViewById(R.id.etStreet);
        etNumber = findViewById(R.id.etNumber);
        etDistrict = findViewById(R.id.etDistrict);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etCep = findViewById(R.id.etCep);
        etComplement = findViewById(R.id.etComplement);

        // Botões e layouts
        btnNext = findViewById(R.id.btnNext);
        btnRegister = findViewById(R.id.btnRegister);
        layoutUser = findViewById(R.id.layoutUser);
        layoutAddress = findViewById(R.id.layoutAddress);

        etDateOfBirth.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                etDateOfBirth.setText(apiDateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnNext.setOnClickListener(v -> {
            String nome = etName.getText().toString().trim();
            String cpf = etCpf.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String senha = etPassword.getText().toString().trim();
            String dataNascimento = etDateOfBirth.getText().toString().trim();

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty() || dataNascimento.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                layoutUser.setVisibility(LinearLayout.GONE);
                layoutAddress.setVisibility(LinearLayout.VISIBLE);
            }
        });

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        // Usuário
        String nome = etName.getText().toString().trim();
        String cpf = etCpf.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etPassword.getText().toString().trim();
        String dataNascimento = etDateOfBirth.getText().toString().trim();

        // Endereço
        String rua = etStreet.getText().toString().trim();
        String numero = etNumber.getText().toString().trim();
        String bairro = etDistrict.getText().toString().trim();
        String cidade = etCity.getText().toString().trim();
        String estado = etState.getText().toString().trim();
        String cep = etCep.getText().toString().trim();
        String complemento = etComplement.getText().toString().trim();

        if (rua.isEmpty() || numero.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty() || cep.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos de endereço", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioRequest usuario = new UsuarioRequest(nome, cpf, email, senha, dataNascimento, "beneficiario");
        EnderecoRequest endereco = new EnderecoRequest(rua, numero, bairro, cidade, estado, cep, complemento);
        RegisterRequest request = new RegisterRequest(usuario, endereco);

        ApiService apiService = ApiClient.getApiService();
        Call<RegisterResponse> call = apiService.register(request);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
