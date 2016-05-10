package br.edu.ifce.engcomp.francis.diversidade.Fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import br.edu.ifce.engcomp.francis.diversidade.adapters.TextRecyclerViewAdapter;
import br.edu.ifce.engcomp.francis.diversidade.interfaces.RecyclerViewOnClickListenerHack;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightsFragment extends Fragment implements RecyclerViewOnClickListenerHack{
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<TextBlog> dataSource = new ArrayList<>();
    ProgressDialog progressDialog;
    TextRecyclerViewAdapter adapter;

    public RightsFragment() {
        // Required empty public constructor
    }

    public void doRequest(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://diversidade-cloudsocial.rhcloud.com/api/v1/news/categories/rights";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_load));
        progressDialog.show();

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String text = jsonObject.getString("text");
                                String category = jsonObject.getString("category");
                                String source = jsonObject.getString("source");

                                dataSource.add(new TextBlog(title, text, source, category));

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rights, container, false);

        doRequest();

        adapter = new TextRecyclerViewAdapter(getActivity().getApplicationContext(), dataSource);
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
