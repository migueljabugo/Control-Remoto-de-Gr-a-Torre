package gruaTorre;

public class AppServidor {

	public static void main(String[] args) {
		try{
			Servidor server = new Servidor();
			server.ejecucion();
		} catch (ClassNotFoundException e) {
		}

	}

}
