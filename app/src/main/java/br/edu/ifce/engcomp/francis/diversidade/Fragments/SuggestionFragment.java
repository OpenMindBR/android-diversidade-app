package br.edu.ifce.engcomp.francis.diversidade.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import br.edu.ifce.engcomp.francis.diversidade.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestionFragment extends Fragment {


    public SuggestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);

        Button enviarButton = (Button) view.findViewById(R.id.enviarButton);
        enviarButton.setOnClickListener(this.makeRegisterButtonClickListener());

        return view;
    }

    private View.OnClickListener makeRegisterButtonClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Agradecemos pela sugestão! Em breve, entraremos em contato.", Toast.LENGTH_LONG).show();
            }
        };
    }
}
