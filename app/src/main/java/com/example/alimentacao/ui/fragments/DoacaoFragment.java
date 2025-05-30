package com.example.alimentacao.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alimentacao.R;
import com.example.alimentacao.ui.auth.ReceberDoacaoActivity;

public class DoacaoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("DoacaoFragment", "onCreateView chamado");
        View view = inflater.inflate(R.layout.fragment_doacao, container, false);

        Button btnFazerDoacao = view.findViewById(R.id.btn_fazer_doacao);
        Button btnReceberDoacao = view.findViewById(R.id.btn_receber_doacao);

        btnFazerDoacao.setOnClickListener(v ->
                Toast.makeText(getContext(), "Opção: Fazer Doação", Toast.LENGTH_SHORT).show()
        );

        btnReceberDoacao.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReceberDoacaoActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
