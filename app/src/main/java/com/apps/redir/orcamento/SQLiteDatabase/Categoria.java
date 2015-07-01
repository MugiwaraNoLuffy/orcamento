package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

/**
 * Created by redir on 6/30/2015.
 */
public class Categoria {
    private String descricao;
    private float limite;

    public Categoria(String descricao, float limite){
        this.descricao = descricao;
        this.limite = limite;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getLimite() {
        return limite;
    }

    public void setLimite(float limite) {
        this.limite = limite;
    }

    public ContentValues getContent()
    {
        ContentValues value = new ContentValues();
        value.put(DatabaseContract.Categoria.DESCRICAO, descricao);
        value.put(DatabaseContract.Categoria.LIMITE, limite);
        return value;
    }
}
