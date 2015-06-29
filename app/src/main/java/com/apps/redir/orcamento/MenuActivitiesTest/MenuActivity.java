package com.apps.redir.orcamento.MenuActivitiesTest;


import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.apps.redir.orcamento.MenuFragments.CadastroFragment;
import com.apps.redir.orcamento.MenuFragments.ReportFragment;
import com.apps.redir.orcamento.R;

import java.util.ArrayList;


public class MenuActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, CadastroFragment.OnFragmentInteractionListener, ReportFragment.OnFragmentInteractionListener {
    private DrawerLayout mDrawerLayout;
    ListView listView;
    ArrayList<MenuOrcItem> menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu = new ArrayList<MenuOrcItem>();

        MenuOrcItem cadastro = new MenuOrcItem("Cadastro");
        cadastro.setClickable(false);
        MenuOrcItem cadastroContas = new MenuOrcItem("   Contas");
        MenuOrcItem cadastroCategorias = new MenuOrcItem("   Categorias");
        MenuOrcItem cadastroLimites = new MenuOrcItem("   Limites");
        MenuOrcItem cadastroLancamentos = new MenuOrcItem("   Lançamentos");

        MenuOrcItem relatorios = new MenuOrcItem("Relatorios");
        relatorios.setClickable(false);
        MenuOrcItem relatorioGeral = new MenuOrcItem("   Relatorio Geral");
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), listView, list);
        menu.add(cadastro);
        menu.add(cadastroCategorias);
        menu.add(cadastroContas);
        menu.add(cadastroLancamentos);
        menu.add(cadastroLimites);
        menu.add(relatorios);
        menu.add(relatorioGeral);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.listView);
        MenuItemAdapter adapter = new MenuItemAdapter(this, menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
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
        displayView(position);
    }
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Log.e("display", "its getting here!");
        switch (position)
        {
            case 1:
                fragment = new CadastroFragment();
                break;
            case 2:
            default:
                fragment = new ReportFragment();
                break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            }
            else
            {
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            }

            listView.setItemChecked(position, true);
            listView.setSelection(position);
            setTitle(menu.get(position).getName());
            mDrawerLayout.closeDrawer(listView);
        }
        else
        {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
