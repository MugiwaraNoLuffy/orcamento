package com.apps.redir.orcamento.Principal;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.apps.redir.orcamento.MenuFragments.CadastroCategoriaFragment;
import com.apps.redir.orcamento.MenuFragments.CadastroContasFragment;
import com.apps.redir.orcamento.MenuFragments.PrincipalFragment;
import com.apps.redir.orcamento.MenuFragments.ReportFragment;
import com.apps.redir.orcamento.R;
import com.apps.redir.orcamento.SQLiteDatabase.LoginHelper;

import java.util.HashMap;
import java.util.List;

public class PrincipalActivity extends ActionBarActivity {
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
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String nome = new LoginHelper(getApplicationContext()).selectId((int) getPreferences(MODE_PRIVATE).getLong("userid", 1)).getEmail();
                if(groupPosition == 0){
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
                Log.e("","Item Clicked."+nome);
                return false;
            }
        });
    }

}
