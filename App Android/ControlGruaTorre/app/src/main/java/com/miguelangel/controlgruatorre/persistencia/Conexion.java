package com.miguelangel.controlgruatorre.persistencia;

import java.io.Serializable;

/**
 * Created by MiguelAngel on 16/05/2017.
 */

public class Conexion implements Serializable{

    private String nombre;
    private String direccion;
    private String usuario;
    private String clave;

    public Conexion(String direccion, String usuario, String clave, String nombre) {
        this.direccion = direccion;
        this.usuario = usuario;
        this.clave = clave;
        this.nombre = nombre;
    }

    public Conexion() {}


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }
}
