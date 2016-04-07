package br.edu.ifce.engcomp.francis.diversidade.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.activities.DetailDeveloperActivity;
import br.edu.ifce.engcomp.francis.diversidade.adapters.AboutRecyclerViewAdapter;
import br.edu.ifce.engcomp.francis.diversidade.adapters.TextRecyclerViewAdapter;
import br.edu.ifce.engcomp.francis.diversidade.interfaces.RecyclerViewOnClickListenerHack;
import br.edu.ifce.engcomp.francis.diversidade.model.Contact;
import br.edu.ifce.engcomp.francis.diversidade.model.Developer;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment implements RecyclerViewOnClickListenerHack {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Developer> dataSource;


    public AboutFragment() {
        // Required empty public constructor
    }

    public ArrayList<Developer> generateDataSource(){
        ArrayList<Developer> developersArrayList = new ArrayList<>();

        Developer andre = new Developer("André Coelho");
        ArrayList<Contact> andreContacts = new ArrayList<>();
        andreContacts.add(new Contact("facebook", "fb.com/"));
        andreContacts.add(new Contact("twitter", "twitter.com/"));
        andreContacts.add(new Contact("instagram", "instagram.com/"));
        andreContacts.add(new Contact("github", "github.com/"));
        andre.setContacts(andreContacts);

        Developer francisco = new Developer("Francisco Souza");
        ArrayList<Contact> franciscoContacts = new ArrayList<>();
        franciscoContacts.add(new Contact("facebook", "fb.com/"));
        franciscoContacts.add(new Contact("twitter", "twitter.com/"));
        franciscoContacts.add(new Contact("instagram", "instagram.com/"));
        franciscoContacts.add(new Contact("github", "github.com/"));
        francisco.setContacts(franciscoContacts);

        Developer joamila = new Developer("Joamila Brito");
        ArrayList<Contact> joamilaContacts = new ArrayList<>();
        joamilaContacts.add(new Contact("facebook", "fb.com/joamilab"));
        joamilaContacts.add(new Contact("twitter", "@joamila"));
        joamilaContacts.add(new Contact("instagram", "@joamilab"));
        joamilaContacts.add(new Contact("github", "github.com/joamilab"));
        joamila.setContacts(joamilaContacts);

        developersArrayList.add(andre);
        developersArrayList.add(francisco);
        developersArrayList.add(joamila);

        return developersArrayList;
    }

    @Override
    public void onCreate(Bundle savedInstace){
        super.onCreate(savedInstace);

        dataSource = generateDataSource();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        AboutRecyclerViewAdapter adapter = new AboutRecyclerViewAdapter(dataSource, getActivity().getApplicationContext());
        adapter.setRecycleViewOnClickListenerHack(this);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = (RecyclerView) view.findViewById(R.id.developers_recyler_view);

        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onClickListener(View view, int position){
        Log.i("TESTE-INTENT", dataSource.get(position).getName());
        Intent intent = new Intent(getActivity(), DetailDeveloperActivity.class);
        intent.putExtra("INFOS_DEVELOPER", dataSource.get(position));
        startActivity(intent);
    }
}
