package com.raulh.miempleo_final.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raulh on 05/11/2014.
 */
public class SolicitanteSQLiteHelper extends SQLiteOpenHelper {


    public static final String TABLE = "solicitante";
    public static final String COLUMN_ID = "idsolic";
    public static final String COLUMN_NOMBRE = "nombresolic";
    public static final String COLUMN_APELLIDO = "apellidosolic";
    public static final String COLUMN_FECHANAC = "fechanacsolic";
    public static final String COLUMN_TELEFONO = "telsolic";
    public static final String COLUMN_GENERO = "generosolic";
    public static final String COLUMN_IDTIPO = "idtipo";
    public static final String COLUMN_IDMUN = "idmun";
    public static final String COLUMN_IDNIVEL = "idnivel";


    public static final String DATABASE_NAME = "miempleo.solicitante";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " +
            TABLE + "(" + COLUMN_ID + " integer primary key autoincrement," +
            COLUMN_NOMBRE + " text not null," +
            COLUMN_APELLIDO + " text not null," +
            COLUMN_FECHANAC + " text not null," +
            COLUMN_TELEFONO + " text ," +
            COLUMN_GENERO + " text ," +
            COLUMN_IDTIPO + " text , " +
            COLUMN_IDMUN + " text , " +
            COLUMN_IDNIVEL + " text " +
            ");";



    public SolicitanteSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
