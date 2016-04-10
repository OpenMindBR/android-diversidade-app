package br.edu.ifce.engcomp.francis.diversidade.Fragments;

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
import br.edu.ifce.engcomp.francis.diversidade.adapters.CommentsRecyclerViewAdapter;
import br.edu.ifce.engcomp.francis.diversidade.model.Comment;

/**
 * Created by Bolsista on 29/03/2016.
 */
public class CommentsFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Comment> dataSource;

    public CommentsFragment() {
        // Required empty public constructor
    }

    public ArrayList<Comment> generateDataSourceMock(){
        ArrayList<Comment> commentsArrayList = new ArrayList<>();

        Comment teste1 = new Comment();
        teste1.setAuthor("Anônimo");
        teste1.setDate("25/12/2015 - 09:35");
        teste1.setContent("MARA!");

        Comment teste2 = new Comment();
        teste2.setAuthor("Maria");
        teste2.setDate("01/04/2016 - 12:05");
        teste2.setContent("Adorei e vou voltar!");

        commentsArrayList.add(teste1);
        commentsArrayList.add(teste2);

        return commentsArrayList;
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
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        CommentsRecyclerViewAdapter adapter = new CommentsRecyclerViewAdapter(getActivity().getApplicationContext(), dataSource);
        this.layoutManager = new LinearLayoutManager(getActivity());

        this.recyclerView = (RecyclerView) view.findViewById(R.id.comments_nucleus_list);
        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

}
