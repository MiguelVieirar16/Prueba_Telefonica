package com.example.prueba_telefonica.model;

public class VerifyRequest {
    private String documento_identidad; //documento de identidad del usuario
    private String code; //código de verificación

    public VerifyRequest(String documento_identidad, String code) {
        this.documento_identidad = documento_identidad; //inicializa el documento de identidad
        this.code = code; //inicializa el código de verificación
    }

    public String getDocumento_identidad() {
        return documento_identidad;
    }

    public void setDocumento_identidad(String documento_identidad) {
        this.documento_identidad = documento_identidad;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code; // establece el código de verificación
    }
}
