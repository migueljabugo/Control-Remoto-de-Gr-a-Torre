package com.miguelangel.controlgruatorre.cliente;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.Toast;

import java.io.*;
import java.net.*;

public class Cliente {
	private String host;
	private int puerto;
	private DatosConexion datos;

    public Cliente(String host, int puerto, DatosConexion datosConexion){
		this.host=host;
		this.puerto=puerto;
		this.datos=datosConexion;
		
	}

	public void conectar() /*throws NetworkOnMainThreadException*/{
        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket cliente = new Socket(host, puerto);
                    Log.i("CLIENTE", "Conectado");
                    Paquete paquete = new Paquete(datos, TipoOrden.CONECTADO);
                    ObjectOutputStream salida= new ObjectOutputStream(cliente.getOutputStream());
                    salida.writeObject(paquete);
                    ObjectInputStream entrada  =new ObjectInputStream(cliente.getInputStream());
                    paquete =(Paquete) entrada.readObject();
                    if(paquete.getTipo() == TipoOrden.CONECTADO){

                    }else{
                        entrada.close();
                        salida.close();
                        cliente.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

	public void movimiento(final TipoOrden orden) /*throws NetworkOnMainThreadException*/{

        new Thread(new Runnable() {
            public void run() {
                try {
                    ObjectOutputStream salida;
                    ObjectInputStream entrada;
                    Paquete paquete;
                    Socket cliente = new Socket(host, puerto);
                    Log.i("CLIENTE", cliente.toString());

                    paquete = new Paquete(datos, orden);
                    salida = new ObjectOutputStream(cliente.getOutputStream());
                    salida.writeObject(paquete);
                    entrada = new ObjectInputStream(cliente.getInputStream());
                    paquete = (Paquete) entrada.readObject();
                    if (paquete.getTipo() == TipoOrden.DESCONECTAR) {
                        System.err.println("Datos de conexion incorrectos");
                    }

                    entrada.close();
                    salida.close();
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();


	}

	@Override
	public String toString() {
		return "Cliente{" +
				"host='" + host + '\'' +
				", puerto=" + puerto +
				", datos=" + datos +
				'}';
	}
}
