package br.edu.ifce.engcomp.francis.diversidade.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ifce.engcomp.francis.diversidade.Fragments.CommentsFragment;
import br.edu.ifce.engcomp.francis.diversidade.Fragments.InformationsFragment;
import br.edu.ifce.engcomp.francis.diversidade.Fragments.ServicesFragment;
import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.model.Developer;
import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailNucleusActivity extends AppCompatActivity {
    private FragmentTabHost placeTabHost;
    private CircleImageView photoPlace;
    FloatingActionButton addComment;
    TextView nucleusName;
    TextView nucleusCity;
    Nucleus nucleus;
    ProgressDialog progressDialog;

    public void initDataSource() {
        Intent currentIntent = getIntent();
        nucleus = (Nucleus) currentIntent.getSerializableExtra("INFOS_NUCLEUS");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nucleus);

        initDataSource();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        this.addComment = (FloatingActionButton) findViewById(R.id.add_comment_fabutton);

        nucleusName = (TextView) findViewById(R.id.nucleus_name);
        nucleusCity = (TextView) findViewById(R.id.nucleus_city);
        photoPlace = (CircleImageView) findViewById(R.id.adapter_nucleus_image);

        this.initTabHost();
        this.initComponents();
        this.initAddComment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.placeTabHost = null;
    }

    private void initComponents() {
        nucleusName.setText(nucleus.getName());
        nucleusCity.setText(nucleus.getAddress().getCity());
    }

    private void initTabHost() {

        this.placeTabHost = (FragmentTabHost) findViewById(R.id.nucleus_tabHost);
        this.placeTabHost.setup(this, this.getSupportFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec placeInformationTab = this.placeTabHost.newTabSpec("placeInformationTab");
        TabHost.TabSpec servicesTab = this.placeTabHost.newTabSpec("servicesTab");
        TabHost.TabSpec commentsTab = this.placeTabHost.newTabSpec("commentsTab");

        placeInformationTab.setIndicator(getString(R.string.place_tab_informations));
        servicesTab.setIndicator(getString(R.string.place_tab_services));
        commentsTab.setIndicator(getString(R.string.place_tab_comments));

        this.placeTabHost.addTab(placeInformationTab, InformationsFragment.class, null);
        this.placeTabHost.addTab(servicesTab, ServicesFragment.class, null);
        this.placeTabHost.addTab(commentsTab, CommentsFragment.class, null);

        this.placeTabHost.setCurrentTab(0);

        this.stylizeTabsTextView(placeTabHost);
    }

    private void stylizeTabsTextView(FragmentTabHost tabHost) {
        ColorStateList tabTextColors;
        TabWidget tabWidget;
        TextView tabTextView;
        View tabView;

        int tabAmount;

        tabWidget = tabHost.getTabWidget();
        tabTextColors = this.getResources().getColorStateList(R.color.tab_text_selector);
        tabAmount = tabWidget.getTabCount();

        for (int i = 0; i < tabAmount; i++) {
            tabView = tabWidget.getChildTabViewAt(i);
            tabView.setBackgroundResource(R.drawable.abc_tab_indicator_material);

            tabTextView = (TextView) tabView.findViewById(android.R.id.title);
            tabTextView.setTextColor(tabTextColors);
        }
    }

    private void initAddComment() {
        this.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCommentActivity = new Intent(getBaseContext(), CommentActivity.class);
                intentCommentActivity.putExtra("INFOS_NUCLEUS", nucleus);
                startActivity(intentCommentActivity);
            }
        });
    }


}
