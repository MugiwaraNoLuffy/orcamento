package com.apps.redir.orcamento.SQLiteDatabase;

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

    public static final class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "user";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TOKEN = "token";

        static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NAME + " TEXT NOT NULL, "+
                EMAIL + " TEXT NOT NULL UNIQUE, "+
                PASSWORD + " TEXT NOT NULL, "+
                TOKEN + " TEXT NOT NULL );";
    }

    public static final class Categoria implements BaseColumns{
        public static final String TABLE_NAME = "categorias";
        public static final String DESCRICAO = "descricao";
        public static final String LIMITE = "limite_max_mensal";
        static final String SQL_CREATE_CATEGORIA_TABLE = "CREATE TABLE " + TABLE_NAME + " ("+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DESCRICAO + " VARCHAR(30) NOT NULL UNIQUE, "+
                LIMITE + " FLOAT);";
    }

    public static final class Contas implements BaseColumns{
        public static final String TABLE_NAME = "contas";
        public static final String NAME = "nome";
        public static final String ID_CATEGORIA = "idcategoria";
        static final String SQL_CREATE_CONTAS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NAME + " VARCHAR(30) NOT NULL UNIQUE, "+
                ID_CATEGORIA + " INTEGER," +
                "FOREIGN KEY("+ ID_CATEGORIA +") REFERENCES "+Categoria.TABLE_NAME+"("+Categoria._ID+"));";
    }

    public static final class Lancamentos implements BaseColumns{
        public static final String TABLE_NAME = "lancamentos";
        public static final String CONTA_ENTRADA = "id_conta_entrada";
        public static final String CONTA_SAIDA = "id_conta_saida";
        public static final String DESCRICAO = "descricao";
        public static final String VALOR = "valor";
        public static final String DATA = "data";
        static final String SQL_CREATE_LANCAMENTOS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                CONTA_ENTRADA + " INTEGER, "+
                CONTA_SAIDA + " INTEGER," +
                DESCRICAO + " VARCHAR(30),"+
                VALOR + " FLOAT,"+
                DATA + " DATE,"+
                "FOREIGN KEY("+CONTA_ENTRADA+","+ CONTA_SAIDA +") REFERENCES "+Contas.TABLE_NAME+"("+Contas._ID+","+Contas._ID+"));";
    }
}
