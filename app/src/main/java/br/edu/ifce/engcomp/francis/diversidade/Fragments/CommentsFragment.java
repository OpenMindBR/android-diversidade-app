package br.edu.ifce.engcomp.francis.diversidade.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.adapters.CommentsRecyclerViewAdapter;
import br.edu.ifce.engcomp.francis.diversidade.model.Comment;
import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;

/**
 * Created by Bolsista on 29/03/2016.
 */
public class CommentsFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Comment> dataSource = new ArrayList<>();
    ProgressDialog progressDialog;
    Nucleus nucleus;
    CommentsRecyclerViewAdapter adapter;

    public CommentsFragment() {
        // Required empty public constructor
    }

    public void doRequest(){
        Intent currentIntent = getActivity().getIntent();
        nucleus = (Nucleus) currentIntent.getSerializableExtra("INFOS_NUCLEUS");

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String urlBase = "http://diversidade-cloudsocial.rhcloud.com/api/v1/centers/";
        String urlSufix = "/comments";
        String url = urlBase + String.valueOf(nucleus.getId()) + urlSufix;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_load));
        progressDialog.show();

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dataSource.clear();
                        for(int i =0; i<response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String text = jsonObject.getString("text");
                                String date = jsonObject.getString("created_at");

                                dataSource.add(new Comment(name, text, date));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        progressDialog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.error_server, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map map = new HashMap();
                map.put("Accept", "application/json; charset=UTF-8");
                return map;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onCreate(Bundle savedInstace){
        super.onCreate(savedInstace);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        doRequest();

        adapter = new CommentsRecyclerViewAdapter(getActivity().getApplicationContext(), dataSource);
        this.layoutManager = new LinearLayoutManager(getActivity());

        this.recyclerView = (RecyclerView) view.findViewById(R.id.comments_nucleus_list);
        this.recyclerView.setHasFixedSize(false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

}
