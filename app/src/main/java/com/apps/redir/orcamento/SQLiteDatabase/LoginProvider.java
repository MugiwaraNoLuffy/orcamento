package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by redir on 6/15/2015.
 */
public class LoginProvider extends ContentProvider {

    /**
     * URIMatcher
     */
    public static final int LOGIN = 109;
    public static final int REGISTER = 101;

    private static final String DATABASE = "orcamento";
    private static final String CONTENT_AUTHORITY = "com.apps.redir.orcamento.SQLiteDatabse";
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY
            + "/" + DATABASE);
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + DATABASE;

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(CONTENT_AUTHORITY, DATABASE, LOGIN);
        sURIMatcher.addURI(CONTENT_AUTHORITY, DATABASE, REGISTER);
    }
    /**
     * URIMatcher
     */
    private LoginHelper helper;
    @Override
    public boolean onCreate() {
        helper = new LoginHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseContract.UserEntry.TABLE_NAME);
        int uriType = sURIMatcher.match(uri);
        switch (uriType){
            case LOGIN:
                break;
            case REGISTER:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(helper.getWritableDatabase(),projection,
                selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }



}
