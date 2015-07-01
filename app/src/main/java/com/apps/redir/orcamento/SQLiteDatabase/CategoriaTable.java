package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.apps.redir.orcamento.R;

import java.util.ArrayList;

public class CategoriaTable {
    private final String LOG_TAG = CategoriaTable.class.getSimpleName();
    SQLiteDatabase db;

    public CategoriaTable(SQLiteDatabase db) {
        this.db = db;
    }

    public void onCreate(){
        this.db.execSQL(DatabaseContract.Categoria.SQL_CREATE_CATEGORIA_TABLE);
    }

    public long addCategoria(ContentValues values) {
        long tag_id = -1;
        try {
            tag_id = db.insert(DatabaseContract.Categoria.TABLE_NAME, null, values);
        }
        catch(SQLiteConstraintException e){
            Log.v(LOG_TAG, "Already exists");
        }

        return tag_id;
    }

    public ArrayList<Categoria> selectAll(){
        Cursor cursor = db.query(DatabaseContract.Categoria.TABLE_NAME, // a. table
                new String[]{DatabaseContract.Categoria.DESCRICAO, DatabaseContract.Categoria.LIMITE}, // b. column names to return
                null, // c. selections "where clause"
                null, // d. selections args "where values"
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        ArrayList<Categoria> categorias = null;
        if( cursor.moveToFirst() ) {
            categorias = new ArrayList<>();
            do{
                categorias.add(new Categoria(cursor.getString(0), cursor.getFloat(1)));
            }while(cursor.moveToNext());
        }
        return categorias;
    }
}
