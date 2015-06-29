package com.apps.redir.orcamento.Principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by redir on 6/29/2015.
 */
public class MenuListItems {

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public MenuListItems(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
    }
    public void populate(){
        listDataHeader.add("Cadastro");
        listDataHeader.add("Relatórios");
        List<String> cadastro = new ArrayList<String>();
        cadastro.add("Lançamentos");
        cadastro.add("Contas");
        cadastro.add("Limites");
        List<String> relatorios = new ArrayList<String>();
        relatorios.add("Entradas");
        relatorios.add("Saídas");
        relatorios.add("Limites");
        relatorios.add("Geral");
        listDataChild.put(listDataHeader.get(0), cadastro); // Header, Child data
        listDataChild.put(listDataHeader.get(1), relatorios);
    }

    public HashMap<String, List<String>> getData(){
        return listDataChild;
    }
    public List<String> getHeaders(){
        return listDataHeader;
    }
}
