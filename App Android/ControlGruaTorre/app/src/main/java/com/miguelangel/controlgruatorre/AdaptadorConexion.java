package com.miguelangel.controlgruatorre;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguelangel.controlgruatorre.persistencia.Conexion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MiguelAngel on 17/05/2017.
 */

public class AdaptadorConexion extends RecyclerView.Adapter<AdaptadorConexion.ConexionViewHolder> {

    private List<Conexion> datos;

    public AdaptadorConexion(List<Conexion> datos){
        this.datos=datos;
    }






    @Override
    public AdaptadorConexion.ConexionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.elemento_lista_conexiones, viewGroup, false);

        ConexionViewHolder tvh = new ConexionViewHolder(itemView);
        return tvh;
    }

    @Override
    public void onBindViewHolder(AdaptadorConexion.ConexionViewHolder holder, int position) {
        Conexion item = datos.get(position);

        holder.bindConexion(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void addConexion(Conexion conexion){
        datos.add(0, conexion);
        this.notifyItemInserted(0);
    }






    class ConexionViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        private TextView txtNombre, direccionIp;
        private Conexion conexion;

        public ConexionViewHolder(View itemView){
            super(itemView);

            txtNombre = (TextView)itemView.findViewById(R.id.txtNombreLista);
            direccionIp = (TextView)itemView.findViewById(R.id.direccionIpLista);
        }

        public void bindConexion(Conexion conexion){
            this.conexion=conexion;
            txtNombre.setText(conexion.getNombre());
            direccionIp.setText(conexion.getDireccion());
        }


    }
}
