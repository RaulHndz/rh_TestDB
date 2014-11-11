package com.raulh.miempleo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.raulh.miempleo_final.helpers.MunicipioSQLiteHelper;
import com.raulh.miempleo_final.models.Municipio_c;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class MunicipioDataSource {

    //Database Fields
    private SQLiteDatabase database;
    private MunicipioSQLiteHelper dbHelper;
    private String[] allColumns = {MunicipioSQLiteHelper.COLUMN_ID,
            MunicipioSQLiteHelper.COLUMN_NOMBRE,
            MunicipioSQLiteHelper.COLUMN_IDDEPTO
    };

    public MunicipioDataSource(Context context) {

        dbHelper = new MunicipioSQLiteHelper(context);

    }

    public void open() {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public Municipio_c createMunicipio(String nombre, String iddep) {

        ContentValues values = new ContentValues();

        values.put(MunicipioSQLiteHelper.COLUMN_NOMBRE, nombre);
        values.put(MunicipioSQLiteHelper.COLUMN_IDDEPTO, iddep);

        long insertId = database.insert(MunicipioSQLiteHelper.TABLE, null, values);

        Cursor cursor = database.query(MunicipioSQLiteHelper.TABLE, allColumns,
                MunicipioSQLiteHelper.COLUMN_ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();

        Municipio_c newMuncipio = cursorToMunicipio(cursor);

        cursor.close();

        return newMuncipio;

    }

    public Municipio_c findMunicipio(long municipioID) {
        Municipio_c c = null;
        String query = "SELECT * FROM " + MunicipioSQLiteHelper.TABLE + " WHERE _id = " + municipioID;
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            c = cursorToMunicipio(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return c;
    }

    private Municipio_c cursorToMunicipio(Cursor cursor) {
        return new Municipio_c(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
    }

    public List<Municipio_c> getAllMunicipios() {
        List<Municipio_c> municipios = new ArrayList<Municipio_c>();
        Cursor cursor = database.query(MunicipioSQLiteHelper.TABLE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            municipios.add(cursorToMunicipio(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return municipios;
    }

    public void deleteMunicipio(long municipioId) {
        try {
            database.delete(MunicipioSQLiteHelper.TABLE, MunicipioSQLiteHelper.COLUMN_ID + " = " + municipioId, null);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public Municipio_c updateMunicipio(Municipio_c municipio) {
        ContentValues values = new ContentValues();
        values.put(MunicipioSQLiteHelper.COLUMN_NOMBRE, municipio.getNombremun());
        values.put(MunicipioSQLiteHelper.COLUMN_IDDEPTO, municipio.getIddep());
        long insertId = database.update(MunicipioSQLiteHelper.TABLE, values, MunicipioSQLiteHelper.COLUMN_ID + " = " + municipio.getIdmun(), null);
        Cursor cursor = database.query(MunicipioSQLiteHelper.TABLE, allColumns,
                MunicipioSQLiteHelper.COLUMN_ID + "=" + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        municipio = cursorToMunicipio(cursor);
        cursor.close();
        return municipio;
    }

}
