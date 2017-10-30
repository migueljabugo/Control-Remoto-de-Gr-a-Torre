/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author MiguelAngel
 */
public class ControlGanchoBajar extends AbstractControl{
    
    private float speed;
    
    
    public ControlGanchoBajar(float speed){
        this.speed = speed;
    }
    public ControlGanchoBajar(){
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        spatial.move(0, -tpf, 0);
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        ControlGanchoBajar control = new ControlGanchoBajar();
        control.setSpeed(speed);
        control.setSpatial(spatial);
        return control;        
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
}
