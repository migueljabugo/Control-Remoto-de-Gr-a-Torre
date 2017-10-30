package com.miguelangel.controlgruatorre.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MiguelAngel on 16/05/2017.
 */

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String conexiones="CREATE TABLE conexiones(" +
                "direccion TEXT," +
                "usuario TEXT," +
                "clave TEXT," +
                "nombre TEXT" +
                ");";

        sqLiteDatabase.execSQL(conexiones);


        String sqlInsert;
        sqlInsert="INSERT INTO conexiones VALUES('192.168.1.250','usuario','usuario', 'Grúa de prueba');";
        sqLiteDatabase.execSQL(sqlInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS conexiones");
    }



}
