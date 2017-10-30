package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.cinematic.Cinematic;
import com.jme3.light.PointLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * @author Miguel Angel Gonzalez
 */
public class graficos extends SimpleApplication {
    
    private Spatial scene;
    private Grua grua;
    private Cinematic rotarDerecha;
    boolean prueba =true;
    HiloServidor hilo;

    public static void main(String[] args) throws InterruptedException {
        final graficos app = new graficos();
        app.start();
        System.out.println("Interface creada");
        
        
       
       
       
    }
    
    
   
   

    @Override
    public void simpleInitApp() {
        
      //Cargar escena con el mundo
        scene = assetManager.loadModel("Scenes/mundo.j3o");
        rootNode.attachChild(scene);
        
        
        posicionarCamara();
        grua = new Grua(this.assetManager);
        rootNode.attachChild(grua);
        cargarPuntoLuz();
        crearEscavadora();
        cargarPisos();
        
        hiloServidor();
    }
    
     

    @Override
    public void simpleUpdate(float tpf) {
        
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
    
    public void cargarPisos(){
        Node piso = (Node)assetManager.loadModel("Models/pisos/piso1/building.obj");
        piso.scale(0.15f);
        piso.move(38, 0, -45);
        piso.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(piso);
        
        
        
        
        
        
        
        
    }
    
    public void crearEscavadora(){
        Node escavadora = (Node)assetManager.loadModel("Models/escavator/Excavator.obj");
        escavadora.scale(0.005f, 0.005f, 0.005f);
        escavadora.move(-30, 5, -50);
        escavadora.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(escavadora);
    }
    


    /**
     * Crea un hilo con el servidor y la recepcion de ordenes
     * 
     */
    private void hiloServidor() {
        //Crear hilo del servidor
        hilo = new HiloServidor(grua);
        hilo.start();
        System.out.println("Hilo creado");
    }

    /**
     * Carga todos los modelos necesarios para crear la grua.
     * 
     */
   /* private void cargarGrua() {
        try{
            carro = (Node)assetManager.loadModel("Models/carro.obj");
            carro.scale(0.5f);
            
            //lo mas cerca 10.f
            //lo mas lejos -40.f
            //carro.setLocalTranslation(-40.f, 0.f, 0.f);
            rootNode.attachChild(carro);
            
            gancho = (Node)assetManager.loadModel("Models/gancho.obj");
            gancho.scale(0.5f);
            //punto mas bajo -10.f
            //punto mas alto 23.f
            //gancho.setLocalTranslation(0.f, 23.f, 0.f);
            rootNode.attachChild(gancho);
            
            pluma = (Node)assetManager.loadModel("Models/pluma.obj");
            pluma.scale(0.5f);
            rootNode.attachChild(pluma);
            
            torre = (Node)assetManager.loadModel("Models/torre.obj");
            torre.scale(0.5f);
            rootNode.attachChild(torre);
            
            
        }
        catch(Exception e)
        {
            System.out.println("Fallo al cargar los modelos");
        }
    }*/

    
    /**
     * Posicionar camara
     */
    private void posicionarCamara() {
        viewPort.getCamera().setLocation(new Vector3f(60.f, 70.f, 80.f));
        viewPort.getCamera().lookAtDirection(new Vector3f(-10f, -10f, -25f), new Vector3f(0f, 0f, 0f));
        
        /*viewPort.getCamera().setLocation(new Vector3f(80.f, 70.f, 80.f));
        viewPort.getCamera().lookAtDirection(new Vector3f(-10f, -10f, -25f), new Vector3f(0f, 0f, 0f));*/
    }

    /**
     * Carga punto de luz
     */
    private void cargarPuntoLuz() {
        PointLight lamp = new PointLight();
        //lamp.setPosition(new Vector3f(20.f, 100.f, 80.f));
        lamp.setPosition(new Vector3f(50.f, 200.f, 50.f));
        //lamp.setColor(ColorRGBA.White);
        rootNode.addLight(lamp); 
    }

    
}
