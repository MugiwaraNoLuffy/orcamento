package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

/**
 * Created by redir on 6/15/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "orcamento.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.UserEntry.SQL_CREATE_USERS_TABLE);
        db.execSQL(DatabaseContract.Categoria.SQL_CREATE_CATEGORIA_TABLE);
        db.execSQL(DatabaseContract.Contas.SQL_CREATE_CONTAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Categoria.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Contas.TABLE_NAME);
        onCreate(db);
    }


    public long addUser(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        long tag_id = db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values);
        Log.e("Insert", "insert sucessfull");
        return tag_id;
    }

    public User getUserById(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.UserEntry.TABLE_NAME, // a. table
                new String[] {DatabaseContract.UserEntry.NAME, DatabaseContract.UserEntry.EMAIL, DatabaseContract.UserEntry.PASSWORD}, // b. column names to return
                "_ID = ?", // c. selections "where clause"
                new String[] {String.valueOf(id)}, // d. selections args "where values"
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        cursor.moveToFirst();
        User user = null;
        try{
            user = new User();
            user.setName(cursor.getString(0));
            user.setEmail(cursor.getString(1));
            user.setEncryptedPassword(cursor.getString(2));
            return user;
        }catch(CursorIndexOutOfBoundsException e){
            Log.e(LOG_TAG, "CursorIndexOutOfBoundsException");
        }

        return null;
    }

    public ArrayList<User> selectAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DatabaseContract.UserEntry.TABLE_NAME, // a. table
                new String[]{DatabaseContract.UserEntry.NAME, DatabaseContract.UserEntry.EMAIL, DatabaseContract.UserEntry.PASSWORD}, // b. column names to return
                null, // c. selections "where clause"
                null, // d. selections args "where values"
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        ArrayList<User> users = null;
        if( cursor.moveToFirst() ) {
            users = new ArrayList<>();
            String user1 = new User(cursor.getString(0), cursor.getString(1),cursor.getString(2)).toString();
            do{
                users.add(new User(cursor.getString(0), cursor.getString(1),cursor.getString(2)));
            }while(cursor.moveToNext());
        }
        return users;
    }

    public long Login(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.UserEntry.TABLE_NAME, // a. table
                new String[] {DatabaseContract.UserEntry._ID}, // b. column names to return
                "email = ?",
                new String[] {user.getContent().getAsString("email") },
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if(cursor.getCount() == 0){
            long i = addUser(user.getContent());
            if( i > 0){
                Log.e(LOG_TAG, "User added to local database");
            }
            return i;
        }
        cursor.moveToFirst();
        return cursor.getLong(0);
    }

}
