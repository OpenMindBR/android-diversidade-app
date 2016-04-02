package br.edu.ifce.engcomp.francis.diversidade.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.fragments.CommentsFragment;
import br.edu.ifce.engcomp.francis.diversidade.fragments.InformationsFragment;
import br.edu.ifce.engcomp.francis.diversidade.fragments.ServicesFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailNucleusActivity extends AppCompatActivity {
    private FragmentTabHost placeTabHost;
    private CircleImageView photoPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nucleus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView nucleusName = (TextView) findViewById(R.id.nucleus_name);
        TextView nucleusCity = (TextView) findViewById(R.id.nucleus_city);
        photoPlace = (CircleImageView) findViewById(R.id.adapter_nucleus_image);

        this.initTabHost();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.placeTabHost = null;
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

        this.placeTabHost.setCurrentTab(1);

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

}
