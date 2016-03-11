package br.edu.ifce.engcomp.francis.diversidade.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.adapters.TextRecyclerViewAdapter;
import br.edu.ifce.engcomp.francis.diversidade.interfaces.RecyclerViewOnClickListenerHack;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightsFragment extends Fragment implements RecyclerViewOnClickListenerHack{
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<TextBlog> dataSource;

    public RightsFragment() {
        // Required empty public constructor
    }

    public ArrayList<TextBlog> generateDataSourceMock(){
        ArrayList<TextBlog> textBlogArrayList = new ArrayList<>();

        TextBlog teste1 = new TextBlog();
        teste1.setTitle("DEPOIS DE PERDER ESPOSA EM ACIDENTE, VIÚVA PROTESTA EM FAVOR DO CASAMENTO IGUALITÁRIO");
        teste1.setContent("A australiana Lara Ryan publicou nas redes sociais uma carta aberta em protesto contra as atribulações " +
                "por que passou durante a morte da esposa e as dificuldades para garantir o futuro de sua família porque " +
                "seu país não reconhece o casamento igualitário...");
        teste1.setSource("http://ladobi.uol.com.br/2016/03/viuva-australia/");
        teste1.setCategory("R");

        TextBlog teste2 = new TextBlog();
        teste2.setTitle("Destaque da Semana: Casamento gay em Portugal");
        teste2.setContent("Se levarmos em consideração que até 1982 a homossexualidade era considerada formalmente como crime " +
                "em terras lusas, não nos restam dúvidas de que a comemoração em torno da aprovação da proposta de lei " +
                "favorável ao casamento entre pessoas do mesmo sexo é mais do que merecida...");
        teste2.setSource("https://homomento.wordpress.com/2010/01/10/destaque-da-semana-casamento-gay-em-portugal/");
        teste2.setCategory("R");

        textBlogArrayList.add(teste1);
        textBlogArrayList.add(teste2);

        return textBlogArrayList;
    }

    @Override
    public void onCreate(Bundle savedInstace){
        super.onCreate(savedInstace);

        dataSource = generateDataSourceMock();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rights, container, false);

        TextRecyclerViewAdapter adapter = new TextRecyclerViewAdapter(getActivity().getApplicationContext(), dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) view.findViewById(R.id.rights_texts);

        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        String urlSource = dataSource.get(position).getSource();

        Uri uri = Uri.parse(urlSource);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
