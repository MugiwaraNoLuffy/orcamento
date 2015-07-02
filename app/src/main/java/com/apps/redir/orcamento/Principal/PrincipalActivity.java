package com.apps.redir.orcamento.Principal;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;

import com.apps.redir.orcamento.AuthActivities.LoginActivity;
import com.apps.redir.orcamento.MenuFragments.CadastroCategoriaFragment;
import com.apps.redir.orcamento.MenuFragments.CadastroContasFragment;
import com.apps.redir.orcamento.MenuFragments.PrincipalFragment;
import com.apps.redir.orcamento.R;
import com.apps.redir.orcamento.SQLiteDatabase.DatabaseHelper;

import java.util.HashMap;
import java.util.List;

public class PrincipalActivity extends ActionBarActivity {

    String LOG_TAG = PrincipalActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    HashMap<String, List<String>> items;
    List<String> headers;
    MenuExpandableListAdapter listAdapter;
    ExpandableListView expListView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Log.e(LOG_TAG, "toda vez que gira a porra da tela");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        int requestCode = 1;
        startActivityForResult(intent, requestCode);
        ActionBar actionBar = getSupportActionBar();
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_bar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setTitle("DOMestic ACCOunting");
        getSupportActionBar().setHomeButtonEnabled(true);
        MenuListItems menuItems = new MenuListItems();
        menuItems.populate();
        this.items = menuItems.getData();
        this.headers = menuItems.getHeaders();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expListView = (ExpandableListView) findViewById(R.id.listView);
        listAdapter = new MenuExpandableListAdapter(this, headers, this.items);
        Fragment fragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new PrincipalFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace( R.id.frame_container, fragment );
        transaction.commit();

        expListView.setAdapter(listAdapter);
        expListView.expandGroup(1);
        expListView.expandGroup(2);
        expListView.expandGroup(0);
        mDrawerLayout.openDrawer(Gravity.LEFT);
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                String nome = new DatabaseHelper(getApplicationContext()).getUserById((long) getSharedPreferences("myPref",MODE_PRIVATE).getLong("userid", 0)).getEmail();
                if(groupPosition == 1){
                    Fragment fragment;

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    switch (childPosition){
                        case 0:
                            transaction.replace( R.id.frame_container, new CadastroContasFragment() );
                            break;
                        case 1:
                            transaction.replace( R.id.frame_container, new CadastroCategoriaFragment() );
                            break;
                        default:
                            transaction.replace( R.id.frame_container, new CadastroContasFragment() );
                            break;
                    }
                    transaction.commit();
                }
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                SharedPreferences preferences = getSharedPreferences("myPref", MODE_PRIVATE);

                Log.e("","Item Clicked."+preferences.getLong("userid", 1));
                return false;
            }
        });
        //View action = View.inflate(getApplicationContext(),R.layout.custom_bar, null);
        ImageButton button2 = (ImageButton) findViewById(R.id.btn_slide);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lateral_menu, menu);
        //Login login = new Login();

        //fragmentTransaction.add(R.id.login, login);
        return super.onCreateOptionsMenu(menu);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Collect data from the intent and use it
        try {
            String value = data.getStringExtra("status");
            Log.e(LOG_TAG, value);
            SharedPreferences prefs = getSharedPreferences("myPref",MODE_PRIVATE);
            long i = (long)prefs.getLong("userid", 1);
            String restoredText = new DatabaseHelper(this).getUserById(i).getEmail();
            Log.e(LOG_TAG, restoredText);

        }
        catch( NullPointerException e){
            e.printStackTrace();
        }
    }
}
