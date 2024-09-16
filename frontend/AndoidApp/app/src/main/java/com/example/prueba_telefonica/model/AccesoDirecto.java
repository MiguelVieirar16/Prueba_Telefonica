package com.example.prueba_telefonica.model;

public class AccesoDirecto {
    private String titulo;
    private String subtitulo;
    private int icono;
    private String url;

    public AccesoDirecto(String titulo, String subtitulo, int icono, String url) {
        this.titulo = titulo; //inicializa el título
        this.subtitulo = subtitulo; //inicializa el subtítulo
        this.icono = icono; //inicializa el ícono
        this.url = url; //inicializa la URL
    }

    public String getTitulo() {
        return titulo; //devuelve el título
    }

    public String getSubtitulo() {
        return subtitulo; //devuelve el subtítulo
    }

    public int getIcono() {
        return icono; //devuelve el ícono
    }

    public String getUrl() {
        return url; //devuelve la URL
    }
}

