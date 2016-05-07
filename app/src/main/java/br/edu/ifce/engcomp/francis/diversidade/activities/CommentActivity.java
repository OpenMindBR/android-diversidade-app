package br.edu.ifce.engcomp.francis.diversidade.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.model.Comment;
import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;

public class CommentActivity extends AppCompatActivity {
    EditText commentEditText;
    EditText userCommentEditText;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        commentEditText = (EditText) findViewById(R.id.new_comment_edit_text);
        userCommentEditText = (EditText) findViewById(R.id.user_comment_edit_text);
        commentEditText.addTextChangedListener(this.buildTextWatcher());

        doRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.review_menu) {
            sendComment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doRequest(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://diversidade-cloudsocial.rhcloud.com/api/v1/centers/1/comments";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("RESPONSE-COM", response.toString());

                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Erro no servidor!", Toast.LENGTH_SHORT).show();
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

    public void sendComment(){
        final Nucleus nucleus = (Nucleus) getIntent().getExtras().get("INFOS_NUCLEUS");
        final String commentAuthor = userCommentEditText.getText().toString();
        final String commentContent = commentEditText.getText().toString();

        String urlBase = "http://diversidade-cloudsocial.rhcloud.com/api/v1/centers/";
        String urlSufix = "/comments";
        String url = urlBase + String.valueOf(nucleus.getId()) + urlSufix;

        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject params = new JSONObject();

        try {
            params.put("comment_user_name", commentAuthor);
            params.put("comment_text", commentContent);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Postando...");
        progressDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplication(), "Postado com SUCESSO", Toast.LENGTH_SHORT);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplication(), "ERRO no servidor", Toast.LENGTH_SHORT);

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };


// Adding request to request queue
        queue.add(jsonObjReq);

        /*StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("RESPONSE-COM", response);
                Toast.makeText(getApplicationContext(), "Postado com sucesso!", Toast.LENGTH_SHORT);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erro no servidor!", Toast.LENGTH_SHORT);
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", commentAuthor);
                params.put("text", Uri.encode(commentContent));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(request);*/
    }

    private TextWatcher buildTextWatcher() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String subtitle;
                int residualChars = 250 - s.length();

                if (residualChars > 1)
                    subtitle = String.format("%d ", residualChars) + getString(R.string.readers_review_toolbar_subtitle_plural);
                else
                    subtitle = String.format("%d ", residualChars) + getString(R.string.readers_review_toolbar_subtitle);

                toolbar.setSubtitle(subtitle);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
    }

}
