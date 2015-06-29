package com.apps.redir.orcamento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.apps.redir.orcamento.AuthActivities.LoginActivity;
import com.apps.redir.orcamento.AuthActivities.RegisterActivity;
import com.apps.redir.orcamento.Principal.PrincipalActivity;
import com.apps.redir.orcamento.SQLiteDatabase.LoginHelper;
import com.apps.redir.orcamento.SQLiteDatabase.User;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

        textView = (TextView) findViewById(R.id.textView);

        LoginHelper helper = new LoginHelper(this);
        ArrayList<User> users = helper.selectAll();


        if(users.size() == 0 ){
            Intent intent = new Intent(this, RegisterActivity.class);
            int requestCode = 1; // Or some number you choose
            startActivityForResult(intent, requestCode);
        }
        else
        {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        if(users!=null)
            intent.putParcelableArrayListExtra("users", users);
        Log.e("Main", String.valueOf(users.size()));
        int requestCode = 1;
        startActivityForResult(intent, requestCode);
        }

    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Collect data from the intent and use it
        try {
            String value = data.getStringExtra("status");
            Log.e("Retorno", value);
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);

            String restoredText = new LoginHelper(this).selectId((int) prefs.getLong("userid", 1)).getEmail();
            textView.setText(restoredText);
            Intent intent = new Intent(this, PrincipalActivity.class);
            startActivity(intent);
        }
        catch( NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Login login = new Login();

        //fragmentTransaction.add(R.id.login, login);
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
}
