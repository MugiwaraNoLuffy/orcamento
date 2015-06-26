package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by redir on 6/16/2015.
 */
public class User implements Parcelable {

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String LAST_LOGIN = "last_login";
    private static String TOKEN = "token";

    public String name;
    public String email;
    private String password;
    public Date last_login;
    private String token;

    public User(Parcel in)
    {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        token = in.readString();
    }
    public User()
    {

    }

    public User(String name, String email, String password)
    {
        setName(name);
        setEmail(email);
        setPassword(password);
    }
    public void setName(String name){
        this.name = name;
        // TODO verificar nome
    }

    public void setEmail(String email){
        this.email = email;
        // TODO testar email
    }

    public void setEncryptedPassword(String senha){
        this.password = senha;
    }

    public void setPassword(String senha) {
        // TODO testar forca senha
        /*try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            this.password = hexString.toString();
            Log.e("User", senha);
            Log.e("User", password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        password = senha;
    }

    public ContentValues getContent(){
        ContentValues value = new ContentValues();
        value.put(NAME, name);
        value.put(EMAIL, email);
        value.put(PASSWORD, password);
        value.put(TOKEN, token);
        return value;
    }

    public String toString(){
        return NAME+": "+name+"\t"+EMAIL+": "+email+".";
    }

    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(token);
    }

    public String getToken(){
        return token;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
