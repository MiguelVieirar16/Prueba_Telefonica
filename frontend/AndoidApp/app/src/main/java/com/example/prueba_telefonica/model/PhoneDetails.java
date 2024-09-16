package com.example.prueba_telefonica.model;

public class PhoneDetails {
    private String numeroMovil; //número de teléfono
    private String plataforma; //plataforma del teléfono
    private String saldo; //saldo del teléfono
    private String datosRestantes; //datos restantes del teléfono
    private String plan;

    public String getNumeroMovil() {
        return numeroMovil; //devuelve el número de teléfono
    }

    public void setNumeroMovil(String numeroMovil) {
        this.numeroMovil = numeroMovil; //establece el número de teléfono
    }

    public String getPlataforma() {
        return plataforma; //devuelve la plataforma
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma; //establece la plataforma
    }

    public String getSaldo() {
        return saldo; // devuelve el saldo
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo; // establece el saldo
    }

    public String getDatosRestantes() {
        return datosRestantes; // devuelve los datos restantes
    }

    public void setDatosRestantes(String datosRestantes) {
        this.datosRestantes = datosRestantes; // establece los datos restantes
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
