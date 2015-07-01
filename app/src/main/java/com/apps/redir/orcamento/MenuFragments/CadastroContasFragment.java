package com.apps.redir.orcamento.MenuFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.apps.redir.orcamento.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CadastroContasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CadastroContasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroContasFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CadastroContasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroContasFragment newInstance(String param1, String param2) {
        CadastroContasFragment fragment = new CadastroContasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CadastroContasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro_contas, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public JSONObject getJSON(){
        JSONObject json = new JSONObject();
        EditText editCategoria = (EditText) getView().findViewById(R.id.editConta);
        EditText editLimite = (EditText) getView().findViewById(R.id.editCategoria);
        try {
            json.accumulate("conta", editCategoria.getText());
            json.accumulate("categoria", editLimite.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
