package br.edu.ifce.engcomp.francis.diversidade.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.adapters.ServicesRecyclerViewAdapter;
import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.Service;

/**
 * Created by Bolsista on 29/03/2016.
 */
public class ServicesFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Service> dataSource;

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstace){
        super.onCreate(savedInstace);

        //dataSource = generateDataSourceMock();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        Intent currentIntent = getActivity().getIntent();
        Nucleus nucleus = (Nucleus) currentIntent.getSerializableExtra("INFOS_NUCLEUS");

        dataSource = nucleus.getServices();

        ServicesRecyclerViewAdapter adapter = new ServicesRecyclerViewAdapter(getActivity().getApplicationContext(), dataSource);
        this.layoutManager = new LinearLayoutManager(getActivity());

        this.recyclerView = (RecyclerView) view.findViewById(R.id.services_nucleus_list);
        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}
