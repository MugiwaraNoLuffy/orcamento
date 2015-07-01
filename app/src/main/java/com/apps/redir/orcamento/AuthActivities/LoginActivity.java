package com.apps.redir.orcamento.AuthActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.redir.orcamento.R;
import com.apps.redir.orcamento.SQLiteDatabase.DatabaseHelper;
import com.apps.redir.orcamento.SQLiteDatabase.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class LoginActivity extends ActionBarActivity {
    String LOG_TAG = LoginActivity.class.getSimpleName();
    private static EditText email;
    private static EditText passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseHelper helper = new DatabaseHelper(this);
        final ArrayList<User> users;
        email = (EditText) findViewById(R.id.editEmail);
        passw = (EditText) findViewById(R.id.editPass);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        User user = null;
        long id = 0;
        id = prefs.getLong("userid", 1);
        if(id > 0 ){
            user = helper.getUserById(id);
            if(user != null) {
                Log.e(LOG_TAG, user.getContent().toString());
                email.setText(user.getEmail().toString());
                passw.setText(user.getPassword().toString());
                LoginTask registerTask = new LoginTask();
                registerTask.execute(email.getText().toString(), passw.getText().toString());
            }
        }
        if(user == null) {
            users = helper.selectAll();

            try {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Select one to log in");
                final String[] items = new String[users.size()];
                int i = 0;
                for (User u : users) {
                    items[i] = u.getEmail();
                    i++;
                }

                // alertDialog.setMessage(users.get(0).toString());
                alertDialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        email.setText(items[item]);
                        passw.setText(users.get(item).getPassword());
                        switch (item) {
                            case 0:
                                // Your code when first option seletced

                                break;
                            case 1:
                                // Your code when 2nd  option seletced

                                break;
                            case 2:
                                // Your code when 3rd option seletced
                                break;
                            case 3:
                                // Your code when 4th  option seletced
                                break;

                        }

                    }
                });
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Add your code for the button here.
                    }
                });
                alertDialog.create();
                alertDialog.show();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }



            Button button = (Button) findViewById(R.id.buttonLogin);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int error = 0;
                    if (email.getText().toString().equals("")) {
                        Toast.makeText(getBaseContext(), "Field e-mail is mandatory!", Toast.LENGTH_LONG).show();
                        error = error + 1;
                    }
                    if (passw.getText().toString().equals("")) {
                        Toast.makeText(getBaseContext(), "Field password is mandatory!", Toast.LENGTH_LONG).show();
                        error = error + 1;
                    }
                    if (error > 0) {
                        // todo show error on the fields

                    } else {
                        LoginTask registerTask = new LoginTask();
                        registerTask.execute(email.getText().toString(), passw.getText().toString());

                    }

                }
            });
            Button registro = (Button) findViewById(R.id.buttonSignUp);
            registro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class LoginTask extends AsyncTask<String, Void, User> {

        private final String LOG_TAG = LoginTask.class.getSimpleName();
        private String result = "";
        private ProgressDialog progressDialog;

        public LoginTask() {

        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Autenticando", "Contactando o servidor, por favor, aguarde alguns instantes.", true, false);
        }

        @Override
        protected User doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://www.dossrosa.com/orcamento/scripts/login.php");
            User user = new User();
            user.setEmail(params[0]);
            user.setPassword(params[1]);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("email", user.getEmail());
                jsonObject.accumulate("password", user.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String json = jsonObject.toString();

            try {

                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = httpclient.execute(httpPost);
                InputStream inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);

                }
                else
                    result = new JSONObject().accumulate("status", "Invalid input").toString();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e(LOG_TAG, result);
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
                if(jObject.getString("status").equals("Success")) {
                    user.setToken(jObject.getString("token"));
                    user.setName(jObject.getString("name"));
                }
                else{
                    user.setToken(jObject.getString("status"));
                }
                return user;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        private String convertInputStreamToString(InputStream inputStream) throws IOException{
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        @Override
        protected void onPostExecute(User user){
            //super.onPostExecute( );
            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            if(user != null && user.getToken() != "Could not login" ) {
                long i = helper.Login(user);
                if (i > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("status", "Logged");
                    setResult(RESULT_OK, intent);
                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                    editor.putLong("userid", i);
                    Log.e(LOG_TAG, String.valueOf(i));
                    editor.apply();
                    finish();
                }
            }
            progressDialog.dismiss();
        }




    }

}
