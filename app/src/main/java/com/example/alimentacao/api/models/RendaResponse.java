package com.example.alimentacao.api.models;

public class RendaResponse {
    private double rendaPerCapita;
    private boolean ehBeneficiario;
    private String qrCode;

    public double getRendaPerCapita() {
        return rendaPerCapita;
    }

    public boolean isEhBeneficiario() {
        return ehBeneficiario;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
