package com.example.prueba_telefonica.model;

public class UserValidationRequest {
    private String documento_identidad; //documento de identidad del usuario
    private String numero_movil; //número de teléfono del usuario

    public UserValidationRequest(String documento_identidad, String numero_movil) {
        this.documento_identidad = documento_identidad; //inicializa el documento de identidad
        this.numero_movil = numero_movil; //inicializa el número de móvil
    }

    public String getDocumento_identidad() {
        return documento_identidad; // devuelve el documento de identidad
    }

    public void setDocumento_identidad(String documento_identidad) {
        this.documento_identidad = documento_identidad; // establece el documento de identidad
    }

    public String getNumero_movil() {
        return numero_movil; // devuelve el número de móvil
    }

    public void setNumero_movil(String numero_movil) {
        this.numero_movil = numero_movil; // establece el número de móvil
    }
}
