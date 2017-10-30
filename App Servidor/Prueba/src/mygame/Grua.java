/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import gruaTorre.Servidor;

/**
 *
 * @author MiguelAngel
 */
public class Grua extends Node{
   /* private Integer posCarro;
    private Integer posGancho;
    private Integer pospluma;
    
    private Integer maxPosCarro;
    private Integer maxPosGancho;
    private Integer minPosCarro;
    private Integer minPosGancho;*/
    
    private AssetManager assetManager;
    
    private Node carro, pluma, gancho, torre, caja;
    private Servidor server;
    
    
    public Grua(AssetManager assetManager){
        /*this.posCarro = 0;
        this.posGancho = 0;
        this.pospluma = 0;
        this.maxPosCarro = 100;
        this.maxPosGancho = 100;
        this.minPosCarro = 0;
        this.minPosGancho = 0;*/
        
        carro = new Node();
        carro = (Node)assetManager.loadModel("Models/carro.obj");
        carro.scale(0.5f);
        carro.move(0, 38f, 0);
        carro.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        //lo mas cerca 10.f
        //lo mas lejos -40.f
        //carro.setLocalTranslation(-40.f, 0.f, 0.f);
        gancho = new Node();
        gancho = (Node)assetManager.loadModel("Models/gancho.obj");
        gancho.scale(0.5f);
        gancho.move(-10, 38f, 0);
        gancho.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        
        //punto mas bajo -10.f
        //punto mas alto 23.f
        //gancho.setLocalTranslation(0.f, 23.f, 0.f);
        pluma = new Node();
        pluma = (Node)assetManager.loadModel("Models/pluma_centro.obj");
        pluma.move(0.8f, 0f, -0.6f);
        pluma.scale(0.5f);
        pluma.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        
        torre = new Node();
        torre = (Node)assetManager.loadModel("Models/torre.obj");
        torre.scale(0.5f);
        torre.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        
        
        /****************************************/
        caja = new Node();
        caja = (Node)assetManager.loadModel("Models/caja/caja.j3o");
        caja.scale(1.f);
        caja.move(-20, 24, -2);
        
        /*Material material = new Material();
        //material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/Wooden Box.mtl"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxD3.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxD6.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxD5.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxD4.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxD2.tga"));
        
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxD1.tga"));*/
        /*
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxN1.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxN2.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxN3.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxN4.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxN5.tga"));
        material.setTexture("ColorMap", assetManager.loadTexture("Models/caja/WoodenBoxN6.tga"));*/
        //caja.setMaterial(material);

        gancho.attachChild(caja);
        /****************************************/
        
        
        
        //gancho.attachChild(caja);
        carro.attachChild(gancho);
        pluma.attachChild(carro);
        this.attachChild(pluma);
        this.attachChild(torre);
        
        
        
    }

    
    
    
    

    /*public Integer getPosCarro() {
        return posCarro;
    }*/

   /* public void setPosCarro(Integer posCarro) {
        this.posCarro = posCarro;
    }*/

    /*public Integer getPosGancho() {
        return posGancho;
    }*/

    /*public void setPosGancho(Integer posGancho) {
        this.posGancho = posGancho;
    }*/

   /* public Integer getPospluma() {
        return pospluma;
    }*/

   /* public void setPospluma(Integer pospluma) {
        this.pospluma = pospluma;
    }*/

   /* public Integer getMaxPosCarro() {
        return maxPosCarro;
    }/

   /* public void setMaxPosCarro(Integer maxPosCarro) {
        this.maxPosCarro = maxPosCarro;
    }*/

   /* public Integer getMaxPosGancho() {
        return maxPosGancho;
    }*/

    /*public void setMaxPosGancho(Integer maxPosGancho) {
        this.maxPosGancho = maxPosGancho;
    }*/

   /* public Integer getMinPosCarro() {
        return minPosCarro;
    }*/

   /* public void setMinPosCarro(Integer minPosCarro) {
        this.minPosCarro = minPosCarro;
    }*/

    /*public Integer getMinPosGancho() {
        return minPosGancho;
    }*/

    /*public void setMinPosGancho(Integer minPosGancho) {
        this.minPosGancho = minPosGancho;
    }*/

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Node getCarro() {
        return carro;
    }

    public void setCarro(Node carro) {
        this.carro = carro;
    }

    public Node getPluma() {
        return pluma;
    }

    public void setPluma(Node pluma) {
        this.pluma = pluma;
    }

    public Node getGancho() {
        return gancho;
    }

    public void setGancho(Node gancho) {
        this.gancho = gancho;
    }

    public Node getTorre() {
        return torre;
    }

    public void setTorre(Node torre) {
        this.torre = torre;
    }

    public Servidor getServer() {
        return server;
    }

    public void setServer(Servidor server) {
        this.server = server;
    }

    
    
    
}
