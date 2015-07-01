package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by redir on 7/1/2015.
 */
public class UsersTable {
    public String LOG_TAG = UsersTable.class.getSimpleName();
    SQLiteDatabase db;

    public UsersTable(SQLiteDatabase db){
        this.db = db;
    }

    public void onCreate(){
        this.db.execSQL(DatabaseContract.UserEntry.SQL_CREATE_USERS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UserEntry.TABLE_NAME);
        onCreate();
    }

    public long addUser(ContentValues values)
    {
        long tag_id = db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values);
        Log.e("Insert", "insert sucessfull");
        return tag_id;
    }

    public User getUserByID(long id){
        Cursor cursor = db.query(DatabaseContract.UserEntry.TABLE_NAME, // a. table
                new String[] {DatabaseContract.UserEntry.NAME, DatabaseContract.UserEntry.EMAIL, DatabaseContract.UserEntry.PASSWORD}, // b. column names to return
                "_ID = ?", // c. selections "where clause"
                new String[] {String.valueOf(0)}, // d. selections args "where values"
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

    public ArrayList<User> getAllUsers()
    {
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
