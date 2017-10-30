package com.miguelangel.controlgruatorre.cliente;

import java.io.Serializable;

public enum TipoOrden implements Serializable{
	ROTAR_DERECHA,
	ROTAR_IZQUIERDA,
	SUBIR,
	BAJAR,
	ADELANTE,
	ATRAS,
	PARAR_MOVIMIENTO,
	DESCONECTAR,
	CONECTADO;
	
	
	
}
