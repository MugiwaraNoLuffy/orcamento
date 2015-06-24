package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by redir on 6/15/2015.
 */
public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "com.apps.redir.orcamento";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_LOGIN = "user";

    public static final class UserEntry implements BaseColumns{

        public static final String TABLE_NAME = "user";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TOKEN = "token";


    }
}
