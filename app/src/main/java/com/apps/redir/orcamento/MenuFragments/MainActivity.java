package com.apps.redir.orcamento.MenuFragments;


import android.app.ActionBar;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.apps.redir.orcamento.R;

public class MainActivity extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener, CadastroFragment.OnFragmentInteractionListener, ReportFragment.OnFragmentInteractionListener {
    ViewPager Tab;
    ViewPagerAdapter TabAdapter;
    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main2);
        Tab = (ViewPager)findViewById(R.id.pager);
        TabAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Tab.setAdapter(TabAdapter);
/*        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e("Main", String.valueOf(position));
                        actionBar = getSupportActionBar();
                        actionBar.setSelectedNavigationItem(position);
                    }
                });*/



        actionBar = getSupportActionBar();
        actionBar.addTab(actionBar.newTab().setText("Cadastro").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Relatorios").setTabListener(this));

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        actionBar = getSupportActionBar();
        actionBar.setSelectedNavigationItem(tab.getPosition());
        Tab.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
