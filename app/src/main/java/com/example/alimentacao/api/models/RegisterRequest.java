package com.example.alimentacao.api.models;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    private String nome;
    private String cpf;
    private String email;

    @SerializedName("senha")
    private String password;

    private String dataNascimento;
    private String tipoUsuario;

    public RegisterRequest(String nome, String cpf, String email, String password, String dataNascimento, String tipoUsuario) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.dataNascimento = dataNascimento;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
