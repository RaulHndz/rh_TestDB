package com.raulh.miempleo_final.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raulh on 05/11/2014.
 */
public class MunicipioSQLiteHelper extends SQLiteOpenHelper {


    public static final String TABLE = "municipio";
    public static final String COLUMN_ID = "idmun";
    public static final String COLUMN_NOMBRE = "nombredmun";
    public static final String COLUMN_IDDEPTO = "iddep";
    private static final String DATABASE_CREATE = "CREATE TABLE " +
            TABLE + "(" + COLUMN_ID + " integer primary key autoincrement," +
            COLUMN_NOMBRE + " text not null, " +
            COLUMN_IDDEPTO + " text not null);";
    public static final String DATABASE_NAME = "miempleo.municipio";
    public static final int DATABASE_VERSION = 1;

    public MunicipioSQLiteHelper(Context context) {
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
