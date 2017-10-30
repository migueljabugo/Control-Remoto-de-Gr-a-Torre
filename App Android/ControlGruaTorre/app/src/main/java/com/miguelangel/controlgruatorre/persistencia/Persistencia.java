package com.miguelangel.controlgruatorre.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MiguelAngel on 16/05/2017.
 */

public class Persistencia {
    SQLiteDatabase db;

    public Persistencia(Context context){
        ConexionSQLiteHelper dbh = new ConexionSQLiteHelper(context, "datosConexion", null, 6);
        db=dbh.getWritableDatabase();
    }

    public List<Conexion> getDatosConexion(){
        List<Conexion> lista = new ArrayList<Conexion>();
        Cursor c= db.rawQuery("SELECT * FROM conexiones;", null);
        if(c.moveToFirst()){
            do{
                lista.add(cursorToConexion(c));
            }while(c.moveToNext());
        }
        return lista;
    }

    public Conexion cursorToConexion(Cursor cursor){
        Conexion conexion = new Conexion();
        conexion.setDireccion(cursor.getString(0));
        conexion.setUsuario(cursor.getString(1));
        conexion.setClave(cursor.getString(2));
        conexion.setNombre(cursor.getString(3));
        Log.i("PERSISTENCIA", "Leyendo conexion: " + conexion.toString());
        return conexion;
    }
    /*public Integer getId(){
        List<Conexion> lista= getDatosConexion();
        Conexion = lista.get(lista.size());
    }*/

    public void addConexion(Conexion conexion){
        db.execSQL("INSERT INTO conexiones " +
                "VALUES('"+conexion.getDireccion()+"', " +
                        "'"+conexion.getUsuario()+"', " +
                        "'"+conexion.getClave()+"', " +
                        "'"+conexion.getNombre()+"');");
        Log.i("PERSISTENCIA", "Guardando conexion: "+conexion.toString());
    }

    public void removeConexion(Conexion conexion){
        db.execSQL("DELETE FROM conexiones WHERE " +
                "direccion='"+conexion.getDireccion()+"'" +
                "and usuario='"+conexion.getUsuario()+"'" +
                "and clave='"+conexion.getClave()+"'" +
                "and nombre='"+conexion.getNombre()+"'");
    }
}
