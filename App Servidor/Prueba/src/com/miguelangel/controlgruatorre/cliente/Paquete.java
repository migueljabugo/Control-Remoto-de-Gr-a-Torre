package com.miguelangel.controlgruatorre.cliente;

import java.io.Serializable;

public class Paquete implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private DatosConexion datosConexion;
	private TipoOrden orden;
	
	public Paquete(DatosConexion datos, TipoOrden tipo){
		this.orden = tipo;
		this.datosConexion = datos;
	}

	public DatosConexion getDatosConexion() {
		return datosConexion;
	}

	public TipoOrden getTipo() {
		return orden;
	}
	
	

}
