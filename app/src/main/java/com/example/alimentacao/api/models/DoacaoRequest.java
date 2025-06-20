package com.example.alimentacao.api.models;

public class DoacaoRequest {
    private String doacao;
    private int quantidade;
    private String comentario;

    public DoacaoRequest(String doacao, int quantidade, String comentario) {
        this.doacao = doacao;
        this.quantidade = quantidade;
        this.comentario = comentario;
    }

    // Getters e setters (opcional)
}
