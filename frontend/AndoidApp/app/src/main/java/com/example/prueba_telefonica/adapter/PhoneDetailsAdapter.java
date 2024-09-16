package com.example.prueba_telefonica.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prueba_telefonica.model.PhoneDetails;
import com.example.prueba_telefonica.R;

import java.util.List;

public class PhoneDetailsAdapter extends ArrayAdapter<PhoneDetails> {
    public PhoneDetailsAdapter(Context context, List<PhoneDetails> phoneDetailsList) {
        super(context, 0, phoneDetailsList); //inicializa el adaptador con la lista de detalles de teléfonos
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //infla el layout para cada ítem del Spinner
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        //obtiene el detalle del teléfono en la posición actual
        PhoneDetails phoneDetails = getItem(position);
        //inicializa los elementos de la vista
        TextView tvPhoneNumber = convertView.findViewById(R.id.tvPhoneNumber);
        TextView tvPlatform = convertView.findViewById(R.id.tvPlatform);

        //establece los valores de los elementos de la vista
        tvPhoneNumber.setText(phoneDetails.getNumeroMovil());
        tvPlatform.setText(phoneDetails.getPlataforma());

        return convertView; //devuelve la vista configurada
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //infla el layout para cada ítem del Spinner desplegable
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        //obtiene el detalle del teléfono en la posición actual
        PhoneDetails phoneDetails = getItem(position);
        //inicializa los elementos de la vista
        TextView tvPhoneNumber = convertView.findViewById(R.id.tvPhoneNumber);
        TextView tvPlatform = convertView.findViewById(R.id.tvPlatform);

        //establece los valores de los elementos de la vista
        tvPhoneNumber.setText(phoneDetails.getNumeroMovil());
        tvPlatform.setText(phoneDetails.getPlataforma());

        return convertView; //devuelve la vista configurada
    }
}

