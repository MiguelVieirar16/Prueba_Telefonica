package com.example.prueba_telefonica.model;

public class ValidationResponse {
    private boolean valid; //indica si la validación fue exitosa

    public boolean isValid() {
        return valid; //devuelve el estado de validación
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}