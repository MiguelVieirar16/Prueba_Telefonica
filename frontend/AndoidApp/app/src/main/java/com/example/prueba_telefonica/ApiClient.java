package com.example.prueba_telefonica;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.105:3000/api/"; //URL base de la API
    private static Retrofit retrofit; //instancia de Retrofit

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //establece la URL base
                    .addConverterFactory(GsonConverterFactory.create()) //añade el convertidor de Gson
                    .build(); //construye la instancia de Retrofit
        }
        return retrofit; //devuelve la instancia de Retrofit
    }
}