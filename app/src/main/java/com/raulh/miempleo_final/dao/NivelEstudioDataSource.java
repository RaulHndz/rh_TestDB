package com.raulh.miempleo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.raulh.miempleo_final.helpers.NivelEstudioSQLiteHelper;
import com.raulh.miempleo_final.models.NivelEstudio_c;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class NivelEstudioDataSource {

    //Database Fields
    private SQLiteDatabase database;
    private NivelEstudioSQLiteHelper dbHelper;
    private String[] allColumns = {NivelEstudioSQLiteHelper.COLUMN_ID, NivelEstudioSQLiteHelper.COLUMN_NOMBRE};

    public NivelEstudioDataSource(Context context) {

        dbHelper = new NivelEstudioSQLiteHelper(context);

    }

    public void open() {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public NivelEstudio_c createNivelEstudio(String nombre) {

        ContentValues values = new ContentValues();

        values.put(NivelEstudioSQLiteHelper.COLUMN_NOMBRE, nombre);

        long insertId = database.insert(NivelEstudioSQLiteHelper.TABLE, null, values);

        Cursor cursor = database.query(NivelEstudioSQLiteHelper.TABLE, allColumns,
                NivelEstudioSQLiteHelper.COLUMN_ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();

        NivelEstudio_c newDNivelEstudio = cursorToNivelEstudio(cursor);

        cursor.close();

        return newDNivelEstudio;

    }

    public NivelEstudio_c findNivelEstudio(long nivelestudioID) {
        NivelEstudio_c c = null;
        String query = "SELECT * FROM " + NivelEstudioSQLiteHelper.TABLE + " WHERE _id = " + nivelestudioID;
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            c = cursorToNivelEstudio(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return c;
    }

    private NivelEstudio_c cursorToNivelEstudio(Cursor cursor) {
        return new NivelEstudio_c(cursor.getLong(0), cursor.getString(1));
    }

    public List<NivelEstudio_c> getAllNivelEstudio() {
        List<NivelEstudio_c> nivelestudio = new ArrayList<NivelEstudio_c>();
        Cursor cursor = database.query(NivelEstudioSQLiteHelper.TABLE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            nivelestudio.add(cursorToNivelEstudio(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return nivelestudio;
    }

    public void deleteNivelEstudio(long nivelestudioId) {
        database.delete(NivelEstudioSQLiteHelper.TABLE, NivelEstudioSQLiteHelper.COLUMN_ID
                + " = " + nivelestudioId, null);
    }

    public NivelEstudio_c updateNivelEstudio(NivelEstudio_c nivelestudio) {
        ContentValues values = new ContentValues();
        values.put(NivelEstudioSQLiteHelper.COLUMN_NOMBRE, nivelestudio.getNombrenivel());
        long insertId = database.update(NivelEstudioSQLiteHelper.TABLE, values, NivelEstudioSQLiteHelper.COLUMN_ID + " = " + nivelestudio.getIdnivel(), null);
        Cursor cursor = database.query(NivelEstudioSQLiteHelper.TABLE, allColumns,
                NivelEstudioSQLiteHelper.COLUMN_ID + "=" + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        nivelestudio = cursorToNivelEstudio(cursor);
        cursor.close();
        return nivelestudio;
    }

}
