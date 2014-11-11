package com.raulh.miempleo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.raulh.miempleo_final.helpers.DepartamentoSQLiteHelper;
import com.raulh.miempleo_final.models.Departamento_c;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class DepartamentoDataSource {

    //Database Fields
    private SQLiteDatabase database;
    private DepartamentoSQLiteHelper dbHelper;
    private String[] allColumns = {DepartamentoSQLiteHelper.COLUMN_ID, DepartamentoSQLiteHelper.COLUMN_NOMBRE};

    public DepartamentoDataSource(Context context) {

        dbHelper = new DepartamentoSQLiteHelper(context);

    }

    public void open() {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public Departamento_c createDepartamento(String nombre) {

        ContentValues values = new ContentValues();

        values.put(DepartamentoSQLiteHelper.COLUMN_NOMBRE, nombre);

        long insertId = database.insert(DepartamentoSQLiteHelper.TABLE, null, values);

        Cursor cursor = database.query(DepartamentoSQLiteHelper.TABLE, allColumns,
                DepartamentoSQLiteHelper.COLUMN_ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();

        Departamento_c newDepartamento = cursorToDepartamento(cursor);

        cursor.close();

        return newDepartamento;

    }

    public Departamento_c findCargo(long departamentoID) {
        Departamento_c c = null;
        String query = "SELECT * FROM " + DepartamentoSQLiteHelper.TABLE + " WHERE _id = " + departamentoID;
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            c = cursorToDepartamento(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return c;
    }

    private Departamento_c cursorToDepartamento(Cursor cursor) {
        return new Departamento_c(cursor.getLong(0), cursor.getString(1));
    }

    public List<Departamento_c> getAllDepartamentos() {
        List<Departamento_c> departamentos = new ArrayList<Departamento_c>();
        Cursor cursor = database.query(DepartamentoSQLiteHelper.TABLE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            departamentos.add(cursorToDepartamento(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return departamentos;
    }

    public void deleteDepartament(long departamentoId) {

        try {
            database.delete(DepartamentoSQLiteHelper.TABLE, DepartamentoSQLiteHelper.COLUMN_ID + " = " + departamentoId, null);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public Departamento_c updateDepartament(Departamento_c departamento) {
        ContentValues values = new ContentValues();
        values.put(DepartamentoSQLiteHelper.COLUMN_NOMBRE, departamento.getNombredep());
        long insertId = database.update(DepartamentoSQLiteHelper.TABLE, values, DepartamentoSQLiteHelper.COLUMN_ID + " = " + departamento.getIddep(), null);
        Cursor cursor = database.query(DepartamentoSQLiteHelper.TABLE, allColumns,
                DepartamentoSQLiteHelper.COLUMN_ID + "=" + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        departamento = cursorToDepartamento(cursor);
        cursor.close();
        return departamento;
    }

}
