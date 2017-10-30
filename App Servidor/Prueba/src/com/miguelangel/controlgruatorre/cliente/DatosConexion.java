package com.miguelangel.controlgruatorre.cliente;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="usuario")
public class DatosConexion implements Serializable{

	private static final long serialVersionUID = 1L;
	private String usuario;
	private String clave;
	
	public DatosConexion(String usuario, String clave){
		this.usuario = usuario;
		this.clave = clave;
	}

	private DatosConexion(){}
	
        @XmlAttribute
	public String getClave() {
		return clave;
	}
        
        @XmlAttribute
	public String getUsuario() {
		return usuario;
	}
        
        
	
	

	private void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	private void setClave(String clave) {
		this.clave = clave;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosConexion other = (DatosConexion) obj;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	
	public static DatosConexion parse(String datosConexion){
		DatosConexion s= new DatosConexion();
		String[] lista= datosConexion.split(datosConexion);
		s.setUsuario(lista[0]);
		s.setClave(lista[1]);
		
		return s;
	}

    @Override
    public String toString() {
        return usuario+""+clave;
    }

        
	
	
}
