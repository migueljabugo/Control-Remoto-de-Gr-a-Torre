package gruaTorre;

import Ventana.Ventana;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.miguelangel.controlgruatorre.cliente.DatosConexion;
import com.miguelangel.controlgruatorre.cliente.Paquete;
import com.miguelangel.controlgruatorre.cliente.TipoOrden;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import mygame.ControlCarroAtras;
import mygame.ControlCarroDelante;
import mygame.ControlGanchoBajar;
import mygame.ControlGanchoSubir;
import mygame.ControlRotar;
import mygame.Grua;

public class Servidor {
	private ServerSocket servidor;
	private Socket cliente;
	private List<DatosConexion> lista;
	private ObjectOutputStream salida;
	private ObjectInputStream entrada;
	private boolean moviendo;
        private Grua grua;
	
	/**
	 * Constructor de pruebas
         * @param grua
	 */
	public Servidor(Grua grua){
            this.grua = grua;
		try {
			System.out.println("Iniciando servidor");
			this.servidor = new ServerSocket(6000);
			//this.lista=usuariosPrueba();
			this.lista=leerUsuarios();
			moviendo=false;
			ejecucion();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
        
        public Servidor(){
		try {
			System.out.println("Iniciando servidor");
			this.servidor = new ServerSocket(6000);
			this.lista=usuariosPrueba();
			//this.lista=cargarUsuarios();
			moviendo=false;
			ejecucion();
			
			
		} catch (IOException e) {
                    System.out.println("Error al iniciar el servidor: "+e.toString());
		} catch (ClassNotFoundException e) {
                    System.out.println("Error al iniciar el servidor: "+e.toString());
		}
	}
        
	public List<DatosConexion> leerUsuarios(){
        persistencia.GruaP grua=null;
        try {
            JAXBContext context = JAXBContext.newInstance(persistencia.GruaP.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            grua = (persistencia.GruaP)unmarshaller.unmarshal(new File("usuarios.xml"));
            
        } catch (JAXBException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grua.getUsuarios();
    }
	

	/*public Servidor(int puerto, List<DatosConexion> datosConexion){
		try {
			this.servidor = new ServerSocket(puerto);
			this.lista=datosConexion;
			moviendo=false;
			ejecucion();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * En este metodo es donde se desarrolla toda la ejecucion de la aplicacion.
         * @throws java.lang.ClassNotFoundException
	 */
	public void ejecucion() throws java.lang.ClassNotFoundException{
		System.out.println("Ejecutando Servidor");
		while(true){
			try {
				while(true){
					cliente = servidor.accept();
					Paquete paquete = null;
					//System.out.println("\n\nNueva conexion");
					entrada =new ObjectInputStream(cliente.getInputStream());
					//System.out.println("Reciviendo datos de conexion");
					
					paquete = (Paquete) entrada.readObject();
					//System.out.println("Comprobando datos de conexion");
					if(this.comprobarDatosConexion(paquete.getDatosConexion())){
						//System.out.println("Datos de conexion correctos");
						salida=new ObjectOutputStream(cliente.getOutputStream());
						Paquete paqueteSalida = new Paquete(paquete.getDatosConexion(), TipoOrden.CONECTADO);
						salida.writeObject(paqueteSalida);
						
						
					
						
						System.out.println("Recibiendo orden -> "+paquete.getTipo().toString());
						switch(paquete.getTipo()){
						case ROTAR_DERECHA:
							rotarDerecha();
							break;
						case ROTAR_IZQUIERDA:
							rotarIzquierda();
							
							break;
						case SUBIR:
							subir();
							
							break;
						case BAJAR:
							bajar();
							
							break;
						case ADELANTE:
							adelante();
							
							break;
						case ATRAS:
							atras();
							
							break;
						case PARAR_MOVIMIENTO:
							pararMovimiento();
							
							break;
						case DESCONECTAR:
							desconectar();
							
							break;
						case CONECTADO:
							System.out.println("conectado");
							break;
						}
					}else{
						this.desconectar();
						salida=new ObjectOutputStream(cliente.getOutputStream());
						paquete = new Paquete(paquete.getDatosConexion(), TipoOrden.DESCONECTAR);
						salida.writeObject(paquete);
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Usuarios para pruebas
         * @return 
	 */
	public List<DatosConexion> usuariosPrueba(){
		List<DatosConexion> lista = new ArrayList<>();
		lista.add(new DatosConexion("usuario", "usuario"));
		lista.add(new DatosConexion("MiguelAngel", "Gonzalez"));
		return lista;
	}
	
	/**
	 * Comprobar datos de conexion
	 */
	private boolean comprobarDatosConexion(DatosConexion datos){
		boolean r=false;
		for(DatosConexion e : lista){
			if(e.equals(datos)){
				r=true;
			}
		}
		return r;
	}
	
	/**
	 * Cierra la conexion
	 */
	public void desconectar(){
		System.out.println("Desconectando");
                pararMovimiento();
			try {
				//salida.close();
				cliente.close();
			} catch (IOException e) {
                            System.out.println("Error al desconectar"+ e.toString());
			}
	}
	
	/**
	 * Para el movimiento actual de la grua.
	 */
	public void pararMovimiento(){
            
            grua.getPluma().removeControl(ControlRotar.class);
            grua.getGancho().removeControl(ControlGanchoBajar.class);
            grua.getGancho().removeControl(ControlGanchoSubir.class);
            grua.getCarro().removeControl(ControlCarroAtras.class);
            grua.getCarro().removeControl(ControlCarroDelante.class);
            
            
            moviendo = false;
            System.out.println("ORDEN - Parando");
	}
	
        /**
         * Rota la pluma hacia la derecha.
         */
	public void rotarDerecha(){
            if(moviendo){
                    pararMovimiento();
            }
            moviendo=true;
            System.out.println("ORDEN - Moviendo derecha");
            grua.getPluma().addControl(new ControlRotar(-0.005f));
	}
	
        /**
         * Rota la pluma hacia la izquierda.
         */
	public void rotarIzquierda(){
            if(moviendo){
                    pararMovimiento();
            }
            moviendo=true;
            System.out.println("ORDEN - Moviendo izquierda");
            //grua.getPluma().addControl(new ControlRotar(0.0005f));
    grua.getPluma().addControl(new ControlRotar(0.005f));
	}
        
	/**
         * Sube el gancho de la grua.
         */
	public void subir(){
            if(moviendo){
                    pararMovimiento();
            }
            moviendo=true;
            System.out.println("ORDEN - Subiendo");

            if(limiteGanchoSubir()){
                grua.getGancho().addControl(new ControlGanchoSubir(1.f));
            }
	}
	
        /**
         * Baja el gancho de la grua.
         */
	public void bajar(){
            if(moviendo){
                    pararMovimiento();
            }
            moviendo=true;
            System.out.println("ORDEN - Bajando");

            if(limiteGanchoBajar()){
                grua.getGancho().addControl(new ControlGanchoBajar(1.f));
                }
	}
        
        /**
         * Comprueba si el gancho puede subir.
         * @return 
         */
        public boolean limiteGanchoSubir(){
            boolean resultado = false;
            if(grua.getGancho().getLocalTranslation().getY() < 58.f){
                resultado = true;
            }
            System.out.println("Altura gancho: "+grua.getGancho().getLocalTranslation().getY());
            return resultado;
        }
        
        /**
         * Comprueba si el gancho puede bajar.
         * @return 
         */
        public boolean limiteGanchoBajar(){
            boolean resultado = false;
            if(grua.getGancho().getLocalTranslation().getY() > -90.f){
                resultado = true;
            }
            System.out.println("Altura gancho: "+grua.getGancho().getLocalTranslation().getY());
            return resultado;
        }
	
        
        
        /**
         * Comprueba si el carro puede moverse hacia delante.
         * @return 
         */
        public boolean limiteCarroAdelante(){
            boolean resultado = false;
            if( grua.getCarro().getLocalTranslation().getX() > -90.f){
                resultado = true;
            }
            System.out.println("Posicion carro: "+grua.getCarro().getLocalTranslation().getX());
            return resultado;
        }
	
        /**
        * Mueve la grua hacia delante
        */
        public void adelante(){
            if(moviendo){
                pararMovimiento();
            }
            moviendo=true;
            System.out.println("ORDEN - Moviendo adelante");
            if(limiteCarroAdelante()){
                grua.getCarro().addControl(new ControlCarroDelante(0.5f));
            }
        }
        
        /**
         * Mueve la grua hacia detras
         */
	public void atras(){
            if(moviendo){
                    pararMovimiento();
            }
            moviendo=true;
            System.out.println("ORDEN - Moviendo atras");
            if(limiteCarroAtras()){
                grua.getCarro().addControl(new ControlCarroAtras(0.5f));
            }
	}
	
        /**
         * Comprueba si el carro puede moverse hacia atras.
         * @return 
         */
        public boolean limiteCarroAtras(){
            boolean resultado = false;
            if( grua.getCarro().getLocalTranslation().getX() < 5.f){
                resultado = true;
            }
            System.out.println("Posicion carro: "+grua.getCarro().getLocalTranslation().getX());
            return resultado;
        }
	
	
}
