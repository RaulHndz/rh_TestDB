package com.raulh.miempleo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.raulh.miempleo_final.helpers.SolicitanteSQLiteHelper;
import com.raulh.miempleo_final.models.Solicitante_c;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class SolicitanteDataSource {

    //Database Fields
    private SQLiteDatabase database;
    private SolicitanteSQLiteHelper dbHelper;
    private String[] allColumns = {SolicitanteSQLiteHelper.COLUMN_ID,
            SolicitanteSQLiteHelper.COLUMN_NOMBRE,
            SolicitanteSQLiteHelper.COLUMN_APELLIDO,
            SolicitanteSQLiteHelper.COLUMN_FECHANAC,
            SolicitanteSQLiteHelper.COLUMN_TELEFONO,
            SolicitanteSQLiteHelper.COLUMN_GENERO,
            SolicitanteSQLiteHelper.COLUMN_IDTIPO,
            SolicitanteSQLiteHelper.COLUMN_IDMUN,
            SolicitanteSQLiteHelper.COLUMN_IDNIVEL

    };

    public SolicitanteDataSource(Context context) {

        dbHelper = new SolicitanteSQLiteHelper(context);

    }

    public void open() {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public Solicitante_c createSolicitante(String nombre,
                                           String apellido,
                                           String fechanac,
                                           String telefono,
                                           String genero,
                                           String idtipo,
                                           String idmun,
                                           String idnivel
    ) {

        ContentValues values = new ContentValues();
        Solicitante_c newSolicitante = null;
        try{

        values.put(SolicitanteSQLiteHelper.COLUMN_NOMBRE, nombre);
        values.put(SolicitanteSQLiteHelper.COLUMN_APELLIDO, apellido);
        values.put(SolicitanteSQLiteHelper.COLUMN_FECHANAC, fechanac);
        values.put(SolicitanteSQLiteHelper.COLUMN_TELEFONO, telefono);
        values.put(SolicitanteSQLiteHelper.COLUMN_GENERO, genero);
        values.put(SolicitanteSQLiteHelper.COLUMN_IDTIPO, idtipo);
        values.put(SolicitanteSQLiteHelper.COLUMN_IDMUN, idmun);
        values.put(SolicitanteSQLiteHelper.COLUMN_IDNIVEL, idnivel);

        long insertId = database.insert(SolicitanteSQLiteHelper.TABLE, null, values);

        Cursor cursor = database.query(SolicitanteSQLiteHelper.TABLE, allColumns,
                SolicitanteSQLiteHelper.COLUMN_ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();

         newSolicitante = cursorToSolicitante(cursor);

        cursor.close();
        }catch(Throwable e){
            e.printStackTrace();
        }


        return newSolicitante;

    }

    public Solicitante_c findSolicitante(long solicitanteID) {
        Solicitante_c c = null;
        String query = "SELECT * FROM " + SolicitanteSQLiteHelper.TABLE + " WHERE _id = " + solicitanteID;
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            c = cursorToSolicitante(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return c;
    }

    private Solicitante_c cursorToSolicitante(Cursor cursor) {
        return new Solicitante_c(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8)

        );
    }

    public List<Solicitante_c> getAllSolicitante() {
        List<Solicitante_c> solicitante = new ArrayList<Solicitante_c>();
        Cursor cursor = database.query(SolicitanteSQLiteHelper.TABLE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            solicitante.add(cursorToSolicitante(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return solicitante;
    }

    public void deleteSolicitante(long solicitanteId) {
        database.delete(SolicitanteSQLiteHelper.TABLE, SolicitanteSQLiteHelper.COLUMN_ID
                + " = " + solicitanteId, null);
    }

    public Solicitante_c updateDepartament(Solicitante_c solicitante) {
        ContentValues values = new ContentValues();
        values.put(SolicitanteSQLiteHelper.COLUMN_NOMBRE, solicitante.getNombresolic());
        values.put(SolicitanteSQLiteHelper.COLUMN_APELLIDO, solicitante.getApellidosolic());
        values.put(SolicitanteSQLiteHelper.COLUMN_FECHANAC, solicitante.getFechanacsolic());
        values.put(SolicitanteSQLiteHelper.COLUMN_TELEFONO, solicitante.getTelsolic());
        values.put(SolicitanteSQLiteHelper.COLUMN_GENERO, solicitante.getGenerosolic());
        values.put(SolicitanteSQLiteHelper.COLUMN_IDNIVEL, solicitante.getIdnivel());
        values.put(SolicitanteSQLiteHelper.COLUMN_IDMUN, solicitante.getIdmun());
        values.put(SolicitanteSQLiteHelper.COLUMN_IDTIPO, solicitante.getIdtipo());

        long insertId = database.update(SolicitanteSQLiteHelper.TABLE, values, SolicitanteSQLiteHelper.COLUMN_ID + " = " + solicitante.getIdsoli(), null);
        Cursor cursor = database.query(SolicitanteSQLiteHelper.TABLE, allColumns,
                SolicitanteSQLiteHelper.COLUMN_ID + "=" + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        solicitante = cursorToSolicitante(cursor);
        cursor.close();
        return solicitante;
    }

}
