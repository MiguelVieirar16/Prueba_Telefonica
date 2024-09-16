package com.example.prueba_telefonica.model;

import java.util.List;

public class UserDetailsResponse {
    private String nombre; //nombre del usuario
    private List<PhoneDetails> telefonos; //lista de detalles de los teléfonos

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PhoneDetails> getTelefonos() {
        return telefonos; //devuelve la lista de los teléfonos
    }

    public void setTelefonos(List<PhoneDetails> telefonos) {
        this.telefonos = telefonos;
    }
}


