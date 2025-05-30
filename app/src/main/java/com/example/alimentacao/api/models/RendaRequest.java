package com.example.alimentacao.api.models;

public class RendaRequest {
    private double valorRendaTotal;
    private int quantidadePessoas;

    public RendaRequest(double valorRendaTotal, int quantidadePessoas) {
        this.valorRendaTotal = valorRendaTotal;
        this.quantidadePessoas = quantidadePessoas;
    }

    public double getValorRendaTotal() {
        return valorRendaTotal;
    }

    public void setValorRendaTotal(double valorRendaTotal) {
        this.valorRendaTotal = valorRendaTotal;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(int quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }
}
