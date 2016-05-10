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
            params.put("text", commentContent);
            params.put("name", commentAuthor);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_post));
        progressDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        commentEditText.setText("");
                        userCommentEditText.setText("");
                        Toast.makeText(getApplication(), R.string.toast_success_post, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if(error.networkResponse.statusCode == 406){
                    commentEditText.setText("");
                    userCommentEditText.setText("");
                    Toast.makeText(getApplication(), R.string.toast_success_post, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplication(), R.string.error_server, Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json; charset=UTF-8");
                return headers;
            }

        };

        queue.add(jsonObjReq);
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
