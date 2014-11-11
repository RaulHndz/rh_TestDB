package com.raulh.miempleo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.raulh.miempleo_final.helpers.TipoDocSQLiteHelper;
import com.raulh.miempleo_final.models.TipoDoc_c;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class TipoDocDataSource {

    //Database Fields
    private SQLiteDatabase database;
    private TipoDocSQLiteHelper dbHelper;
    private String[] allColumns = {TipoDocSQLiteHelper.COLUMN_ID, TipoDocSQLiteHelper.COLUMN_NOMBRE};

    public TipoDocDataSource(Context context) {

        dbHelper = new TipoDocSQLiteHelper(context);

    }

    public void open() {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public TipoDoc_c createTipoDoc(String nombre) {

        ContentValues values = new ContentValues();

        values.put(TipoDocSQLiteHelper.COLUMN_NOMBRE, nombre);

        long insertId = database.insert(TipoDocSQLiteHelper.TABLE, null, values);

        Cursor cursor = database.query(TipoDocSQLiteHelper.TABLE, allColumns,
                TipoDocSQLiteHelper.COLUMN_ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();

        TipoDoc_c newDepartamento = cursorToTipoDoc(cursor);

        cursor.close();

        return newDepartamento;

    }

    public TipoDoc_c findTipoDoc(long tipodocID) {
        TipoDoc_c c = null;
        String query = "SELECT * FROM " + TipoDocSQLiteHelper.TABLE + " WHERE _id = " + tipodocID;
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            c = cursorToTipoDoc(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return c;
    }

    private TipoDoc_c cursorToTipoDoc(Cursor cursor) {
        return new TipoDoc_c(cursor.getLong(0), cursor.getString(1));
    }

    public List<TipoDoc_c> getAllTipoDoc() {
        List<TipoDoc_c> tipodoc = new ArrayList<TipoDoc_c>();
        Cursor cursor = database.query(TipoDocSQLiteHelper.TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tipodoc.add(cursorToTipoDoc(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tipodoc;
    }

    public void deleteTipoDoc(long tipodocId) {
        database.delete(TipoDocSQLiteHelper.TABLE, TipoDocSQLiteHelper.COLUMN_ID
                + " = " + tipodocId, null);
    }

    public TipoDoc_c updatTipoDoc(TipoDoc_c tipodoc) {
        ContentValues values = new ContentValues();
        values.put(TipoDocSQLiteHelper.COLUMN_NOMBRE, tipodoc.getNombretipo());
        long insertId = database.update(TipoDocSQLiteHelper.TABLE, values, TipoDocSQLiteHelper.COLUMN_ID + " = " + tipodoc.getIdtipo(), null);
        Cursor cursor = database.query(TipoDocSQLiteHelper.TABLE, allColumns,
                TipoDocSQLiteHelper.COLUMN_ID + "=" + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        tipodoc = cursorToTipoDoc(cursor);
        cursor.close();
        return tipodoc;
    }

}
