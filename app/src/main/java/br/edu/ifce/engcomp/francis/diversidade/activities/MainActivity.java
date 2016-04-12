package br.edu.ifce.engcomp.francis.diversidade.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifce.engcomp.francis.diversidade.Fragments.AboutFragment;
import br.edu.ifce.engcomp.francis.diversidade.Fragments.DiscoveringFragment;
import br.edu.ifce.engcomp.francis.diversidade.Fragments.HealthFragment;
import br.edu.ifce.engcomp.francis.diversidade.Fragments.NucleusFragment;
import br.edu.ifce.engcomp.francis.diversidade.Fragments.RightsFragment;
import br.edu.ifce.engcomp.francis.diversidade.Fragments.SuggestionFragment;
import br.edu.ifce.engcomp.francis.diversidade.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            Fragment fragmentNucleus = new NucleusFragment();
            android.app.FragmentManager fragmentManagerNucleus = getFragmentManager();
            fragmentManagerNucleus.beginTransaction().add(R.id.content_main_layout, fragmentNucleus).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nucleus) {
            Fragment fragmentNucleus = new NucleusFragment();
            android.app.FragmentManager fragmentManagerNucleus = getFragmentManager();
            toolbar.setTitle(getString(R.string.title_toolbar_nucleus));
            fragmentManagerNucleus.beginTransaction().replace(R.id.content_main_layout, fragmentNucleus).commit();

        } else if (id == R.id.nav_discovery) {
            Fragment fragmentDiscovering = new DiscoveringFragment();
            android.app.FragmentManager fragmentManagerDiscovering = getFragmentManager();
            toolbar.setTitle(getString(R.string.title_toolbar_discovery));
            fragmentManagerDiscovering.beginTransaction().replace(R.id.content_main_layout, fragmentDiscovering).commit();

        } else if (id == R.id.nav_rights) {
            Fragment fragmentRights = new RightsFragment();
            android.app.FragmentManager fragmentManagerRights = getFragmentManager();
            toolbar.setTitle(getString(R.string.title_toolbar_rights));
            fragmentManagerRights.beginTransaction().replace(R.id.content_main_layout, fragmentRights).commit();

        } else if (id == R.id.nav_health) {
            Fragment fragmentHealth = new HealthFragment();
            android.app.FragmentManager fragmentManagerHealth = getFragmentManager();
            toolbar.setTitle(getString(R.string.title_toolbar_health));
            fragmentManagerHealth.beginTransaction().replace(R.id.content_main_layout, fragmentHealth).commit();

        } else if (id == R.id.nav_sugestion) {
            Fragment fragmentSuggestion = new SuggestionFragment();
            android.app.FragmentManager fragmentManagerSuggestion = getFragmentManager();
            toolbar.setTitle(getString(R.string.title_toolbar_suggestion));
            fragmentManagerSuggestion.beginTransaction().replace(R.id.content_main_layout, fragmentSuggestion).commit();

        } else if (id == R.id.nav_about) {
            Fragment fragmentAbout = new AboutFragment();
            FragmentManager fragmentManagerAbout = getFragmentManager();
            toolbar.setTitle(getString(R.string.title_toolbar_about));
            fragmentManagerAbout.beginTransaction().replace(R.id.content_main_layout, fragmentAbout).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
