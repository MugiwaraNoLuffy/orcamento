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
        listDataHeader.add("Lançamento");
        listDataHeader.add("Cadastro");
        listDataHeader.add("Relatório");

        List<String> cadastro = new ArrayList<String>();
        cadastro.add("Contas");
        cadastro.add("Categorias");

        List<String> relatorios = new ArrayList<String>();
        relatorios.add("Entradas");
        relatorios.add("Pagamentos");
        relatorios.add("Parcelamentos");
        relatorios.add("Geral");

        List<String> lancamentos = new ArrayList<String>();
        lancamentos.add("Entradas");
        lancamentos.add("Pagamentos");
        lancamentos.add("Transferencias");
        lancamentos.add("Investimentos");
        lancamentos.add("Parcelamentos");

        listDataChild.put(listDataHeader.get(0), lancamentos); // Header, Child data
        listDataChild.put(listDataHeader.get(1), cadastro);
        listDataChild.put(listDataHeader.get(2), relatorios);
    }

    public HashMap<String, List<String>> getData(){
        return listDataChild;
    }
    public List<String> getHeaders(){
        return listDataHeader;
    }
}
