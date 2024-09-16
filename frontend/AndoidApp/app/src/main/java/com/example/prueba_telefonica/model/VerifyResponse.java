package com.example.prueba_telefonica.model;

public class VerifyResponse {
    private boolean valid; //indica si la verificación fue exitosa
    private String message; //mensaje de verificación

    public boolean isValid() {
        return valid; //devuelve el estado de verificación
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
