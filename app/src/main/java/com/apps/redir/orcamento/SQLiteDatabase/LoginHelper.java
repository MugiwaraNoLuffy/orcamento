package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.apps.redir.orcamento.SQLiteDatabase.DatabaseContract.UserEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by redir on 6/15/2015.
 */
public class LoginHelper extends SQLiteOpenHelper
{
    private final String LOG_TAG = LoginHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "orcamento.db";

    public LoginHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("+
                UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                UserEntry.NAME + " TEXT NOT NULL, "+
                UserEntry.EMAIL + " TEXT NOT NULL UNIQUE, "+
                UserEntry.PASSWORD + " TEXT NOT NULL, "+
                UserEntry.TOKEN + " TEXT NOT NULL );";
       db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);

        onCreate(db);
    }

    public long insert(ContentValues values) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("Insert", "Inserting");
        long tag_id = db.insert(UserEntry.TABLE_NAME, null, values);
        Log.e("Insert", "insert sucessfull");
        return tag_id;
    }

    public User selectId(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UserEntry.TABLE_NAME, // a. table
                new String[] {UserEntry.NAME, UserEntry.EMAIL, UserEntry.PASSWORD}, // b. column names to return
                "_ID = ?", // c. selections "where clause"
                new String[] {String.valueOf(id)}, // d. selections args "where values"
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        cursor.moveToFirst();
        User user = new User();
        user.setName(cursor.getString(0));
        user.setEmail(cursor.getString(1));
        user.setEncryptedPassword(cursor.getString(2));
        return user;
    }

    public ArrayList<User> selectAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UserEntry.TABLE_NAME, // a. table
                new String[]{UserEntry.NAME, UserEntry.EMAIL, UserEntry.PASSWORD}, // b. column names to return
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
        Cursor cursor = db.query(UserEntry.TABLE_NAME, // a. table
                new String[] {UserEntry._ID}, // b. column names to return
                "email = ?",
                new String[] {user.getContent().getAsString("email") },
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if(cursor.getCount() == 0){
            long i = insert(user.getContent());
            if( i > 0){
                Log.e(LOG_TAG, "User added to local database");
            }
            return i;
        }
        cursor.moveToFirst();
        return cursor.getLong(0);
    }

}
