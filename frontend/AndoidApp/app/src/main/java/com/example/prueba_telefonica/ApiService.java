package com.example.prueba_telefonica;



import com.example.prueba_telefonica.model.UserDetailsResponse;
import com.example.prueba_telefonica.model.UserValidationRequest;
import com.example.prueba_telefonica.model.ValidationResponse;
import com.example.prueba_telefonica.model.VerifyRequest;
import com.example.prueba_telefonica.model.VerifyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{documento_identidad}/details")
    Call<UserDetailsResponse> getUserDetails(@Path("documento_identidad") String documentoIdentidad); //obtiene los detalles del usuario

    @POST("users/validate")
    Call<ValidationResponse> validateUser(@Body UserValidationRequest request); //valida al usuario

    @POST("users/verify-code")
    Call<VerifyResponse> verifyCode(@Body VerifyRequest request); //verifica el código
}

