package com.apps.redir.orcamento.AuthActivities;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.apps.redir.orcamento.SQLiteDatabase.LoginHelper;
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


public class RegisterActivity extends ActionBarActivity {
    private static EditText name;
    private static EditText email;
    private static EditText passw;
    private static EditText passw2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.editName);
        email = (EditText) findViewById(R.id.editEmail);
        passw = (EditText) findViewById(R.id.editPass);
        passw2 = (EditText) findViewById(R.id.editPass2);
        Button button = (Button) findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 0;
                if(name.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Field name is mandatory!", Toast.LENGTH_LONG).show();
                    error = error+1;
                }
                if (email.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Field e-mail is mandatory!", Toast.LENGTH_LONG).show();
                    error = error+1;
                }
                if(passw.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Field password is mandatory!", Toast.LENGTH_LONG).show();
                    error = error+1;
                }
                if( passw.getText().toString().length() < 8){
                    Toast.makeText(getBaseContext(), "Password too short!", Toast.LENGTH_LONG).show();
                    error = error+1;
                }
                if(passw2.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Re-type the password!", Toast.LENGTH_LONG).show();
                    error = error+1;
                }
                if(!passw2.getText().toString().equals(passw.getText().toString())){
                    Toast.makeText(getBaseContext(), "The passwords does not match!", Toast.LENGTH_LONG).show();
                    error = error+1;
                }
                if(error > 0){
                    // todo show error on the fields

                }
                else{
                    RegisterTask registerTask = new RegisterTask();

                    registerTask.execute(name.getText().toString(),
                                        email.getText().toString(),
                                        passw.getText().toString() );

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public class RegisterTask extends AsyncTask<String, Void, User> {

        private final String LOG_TAG = RegisterTask.class.getSimpleName();
        private String result = "";
        byte[] messageencrypted;
        private ProgressDialog progressDialog;

        public RegisterTask() {

        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(RegisterActivity.this, "Autenticando", "Contactando o servidor, por favor, aguarde alguns instantes.", true, false);
        }
        @Override
        protected User doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://www.dossrosa.com/orcamento/scripts/register.php");
            User user = new User();
            user.setName(params[0]);
            user.setEmail(params[1]);
            user.setPassword(params[2]);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("name", user.getName());
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
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
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
                if(jObject.getString("status").equals("Sucess")) {
                    user.setToken(jObject.getString("token"));
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
        public void onPostExecute(User user){
            LoginHelper helper = new LoginHelper(getBaseContext());

            if(helper.insert(user.getContent()) > 0)
            {
                Log.e("Register", "User added to the database");
                Intent intent = new Intent();
                intent.putExtra("status", "logged");
                setResult(RESULT_OK, intent);
                progressDialog.dismiss();
                finish();
            }
            else{
                Log.e("Register", "Could not insert in the database");
                Intent intent = new Intent();
                intent.putExtra("status", "Could not insert in the local database!");
                setResult(RESULT_OK, intent);
                progressDialog.dismiss();
                finish();
            }

        }

    }

}
