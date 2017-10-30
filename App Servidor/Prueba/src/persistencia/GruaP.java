/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import com.miguelangel.controlgruatorre.cliente.DatosConexion;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MiguelAngel
 */
@XmlRootElement(name="grua")
public class GruaP {
    private List<DatosConexion> usuarios;
    
    public GruaP(){}
        
    @XmlElementWrapper(name="usuarios")
    @XmlElement(name="usuario")
    public List<DatosConexion> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<DatosConexion> usuarios) {
        this.usuarios = usuarios;
    }
}
