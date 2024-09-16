package com.example.prueba_telefonica;

import android.content.Intent;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.prueba_telefonica.model.ValidarCampos;
import com.example.prueba_telefonica.model.VerifyRequest;
import com.example.prueba_telefonica.model.VerifyResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {
    private EditText codeEditText; //campo de texto para el código
    private Button verifyButton; //botón para verificar el código
    private String documentoIdentidad, code, phoneNumber; //variables para almacenar datos del usuario
    private TextInputLayout il_code; //layout para el campo de texto del código

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        codeEditText = findViewById(R.id.codeEditText); //inicializa el campo de texto del código
        verifyButton = findViewById(R.id.verifyButton); //inicializa el botón de verificación
        il_code = findViewById(R.id.il_code); //inicializa el layout del campo de texto del código
        documentoIdentidad = getIntent().getStringExtra("documento_identidad"); //obtiene el documento de identidad del intent
        phoneNumber = getIntent().getStringExtra("numero_movil"); //obtiene el número de teléfono del intent

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidarCodeInput()) {
                    verifyCode(); //verifica el código si la validación es exitosa
                }
            }
        });
    }
    //declaramos el snackbar que nos ayudará a encontrar errores
    public void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT); //declaración del snackbar
        View snackbarView = snackbar.getView(); //vista
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.error)); //color
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text); //texto
        textView.setTextColor(ContextCompat.getColor(this, R.color.snackbar_text)); //color del texto
        snackbar.show();
    }
    //declaramos el snackbar que nos ayudará a encontrar pasos acertados
    public void showSnackbarGood(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT); //declaración del snackbar
        View snackbarView = snackbar.getView(); //vista
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.good)); //color
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text); //texto
        textView.setTextColor(ContextCompat.getColor(this, R.color.snackbar_text)); //color del texto
        snackbar.show();
    }

    private void ObtenerCode() {
        code = codeEditText.getText().toString(); //obtiene el código del campo de texto
    }

    private boolean ValidarCodeInput() {
        int var = 0;
        ObtenerCode(); //obtiene el código
        ValidarCampos validar = new ValidarCampos(this); //crea una instancia de ValidarCampos

        //valida el código
        if (validar.Validar_Code(code).equals(getString(R.string.trues))) {
            il_code.setError(null); //no hay error
            var++;
        } else if (validar.Validar_Code(code).equals(getString(R.string.empty))) {
            il_code.setError(getString(R.string.empty)); //código vacío
        } else if (validar.Validar_Code(code).equals(getString(R.string.code_invalido))) {
            il_code.setError(getString(R.string.code_invalido)); //código inválido
        }

        return var == 1; //devuelve true si la validación es exitosa
    }

    private void verifyCode() {
        VerifyRequest request = new VerifyRequest(documentoIdentidad, code); //crea una solicitud de verificación
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class); //obtiene una instancia de ApiService
        Call<VerifyResponse> call = apiService.verifyCode(request); //llama al método de verificación de código

        call.enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isValid()) {
                        showSnackbarGood(findViewById(android.R.id.content), "Código correcto. Acceso concedido."); //muestra un mensaje de éxito
                        //navega a la pantalla principal de la aplicación
                        Intent intent = new Intent(VerificationActivity.this, UserDetailsActivity.class);
                        intent.putExtra("documento_identidad", documentoIdentidad);
                        intent.putExtra("numero_movil", phoneNumber);
                        startActivity(intent);
                        finish(); //opcional: cierra la actividad actual
                    } else {
                        showSnackbar(findViewById(android.R.id.content), "Código incorrecto. Inténtalo de nuevo."); //muestra un mensaje de error
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                showSnackbar(findViewById(android.R.id.content), "Error del servidor"); //muestra un mensaje de error del servidor
            }
        });
    }
}