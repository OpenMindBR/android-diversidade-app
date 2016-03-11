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
public class DiscoveringFragment extends Fragment implements RecyclerViewOnClickListenerHack{
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<TextBlog> dataSource;

    public DiscoveringFragment() {
        // Required empty public constructor
    }

    public ArrayList<TextBlog> generateDataSourceMock(){
        ArrayList<TextBlog> textBlogArrayList = new ArrayList<>();

        TextBlog teste1 = new TextBlog();
        teste1.setTitle("ESTÁ NA HORA DE ACABAR COM O BINARISMO DE GÊNERO");
        teste1.setContent("Justin Hubbel explica em quadrinhos o que é binarismo de gênero, " +
                "quais são os males que ele causa e por que esse conceito está ultrapassado...");
        teste1.setSource("http://ladobi.uol.com.br/2016/03/binarismo-genero/");
        teste1.setCategory("D");

        TextBlog teste2 = new TextBlog();
        teste2.setTitle("Os 39 anos de um gay. Crise da meia idade?");
        teste2.setContent("Depois de um longo e feliz “frio de verão” em São Paulo, com direito a muita chuva (o que está acontecendo hoje), volto a escrever no Blog MVG. Desde 2011, quando o iniciei, " +
                "talvez tenha sido o período mais longo sem passar por aqui. Acontece que eu descobri um " +
                "aplicativo danado chamado Smule...");
        teste2.setSource("http://minhavidagay.com.br/2016/03/10/gay-39-anos/");
        teste2.setCategory("D");

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
        View view = inflater.inflate(R.layout.fragment_discovering, container, false);

        TextRecyclerViewAdapter adapter = new TextRecyclerViewAdapter(getActivity().getApplicationContext(), dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) view.findViewById(R.id.discovering_texts);

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
