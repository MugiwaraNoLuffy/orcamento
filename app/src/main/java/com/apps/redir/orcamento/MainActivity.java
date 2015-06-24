package com.apps.redir.orcamento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.apps.redir.orcamento.SQLiteDatabase.LoginHelper;
import com.apps.redir.orcamento.SQLiteDatabase.User;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RegisterTask registerTask = new RegisterTask();

        //registerTask.execute();
        Intent intent = new Intent(this, RegisterActivity.class);
        int requestCode = 1; // Or some number you choose
        startActivityForResult(intent, requestCode);
        //startActivity(intent);

        textView = (TextView) findViewById(R.id.textView);

        LoginHelper helper = new LoginHelper(this);
/*        User user = new User();
        user.setName("redi");
        user.setEmail("redi@gmail.com");
        user.setPassword("123456789");
        helper.insert(user.getContent());

        ArrayList<User> users = helper.selectAll();

        Log.e("Login in", users.get(0).toString());
        Log.e("Login in", users.get(1).toString());
        Log.e("Login in", users.get(2).toString());*/
    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Collect data from the intent and use it
        String value = data.getStringExtra("someValue");
        Log.e("Retorno", value);
        textView.setText(value);
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
