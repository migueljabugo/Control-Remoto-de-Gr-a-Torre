/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import gruaTorre.Servidor;

/**
 *
 * @author MiguelAngel
 */
public class HiloServidor extends Thread{
    
    private Grua grua;
    private Servidor server;
    
    public HiloServidor(Grua grua){
        this.grua = grua;
    }
    
    
    
    
    public void run() {
        try{
            server = new Servidor(grua);
            server.ejecucion();
        } catch (ClassNotFoundException e) {
            System.out.println("Fallo al cargar el servidor");
        }
            
        }
    
    
    
}
