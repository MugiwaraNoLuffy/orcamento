package com.apps.redir.orcamento.SQLiteDatabase;

import android.content.ContentValues;
import java.util.Date;

/**
 * Created by redir on 6/16/2015.
 */
public class User extends Object
{
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
}
