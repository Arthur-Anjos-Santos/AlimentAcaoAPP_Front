package com.example.alimentacao.api.models;

public class LoginResponse {
    private boolean success;
    private String message;
    private UserData data;

    // Getters e Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public UserData getData() { return data; }
    public void setData(UserData data) { this.data = data; }

    public static class UserData {
        private Long id;
        private String nome;
        private String email;
        private String cpf;

        // Getters e Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCpf() { return cpf; }
        public void setCpf(String cpf) { this.cpf = cpf; }
    }
}