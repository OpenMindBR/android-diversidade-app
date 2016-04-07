package br.edu.ifce.engcomp.francis.diversidade.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifce.engcomp.francis.diversidade.R;

/**
 * Created by Bolsista on 29/03/2016.
 */
public class InformationsFragment extends Fragment {

    public InformationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informations, container, false);

        return view;
    }
}
