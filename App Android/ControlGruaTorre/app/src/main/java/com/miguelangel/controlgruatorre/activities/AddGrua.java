package com.miguelangel.controlgruatorre.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelangel.controlgruatorre.R;
import com.miguelangel.controlgruatorre.persistencia.Conexion;
import com.miguelangel.controlgruatorre.persistencia.Persistencia;

public class AddGrua extends Activity  implements View.OnClickListener{

    private int contadorVolver;
    private Persistencia persistencia;
    EditText editTextDireccion, editTextUsuario, editTextClave, editTextNombre;
    ImageButton verClave;
    Button buttonGuardar;
    Boolean claveOculta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grua);
        contadorVolver=0;
        claveOculta=true;

        persistencia = new Persistencia(getApplicationContext());

        editTextDireccion = (EditText) findViewById(R.id.EditTextDireccion);
        editTextUsuario = (EditText) findViewById(R.id.EditTextUsuario);
        editTextClave = (EditText) findViewById(R.id.EditTextClave);
        editTextNombre = (EditText) findViewById(R.id.EditTextNombre);
        buttonGuardar = (Button) findViewById(R.id.buttonGuardarGrua);
        verClave = (ImageButton) findViewById(R.id.verClave);

        buttonGuardar.setOnClickListener(this);
        verClave.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        /*if(contadorVolver < 1 || editTextDireccion.getText().toString().length() > 1 ||
                editTextUsuario.getText().toString().length() > 1 ||
                editTextClave.getText().toString().length() > 1 ||
                editTextNombre.getText().toString().length() > 1){
            if(contadorVolver>0){
                finish();
            }
            contadorVolver++;
            Toast.makeText(getApplicationContext(), R.string.deseaSalir, Toast.LENGTH_LONG).show();

        }else {
            finish();
        }*/
        createLoginDialogo().show();

    }

    public AlertDialog createLoginDialogo() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_cancelar_nueva, null);

        builder.setView(v);

        TextView text_dialog_cancelar_nueva = (TextView)v.findViewById(R.id.text_dialog_cancelar_nueva);
        text_dialog_cancelar_nueva.setText("¿Desea cancelar la creacion de la grua?");

        /*aceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );*/
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        builder.setNeutralButton("Cancelar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });


        return builder.create();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGuardarGrua:
                if(editTextDireccion.getText().toString().length() > 1 &&
                        editTextUsuario.getText().toString().toString().length() > 1 &&
                        editTextClave.getText().toString().toString().length() > 1 &&
                        editTextNombre.getText().toString().length() > 1) {
                    Conexion conexion = new Conexion(editTextDireccion.getText().toString(),
                            editTextUsuario.getText().toString().toString(),
                            editTextClave.getText().toString().toString(), editTextNombre.getText().toString());
                    persistencia.addConexion(conexion);
                    Toast.makeText(getApplicationContext(),R.string.guardadoConExito, Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(), R.string.datosIncompletos, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.verClave:
                if (claveOculta){
                    claveOculta=false;
                    editTextClave.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    verClave.setImageResource(R.drawable.ic_action_ojo_off);

                }else {
                    claveOculta=true;
                    editTextClave.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    verClave.setImageResource(R.drawable.ic_action_ojo);
                }

                break;
        }
    }
}
