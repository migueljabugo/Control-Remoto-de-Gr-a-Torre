package com.miguelangel.controlgruatorre.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.NetworkOnMainThreadException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelangel.controlgruatorre.JoyStickClass;
import com.miguelangel.controlgruatorre.R;
import com.miguelangel.controlgruatorre.cliente.Cliente;
import com.miguelangel.controlgruatorre.cliente.DatosConexion;
import com.miguelangel.controlgruatorre.cliente.TipoOrden;
import com.miguelangel.controlgruatorre.persistencia.Conexion;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ControlActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;
    private RelativeLayout layout_joystick_izquierdo;
    private RelativeLayout layout_joystick_derecho;
    private ImageButton buttonParadaEmergencia;
    private TextView textViewMensajeControl;

    private JoyStickClass jsIzquierdo;
    private JoyStickClass jsDerecho;
    private Conexion conexion;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        getSupportActionBar().hide();

        pedirPermisoInternet();


        conexion = (Conexion) getIntent().getSerializableExtra("CONEXION");


        Log.i("CONTROL", "Usuario: "+conexion.getUsuario());
        Log.i("CONTROL", "Clave: "+conexion.getClave());
        Log.i("CONTROL", "Nombre: "+conexion.getNombre());
        Log.i("CONTROL", "Direccion: "+conexion.getDireccion());
        DatosConexion datosConexion = new DatosConexion(conexion.getUsuario(),conexion.getClave());
        cliente = new Cliente( conexion.getDireccion(), 6000, datosConexion);
        try {
            cliente.conectar();
            Toast.makeText(getApplicationContext(), getText(R.string.conectado), Toast.LENGTH_SHORT).show();
        }catch (NetworkOnMainThreadException e){
            errorAlConectarServidor(e);
            //textViewMensajeControl.setText(R.string.errorConectar);
        }

        buttonParadaEmergencia = (ImageButton) findViewById(R.id.buttonParadaEmergencia);
        textViewMensajeControl = (TextView) findViewById(R.id.textViewMensajeControl);
        layout_joystick_izquierdo = (RelativeLayout)findViewById(R.id.layout_joystick_izquierdo);
        layout_joystick_derecho = (RelativeLayout)findViewById(R.id.layout_joystick_derecho);


        buttonParadaEmergencia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    cliente.movimiento(TipoOrden.PARAR_MOVIMIENTO);

                    //cliente.movimiento(TipoOrden.DESCONECTAR);
                    textViewMensajeControl.setText(getText(R.string.paradaEmergencia));
                }catch (NetworkOnMainThreadException e){
                    desconectado();
                }
            }
        });
        buttonParadaEmergencia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    buttonParadaEmergencia.setRotation(25);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    buttonParadaEmergencia.setRotation(-25);
                }
                return true;
            }
        });


        jsIzquierdo = new JoyStickClass(getApplicationContext()
                , layout_joystick_izquierdo, R.drawable.image_button);
        jsIzquierdo.setStickSize(150, 150);
        jsIzquierdo.setLayoutSize(500, 500);
        jsIzquierdo.setLayoutAlpha(150);
        jsIzquierdo.setStickAlpha(100);
        jsIzquierdo.setOffset(90);
        jsIzquierdo.setMinimumDistance(50);

        layout_joystick_izquierdo.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                jsIzquierdo.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = jsIzquierdo.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                        //up
                        try {
                            cliente.movimiento(TipoOrden.SUBIR);
                            textViewMensajeControl.setText(R.string.subiendo);
                        }catch (NetworkOnMainThreadException e){
                            desconectado();
                        }
                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        //derecha
                        try{
                            cliente.movimiento(TipoOrden.ROTAR_DERECHA);
                            textViewMensajeControl.setText(R.string.rotar_derecha);
                        }catch (NetworkOnMainThreadException e){
                            desconectado();
                        }
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        //abajo
                        try{
                            cliente.movimiento(TipoOrden.BAJAR);
                            textViewMensajeControl.setText(R.string.bajando);
                        }catch (NetworkOnMainThreadException e){
                            desconectado();
                        }
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        //izquierda
                        try{
                            cliente.movimiento(TipoOrden.ROTAR_IZQUIERDA);
                            textViewMensajeControl.setText(R.string.rotar_izquierda);
                        }catch (NetworkOnMainThreadException e){
                            desconectado();
                        }

                    } else if(direction == JoyStickClass.STICK_NONE) {
                        //centro
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    //Esta en el centro o sin hacer nada
                    try{
                        cliente.movimiento(TipoOrden.PARAR_MOVIMIENTO);
                        textViewMensajeControl.setText("");
                    }catch (NetworkOnMainThreadException e){
                        desconectado();
                    }
                }
                return true;
            }
        });


        jsDerecho = new JoyStickClass(getApplicationContext()
                , layout_joystick_derecho, R.drawable.image_button);
        jsDerecho.setStickSize(150, 150);
        jsDerecho.setLayoutSize(500, 500);
        jsDerecho.setLayoutAlpha(150);
        jsDerecho.setStickAlpha(100);
        jsDerecho.setOffset(90);
        jsDerecho.setMinimumDistance(50);

        layout_joystick_derecho.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                jsDerecho.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = jsDerecho.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                        //up


                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        //derecha
                        try{
                            cliente.movimiento(TipoOrden.ATRAS);
                            textViewMensajeControl.setText(R.string.carro_atras);
                        }catch (NetworkOnMainThreadException e){
                            desconectado();
                        }
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        //abajo

                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        //izquierda
                        try{
                            cliente.movimiento(TipoOrden.ADELANTE);
                            textViewMensajeControl.setText(R.string.carro_adelante);
                        }catch (NetworkOnMainThreadException e){
                            desconectado();
                        }

                    } else if(direction == JoyStickClass.STICK_NONE) {
                        //centro
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    //Esta en el centro o sin hacer nada
                    try{
                        cliente.movimiento(TipoOrden.PARAR_MOVIMIENTO);
                        textViewMensajeControl.setText("");
                    }catch (NetworkOnMainThreadException e){
                        desconectado();
                    }
                }
                return true;
            }
        });

    }

    private void pedirPermisoInternet() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET))
            {

        /* Aquí se mostrará la explicación al usuario de porqué es
        necesario el uso de un determinado permiso, pudiéndose mostrar de manera asíncrona, o lo que es lo mismo, desde un
        hilo secundario, sin bloquear el hilo principal, y a la espera de
        que el usuario concede el permiso necesario tras visualizar la explicación.*/
            }
            else
            {

        /* Se realiza la petición del permiso. En este caso permisos
        para leer los contactos.*/
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_INTERNET:
            {
                //Si la petición es cancelada, el resultado estará vacío.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Permiso aceptado

                } else
                {
                    //Permiso denegado. Desactivar la funcionalidad que dependía de dicho permiso.
                    finish();
                }
                return;
            }

            // A continuación, se expondrían otras posibilidades de petición de permisos.
        }
    }

    private void errorAlConectarServidor(NetworkOnMainThreadException e) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConectar), Toast.LENGTH_SHORT).show();

        finish();
    }

    private void desconectado(){
        textViewMensajeControl.setText(getText(R.string.desconectado));
    }

    @Override
    public void onBackPressed() {
        try{
            cliente.movimiento(TipoOrden.PARAR_MOVIMIENTO);
            cliente.movimiento(TipoOrden.DESCONECTAR);
            Toast.makeText(getApplicationContext(), getText(R.string.desconectando), Toast.LENGTH_SHORT).show();
            finish();
        }catch (NetworkOnMainThreadException e){
            desconectado();
            finish();
        }
    }

    @Override
    protected void onPause() {
        try{
            cliente.movimiento(TipoOrden.PARAR_MOVIMIENTO);
            cliente.movimiento(TipoOrden.DESCONECTAR);
        }catch (NetworkOnMainThreadException e){

        }
        super.onPause();
    }
}
