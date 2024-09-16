package com.example.prueba_telefonica;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.prueba_telefonica.adapter.PhoneDetailsAdapter;
import com.example.prueba_telefonica.model.AccesoDirecto;
import com.example.prueba_telefonica.model.PhoneDetails;
import com.example.prueba_telefonica.model.UserDetailsResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {
    private ApiService apiService; //servicio de API para realizar llamadas a la API
    private TextView tvGreeting, tvSaldo, tvDatos, tvPlan; //elementos de texto para mostrar información del usuario
    private Spinner spinnerPhones; //spinner para seleccionar diferentes teléfonos del usuario
    private GridLayout gridAccesosDirectos; //grid layout para mostrar accesos directos
    private String documentoIdentidad, phoneNumber; //variables para almacenar el documento de identidad y el número de teléfono

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        apiService = ApiClient.getApiClient().create(ApiService.class); //inicializa el servicio de API
        tvGreeting = findViewById(R.id.tvGreeting); //inicializa el TextView para el saludo
        spinnerPhones = findViewById(R.id.spinnerPhones); //inicializa el Spinner para los teléfonos
        tvSaldo = findViewById(R.id.tvSaldo); //inicializa el TextView para el saldo
        tvDatos = findViewById(R.id.tvDatos); //inicializa el TextView para los datos
        tvPlan = findViewById(R.id.tvPlan); //inicializa el TextView para el plan
        gridAccesosDirectos = findViewById(R.id.gridAccesosDirectos); //inicializa el GridLayout para los accesos directos

        documentoIdentidad = getIntent().getStringExtra("documento_identidad"); //obtiene el documento de identidad del intent
        phoneNumber = getIntent().getStringExtra("numero_movil"); //obtiene el número de teléfono del intent
        getUserDetails(documentoIdentidad); //llama al método para obtener los detalles del usuario

        //establece un listener para el Spinner de teléfonos
        spinnerPhones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PhoneDetails selectedPhone = (PhoneDetails) parent.getItemAtPosition(position); //obtiene el teléfono seleccionado
                updatePhoneDetails(selectedPhone); //actualiza los detalles del teléfono seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        //crea una lista de accesos directos
        List<AccesoDirecto> accesosDirectos = new ArrayList<>();
        accesosDirectos.add(new AccesoDirecto("Planes", "Opciones para ti", R.drawable.icon_planes, "https://www.movistar.com.ve/Particulares/planes-movistar-plus.html"));
        accesosDirectos.add(new AccesoDirecto("Contáctanos", "Canales de gestión", R.drawable.icon_contactanos, "https://www.movistar.com.ve/Particulares/Autogestion.html"));
        accesosDirectos.add(new AccesoDirecto("Tienda", "Compra en línea", R.drawable.icon_tienda, "https://tienda.movistar.com.ve/linea-nueva"));
        accesosDirectos.add(new AccesoDirecto("Club Movistar", "Beneficios", R.drawable.icon_club_movistar, "https://www.movistar.com.ve/Particulares/Antesala_club_movistar.html"));

        //añade cada acceso directo al GridLayout
        for (AccesoDirecto accesoDirecto : accesosDirectos) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_acceso_directo, gridAccesosDirectos, false);
            TextView tvTitulo = itemView.findViewById(R.id.tvTitulo); //inicializa el TextView para el título
            TextView tvSubtitulo = itemView.findViewById(R.id.tvSubtitulo); //inicializa el TextView para el subtítulo
            ImageView ivIcono = itemView.findViewById(R.id.ivIcono); //inicializa el ImageView para el ícono

            tvTitulo.setText(accesoDirecto.getTitulo()); //establece el título del acceso directo
            tvSubtitulo.setText(accesoDirecto.getSubtitulo()); //establece el subtítulo del acceso directo
            ivIcono.setImageResource(accesoDirecto.getIcono()); //establece el ícono del acceso directo

            //establece un listener para abrir el navegador al hacer clic en el acceso directo
            itemView.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(accesoDirecto.getUrl()));
                startActivity(browserIntent);
            });

            //establece los parámetros de layout para el GridLayout
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.setMargins(16, 16, 16, 16); //margen entre los contenedores
            itemView.setLayoutParams(params);

            gridAccesosDirectos.addView(itemView); //añade el itemView al GridLayout
        }
    }

    private void getUserDetails(String documentoIdentidad) {
        //realiza una llamada a la API para obtener los detalles del usuario
        Call<UserDetailsResponse> call = apiService.getUserDetails(documentoIdentidad);
        call.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                //verifica si la respuesta es exitosa y no es nula
                if (response.isSuccessful() && response.body() != null) {
                    UserDetailsResponse userDetails = response.body(); //obtiene los detalles del usuario
                    tvGreeting.setText("¡Hola, " + userDetails.getNombre() + "!"); //establece el saludo con el nombre del usuario
                    List<PhoneDetails> telefonos = userDetails.getTelefonos(); //obtiene la lista de teléfonos del usuario
                    PhoneDetailsAdapter adapter = new PhoneDetailsAdapter(UserDetailsActivity.this, telefonos); //crea un adaptador para el Spinner
                    spinnerPhones.setAdapter(adapter); //establece el adaptador en el Spinner

                    //selecciona el teléfono inicial en el Spinner
                    if (phoneNumber != null) {
                        for (int i = 0; i < telefonos.size(); i++) {
                            if (telefonos.get(i).getNumeroMovil().equals(phoneNumber)) {
                                spinnerPhones.setSelection(i); //selecciona el teléfono correspondiente
                                updatePhoneDetails(telefonos.get(i)); //actualiza los detalles del teléfono seleccionado
                                break;
                            }
                        }
                    } else if (!telefonos.isEmpty()) {
                        updatePhoneDetails(telefonos.get(0)); //actualiza los detalles del primer teléfono en la lista si no hay un teléfono específico seleccionado
                    }
                } else {
                    Log.e("UserDetailsActivity", "Error: " + response.message()); //registra un error en el log
                    showSnackbar(findViewById(android.R.id.content), "Error: " + response.message()); //muestra un mensaje de error
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                Log.e("UserDetailsActivity", "Error: " + t.getMessage()); //registra un error en el log
                showSnackbar(findViewById(android.R.id.content), "Error: " + t.getMessage()); //muestra un mensaje de error
            }
        });
    }

    private void updatePhoneDetails(PhoneDetails phone) {
        tvSaldo.setText(phone.getSaldo()); //actualiza el saldo
        tvDatos.setText(phone.getDatosRestantes()); //actualiza los datos restantes
        tvPlan.setText(phone.getPlan()); //actualiza el plan
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
}





