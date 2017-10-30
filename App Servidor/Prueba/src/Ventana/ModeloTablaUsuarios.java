/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import com.miguelangel.controlgruatorre.cliente.DatosConexion;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MiguelAngel
 */
public class ModeloTablaUsuarios extends AbstractTableModel{
    private final List<DatosConexion> usuarios;
    
    public ModeloTablaUsuarios(List<DatosConexion> usuarios){
        this.usuarios=usuarios;
    }
    
    
    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    
    @Override
    public int getColumnCount() {
        return 1;
    }

    
    @Override
    public String getColumnName(int i) {
        return "Usuarios";
    }

 
    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

   
    @Override
    public Object getValueAt(int i, int i1) {
        return usuarios.get(i).getUsuario();
    }
}
