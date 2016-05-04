package br.edu.ifce.engcomp.francis.diversidade.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;

/**
 * Created by Bolsista on 29/03/2016.
 */
public class InformationsFragment extends Fragment {
    Nucleus nucleus;
    TextView responsavel;
    TextView telefone;
    TextView email;
    TextView site;
    TextView logradouro;
    TextView referencia;
    TextView bairroCidade;
    TextView estadoPais;

    public InformationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informations, container, false);

        responsavel = (TextView) view.findViewById(R.id.information_responsible_text_view);
        telefone  = (TextView) view.findViewById(R.id.information_phone_text_view);
        email  = (TextView) view.findViewById(R.id.information_email_text_view);
        site  = (TextView) view.findViewById(R.id.information_site_text_view);
        logradouro  = (TextView) view.findViewById(R.id.street_text_view);
        referencia  = (TextView) view.findViewById(R.id.reference_text_view);
        bairroCidade  = (TextView) view.findViewById(R.id.neighborhood_and_city_text_view);
        estadoPais  = (TextView) view.findViewById(R.id.state_and_country_text_view);

        Intent currentIntent = getActivity().getIntent();
        nucleus = (Nucleus) currentIntent.getSerializableExtra("INFOS_NUCLEUS");

        logradouro.setText(nucleus.getAddress().getLogradouro());
        bairroCidade.setText(nucleus.getAddress().getNeighborhood() + ", " + nucleus.getAddress().getCity());
        estadoPais.setText(nucleus.getAddress().getState() + " - Brasil");

        return view;
    }

}
