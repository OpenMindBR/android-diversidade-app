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
public class HealthFragment extends Fragment implements RecyclerViewOnClickListenerHack{
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<TextBlog> dataSource;

    public HealthFragment() {
        // Required empty public constructor
    }

    public ArrayList<TextBlog> generateDataSourceMock(){
        ArrayList<TextBlog> textBlogArrayList = new ArrayList<>();

        TextBlog teste1 = new TextBlog();
        teste1.setTitle("Governo lança política de saúde para público LGBT no SUS");
        teste1.setContent("O Ministério da Saúde aproveitou a 14º Conferência Nacional de Saúde para disparar ações voltadas ao público " +
                "LGBT. Uma delas foi a assinatura pelo ministro da Saúde, Alexandre Padilha, de uma portaria que institui a Política " +
                "Nacional de Saúde Integral...");
        teste1.setSource("http://www1.folha.uol.com.br/equilibrioesaude/2011/12/1015086-governo-lanca-politica-de-saude-para-" +
                "publico-lgbt-no-sus.shtml");
        teste1.setCategory("H");

        TextBlog teste2 = new TextBlog();
        teste2.setTitle("MINISTÉRIO LANÇA CAMPANHA VOLTADA À SAÚDE DA POPULAÇÃO TRANS");
        teste2.setContent("Foi lançada nesta quarta-feira (27), no Ministério da Saúde, em Brasília, a campanha “Cuidar bem da saúde de cada um. " +
                "Faz bem para todos. Faz bem para o Brasil”, com foco na saúde integral, atendimento humanizado e respeito para as travestis, " +
                "mulheres...");
        teste2.setSource("http://portalsaude.saude.gov.br/index.php/lgbt-noticias/21905-ministerio-lanca-campanha-voltada-a-saude-da-populacao-trans");
        teste2.setCategory("H");

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
        View view = inflater.inflate(R.layout.fragment_health, container, false);

        TextRecyclerViewAdapter adapter = new TextRecyclerViewAdapter(getActivity().getApplicationContext(), dataSource);
        adapter.setRecycleViewOnClickListenerHack(this);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) view.findViewById(R.id.health_texts);

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
