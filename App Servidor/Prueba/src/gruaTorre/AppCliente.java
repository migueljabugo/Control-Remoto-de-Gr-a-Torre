package gruaTorre;

import com.miguelangel.controlgruatorre.cliente.DatosConexion;

public class AppCliente {

	public static void main(String[] args) {
		DatosConexion datos= new DatosConexion("usuario", "usuario");
		Cliente cliente = new Cliente("localhost", 6000, datos);
		
	
		cliente.conectar();
		//cliente.movimiento(TipoOrden.ADELANTE);
		//cliente.movimiento(TipoOrden.ATRAS);
		//cliente.movimiento(TipoOrden.PARAR_MOVIMIENTO);
		
	}

}
