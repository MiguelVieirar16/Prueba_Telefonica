package com.example.prueba_telefonica.model;

import android.content.Context;

import com.example.prueba_telefonica.R;

import java.util.regex.Pattern;

public class ValidarCampos {
    private final Context mContext; // contexto de la aplicación

    private static final Pattern CI_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +            // sin espacios en blanco
                    ".{7,12}" +              // 7-12 caracteres
                    "$");
    private static final Pattern TELF_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +            // sin espacios en blanco
                    ".{7}" +                 // 7 caracteres
                    "$");

    private static final Pattern CODE_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +            // sin espacios en blanco
                    ".{6}" +                 // 6 caracteres
                    "$");

    public ValidarCampos(Context mContext) {
        this.mContext = mContext; //inicializa el contexto
    }

    //valida que tenga la longitud de un número telefónico
    public String ValidarTelefono(String telefono) {
        if (telefono.isEmpty()) {
            return mContext.getResources().getString(R.string.empty); //retorna mensaje de campo vacío
        } else if (!TELF_PATTERN.matcher(telefono).matches()) {
            return mContext.getResources().getString(R.string.telefono_invalido); //retorna mensaje de teléfono inválido
        } else {
            return mContext.getResources().getString(R.string.trues); //retorna mensaje de validación exitosa
        }
    }

    // valida que la cédula tenga la longitud adecuada
    public String ValidarID_CI(String cedula) {
        if (cedula.isEmpty()) {
            return mContext.getResources().getString(R.string.empty); //retorna mensaje de campo vacío
        } else if (!CI_PATTERN.matcher(cedula).matches()) {
            return mContext.getResources().getString(R.string.ci_invalida); //retorna mensaje de cédula inválida
        } else {
            return mContext.getResources().getString(R.string.trues); //retorna mensaje de validación exitosa
        }
    }

    // valida que el código tenga la longitud adecuada
    public String Validar_Code(String code) {
        if (code.isEmpty()) {
            return mContext.getResources().getString(R.string.empty); //retorna mensaje de campo vacío
        } else if (!CODE_PATTERN.matcher(code).matches()) {
            return mContext.getResources().getString(R.string.code_invalido); //retorna mensaje de código inválido
        } else {
            return mContext.getResources().getString(R.string.trues); //retorna mensaje de validación exitosa
        }
    }
}

