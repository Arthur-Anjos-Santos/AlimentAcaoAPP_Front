package com.example.alimentacao.api.models;

public class RegisterRequest {
    private UsuarioRequest usuario;
    private EnderecoRequest endereco;

    public RegisterRequest(UsuarioRequest usuario, EnderecoRequest endereco) {
        this.usuario = usuario;
        this.endereco = endereco;
    }

    public UsuarioRequest getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioRequest usuario) {
        this.usuario = usuario;
    }

    public EnderecoRequest getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequest endereco) {
        this.endereco = endereco;
    }
}
