package com.miguelangel.controlgruatorre.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelangel.controlgruatorre.AdaptadorConexion;
import com.miguelangel.controlgruatorre.R;
import com.miguelangel.controlgruatorre.persistencia.Conexion;
import com.miguelangel.controlgruatorre.persistencia.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Conexion> conexiones;
    private RecyclerView recyclerView;
    private Persistencia persistencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        persistencia = new Persistencia(getApplicationContext());


        conexiones = persistencia.getDatosConexion();



        recyclerView =(RecyclerView)findViewById(R.id.RecyclerViewConexiones);
        recyclerView.setHasFixedSize(true);

        final AdaptadorConexion adaptadorConexion = new AdaptadorConexion(conexiones);


        recyclerView.setAdapter(adaptadorConexion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent =new Intent(getApplicationContext(), ControlActivity.class);
                        intent.putExtra("CONEXION", conexiones.get(position));
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                       createLoginDialogo(view, position).show();
                    }
                })
        );


    }
    public AlertDialog createLoginDialogo(final View view, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_cancelar_nueva, null);

        builder.setView(v);

        TextView text_dialog_cancelar_nueva = (TextView)v.findViewById(R.id.text_dialog_cancelar_nueva);
        text_dialog_cancelar_nueva.setText("¿Desea eliminar "+conexiones.get(position).getNombre()+"?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                //conexiones.remove(position);
                persistencia.removeConexion(conexiones.get(position));
                conexiones = persistencia.getDatosConexion();
                final AdaptadorConexion adaptadorConexion = new AdaptadorConexion(conexiones);
                recyclerView.setAdapter(adaptadorConexion);

            }
        });
        builder.setNeutralButton("Cancelar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        return builder.create();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_grua:
                Toast.makeText(getApplicationContext(), R.string.nueva_grua, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AddGrua.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.conexiones = persistencia.getDatosConexion();
        final AdaptadorConexion adaptadorConexion = new AdaptadorConexion(conexiones);
        recyclerView.setAdapter(adaptadorConexion);
    }



    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }



        @Override
        public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
    }


}
