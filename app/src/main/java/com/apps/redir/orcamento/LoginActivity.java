package com.apps.redir.orcamento;

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


public class LoginActivity extends ActionBarActivity {
    private static EditText email;
    private static EditText passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.editEmail);
        passw = (EditText) findViewById(R.id.editPass);
        Button button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 0;
                if (email.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Field e-mail is mandatory!", Toast.LENGTH_LONG).show();
                    error = error+1;
                }
                if(passw.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Field password is mandatory!", Toast.LENGTH_LONG).show();
                    error = error+1;
                }
                if(error > 0){
                    // todo show error on the fields

                }
                else{
                    LoginTask registerTask = new LoginTask();
                    registerTask.execute(email.getText().toString(), passw.getText().toString() );

                }

            }
        });
        Button register = (Button) findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(null, RegisterActivity.class);
                startActivity(intent);
            }
        });

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

        public LoginTask() {

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
                    result = "Did not work!";

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e(LOG_TAG, result);

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




    }

}
