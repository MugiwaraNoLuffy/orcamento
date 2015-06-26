package com.apps.redir.orcamento.MenuActivitiesTest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.apps.redir.orcamento.R;

import java.util.ArrayList;


public class MenuActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ArrayList<MenuOrcItem> menu = new ArrayList<MenuOrcItem>();
        MenuOrcItem cadastroContaBancaria = new MenuOrcItem("Cadastro Conta Bancaria");
        MenuOrcItem cadastro2 = new MenuOrcItem("Cadastro Conta Bancaria");
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), listView, list);
        menu.add(cadastro2);
        menu.add(cadastroContaBancaria);
        ListView listView = (ListView) findViewById(R.id.listView);
        MenuItemAdapter adapter = new MenuItemAdapter(this, menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
