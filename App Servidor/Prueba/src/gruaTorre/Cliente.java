package gruaTorre;

import java.io.*;
import java.net.*;

import com.miguelangel.controlgruatorre.cliente.DatosConexion;
import com.miguelangel.controlgruatorre.cliente.Paquete;
import com.miguelangel.controlgruatorre.cliente.TipoOrden;

public class Cliente {
	private String host;
	private int puerto;
	private DatosConexion datos;
	
	public Cliente(String host, int puerto, DatosConexion datosConexion){
		this.host=host;
		this.puerto=puerto;
		this.datos=datosConexion;
		
	}
	
	public void conectar(){
		try {		
			Socket cliente = new Socket(this.host, this.puerto);
			
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
	
	public void movimiento(TipoOrden orden){
		try {
			ObjectOutputStream salida;
			ObjectInputStream entrada;
			Paquete paquete;
			Socket cliente = new Socket(host,puerto);
			
			paquete = new Paquete(datos, orden);
			salida= new ObjectOutputStream(cliente.getOutputStream());
			salida.writeObject(paquete);
			entrada  =new ObjectInputStream(cliente.getInputStream());
			paquete =(Paquete) entrada.readObject();
			if(paquete.getTipo() == TipoOrden.DESCONECTAR){
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
	
	/*
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String host = "localhost";
		int puerto = 6000;
		DatosConexion datos;
		ObjectOutputStream salida;
		ObjectInputStream entrada;
		Socket cliente;
		
		//////////////////
		try{
			cliente = new Socket(host,puerto);
			datos=new DatosConexion("usuario", "usario");
			Paquete paquete = new Paquete(datos, TipoOrden.CONECTADO);
			System.out.println("CLIENTE - enviando datos");
			salida= new ObjectOutputStream(cliente.getOutputStream());
			salida.writeObject(paquete);
			
			entrada  =new ObjectInputStream(cliente.getInputStream());
			paquete =(Paquete) entrada.readObject();
			if(paquete.getTipo() == TipoOrden.CONECTADO){
				//recibe conectado
				System.out.println("CLIENTE - conectado");
				//////////
				cliente = new Socket(host,puerto);
				
				System.out.println("CLIENTE - Adelante");
				paquete = new Paquete(datos,TipoOrden.ADELANTE);
				salida= new ObjectOutputStream(cliente.getOutputStream());
				salida.writeObject(paquete);
				entrada  =new ObjectInputStream(cliente.getInputStream());
				paquete =(Paquete) entrada.readObject();
				if(paquete.getTipo() == TipoOrden.DESCONECTAR){
					System.err.println("Datos de conexion incorrectos");
					
				}
				
				
				
				
				entrada.close();
				salida.close();
				cliente.close();
			}else{
				entrada.close();
				salida.close();
				cliente.close();
			}
		
		}catch (ConnectException e){
			System.out.println("Error al conectar al servidor");
		}
	}*/
}
