package com.example.prueba_telefonica.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prueba_telefonica.R;
import com.example.prueba_telefonica.model.AccesoDirecto;

import java.util.List;

public class AccesoDirectoAdapter extends RecyclerView.Adapter<AccesoDirectoAdapter.AccesoDirectoViewHolder> {
    private List<AccesoDirecto> accesosDirectos; //lista de accesos directos
    private Context context; //contexto de la aplicación

    public AccesoDirectoAdapter(Context context, List<AccesoDirecto> accesosDirectos) {
        this.context = context; //inicializa el contexto
        this.accesosDirectos = accesosDirectos; //inicializa la lista de accesos directos
    }

    @NonNull
    @Override
    public AccesoDirectoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infla el layout para cada ítem del RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_acceso_directo, parent, false);
        return new AccesoDirectoViewHolder(view); //crea un nuevo ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull AccesoDirectoViewHolder holder, int position) {
        //obtiene el acceso directo en la posición actual
        AccesoDirecto accesoDirecto = accesosDirectos.get(position);
        //establece los valores de los elementos de la vista
        holder.tvTitulo.setText(accesoDirecto.getTitulo());
        holder.tvSubtitulo.setText(accesoDirecto.getSubtitulo());
        holder.ivIcono.setImageResource(accesoDirecto.getIcono());
    }

    @Override
    public int getItemCount() {
        return accesosDirectos.size(); //devuelve el tamaño de la lista
    }

    public static class AccesoDirectoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvSubtitulo; //elementos de texto
        ImageView ivIcono; //elemento de imagen

        public AccesoDirectoViewHolder(@NonNull View itemView) {
            super(itemView);
            //inicializa los elementos de la vista
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvSubtitulo = itemView.findViewById(R.id.tvSubtitulo);
            ivIcono = itemView.findViewById(R.id.ivIcono);
        }
    }
}
