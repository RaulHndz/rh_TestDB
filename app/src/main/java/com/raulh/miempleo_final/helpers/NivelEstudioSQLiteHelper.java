package com.raulh.miempleo_final.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raulh on 05/11/2014.
 */
public class NivelEstudioSQLiteHelper extends SQLiteOpenHelper {


    public static final String TABLE = "nivelestudio";
    public static final String COLUMN_ID = "idnivel";
    public static final String COLUMN_NOMBRE = "nombrenivel";
    private static final String DATABASE_CREATE = "CREATE TABLE " +
            TABLE + "(" + COLUMN_ID + " integer primary key autoincrement," +
            COLUMN_NOMBRE + " text not null);";
    public static final String DATABASE_NAME = "miempleo.nivelestudio";
    public static final int DATABASE_VERSION = 1;


    public NivelEstudioSQLiteHelper(Context context) {


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
