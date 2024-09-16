package com.example.prueba_telefonica;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.prueba_telefonica.model.UserValidationRequest;
import com.example.prueba_telefonica.model.ValidarCampos;
import com.example.prueba_telefonica.model.ValidationResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerIdentidad, spinnerTelf; // spinners para seleccionar identidad y teléfono
    private EditText etNumeroMovil, etDocumentoIdentidad; // campos de texto para número de teléfono y documento de identidad
    private TextInputLayout il_DocumentoIdentidad, il_NumeroMovil; // layouts para los campos de texto
    private Button btnLogin; // botón de login
    private ApiService apiService; // servicio de API
    private ValidarCampos validarCampos; // instancia de ValidarCampos
    private String codigo, ident, cedula, telefono; // variables para almacenar datos del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerIdentidad = findViewById(R.id.spinner_identidad); // inicializa el spinner de identidad
        spinnerTelf = findViewById(R.id.spinner_telf); // inicializa el spinner de teléfono
        etNumeroMovil = findViewById(R.id.etNumeroMovil); // inicializa el campo de texto de número de móvil
        etDocumentoIdentidad = findViewById(R.id.etDocumentoIdentidad); // inicializa el campo de texto de documento de identidad
        il_DocumentoIdentidad = findViewById(R.id.il_DocumentoIdentidad); // inicializa el layout del documento de identidad
        il_NumeroMovil = findViewById(R.id.il_NumeroMovil); // inicializa el layout del número de móvil
        btnLogin = findViewById(R.id.btnLogin); // inicializa el botón de login
        apiService = ApiClient.getApiClient().create(ApiService.class); // crea una instancia del servicio de API
        validarCampos = new ValidarCampos(this); //crea una instancia de ValidarCampos

        //configurar los spinners
        ArrayAdapter<CharSequence> adapterIdentidad = ArrayAdapter.createFromResource(this,
                R.array.identidad_array, android.R.layout.simple_spinner_item);
        adapterIdentidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdentidad.setAdapter(adapterIdentidad);

        ArrayAdapter<CharSequence> adapterTelf = ArrayAdapter.createFromResource(this,
                R.array.telf_array, android.R.layout.simple_spinner_item);
        adapterTelf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTelf.setAdapter(adapterTelf);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidarCamposInput()) {
                    String documentoIdentidad = ident.trim().concat(cedula);
                    String numeroMovil = codigo.trim().concat(telefono);
                    if (documentoIdentidad.isEmpty() || numeroMovil.isEmpty()) {
                        showSnackbar(v, "Por favor, completa todos los campos");
                    } else {
                        validateUser(documentoIdentidad, numeroMovil);
                    }
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

    private void ObtenerDatos() {
        cedula = il_DocumentoIdentidad.getEditText().getText().toString().trim(); //obtiene la cédula del campo de texto
        telefono = il_NumeroMovil.getEditText().getText().toString(); //obtiene el teléfono del campo de texto

        codigo = spinnerTelf.getSelectedItem().toString(); //obtiene el código del spinner de teléfono
        ident = spinnerIdentidad.getSelectedItem().toString(); //obtiene la identidad del spinner de identidad
    }

    private boolean ValidarCamposInput() {
        int var = 0;
        ObtenerDatos(); //obtiene los datos ingresados
        ValidarCampos validar = new ValidarCampos(this); //crea una instancia de ValidarCampos

        // validar cédula o RIF
        if (validar.ValidarID_CI(cedula).equals(getString(R.string.trues))) {
            il_DocumentoIdentidad.setError(null); //no hay error
            var++;
        } else if (validar.ValidarID_CI(cedula).equals(getString(R.string.empty))) {
            il_DocumentoIdentidad.setError(getString(R.string.empty)); // cédula vacía
        } else if (validar.ValidarID_CI(cedula).equals(getString(R.string.ci_invalida))) {
            il_DocumentoIdentidad.setError(getString(R.string.ci_invalida)); // cédula inválida
        }

        //validar teléfono
        if (validar.ValidarTelefono(telefono).equals(getString(R.string.trues))) {
            il_NumeroMovil.setError(null); // no hay error
            var++;
        } else if (validar.ValidarTelefono(telefono).equals(getString(R.string.empty))) {
            il_NumeroMovil.setError(getString(R.string.empty)); // teléfono vacío
        } else if (validar.ValidarTelefono(telefono).equals(getString(R.string.telefono_invalido))) {
            il_NumeroMovil.setError(getString(R.string.telefono_invalido)); // teléfono inválido
        }

        return var == 2; //devuelve true si ambos campos son válidos
    }

    private void validateUser(String documentoIdentidad, String numeroMovil) {
        UserValidationRequest request = new UserValidationRequest(documentoIdentidad, numeroMovil); //crea una solicitud de validación de usuario
        Call<ValidationResponse> call = apiService.validateUser(request); //llama al método de validación de usuario
        call.enqueue(new Callback<ValidationResponse>() {
            @Override
            public void onResponse(Call<ValidationResponse> call, Response<ValidationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isValid()) {
                        showSnackbarGood(findViewById(android.R.id.content), "Código de verificación enviado correctamente "); // muestra un mensaje de éxito
                        Intent intent = new Intent(MainActivity.this, VerificationActivity.class); //crea un intent para la actividad de verificación
                        intent.putExtra("documento_identidad", documentoIdentidad); //pasa el documento de identidad al intent
                        intent.putExtra("numero_movil", numeroMovil); //pasa el número de teléfono al intent
                        startActivity(intent); //inicia la actividad de verificación
                    } else {
                        showSnackbar(findViewById(android.R.id.content), "Documento de identidad y número de móvil no coinciden"); // muestra un mensaje de error
                    }
                } else {
                    showSnackbar(findViewById(android.R.id.content), "Error: " + response.message() + " - " + response.code()); //muestra un mensaje de error con el código y mensaje de respuesta
                    Log.e("API_ERROR", "Error: " + response.message() + " - " + response.code()); //registra el error en el log
                }
            }

            @Override
            public void onFailure(Call<ValidationResponse> call, Throwable t) {
                showSnackbar(findViewById(android.R.id.content), "Error del servidor: " + t.getMessage()); //muestra un mensaje de error del servidor
                Log.e("API_ERROR", "Error del servidor: " + t.getMessage()); //registra el error del servidor en el log
            }
        });
    }
}
