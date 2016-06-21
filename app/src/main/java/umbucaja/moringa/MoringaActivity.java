package umbucaja.moringa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import umbucaja.moringa.fragments.AcudesFragment;
import umbucaja.moringa.fragments.ChuvasFragment;
import umbucaja.moringa.fragments.DesenvolvedoresFragment;
import umbucaja.moringa.fragments.SobreFragment;

public class MoringaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AcudesFragment.OnFragmentInteractionListener, ChuvasFragment.OnFragmentInteractionListener, SobreFragment.OnFragmentInteractionListener, DesenvolvedoresFragment.OnFragmentInteractionListener {

    private MenuItem myActionMenuItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moringa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.water_1).centerCrop().into(imageView);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = AcudesFragment.newInstance("","");
        setTitle("Info Açudes");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentView, fragment).commit();


//        ActionBar actionBar = getActionBar();
//        //actionBar.setDisplayHomeAsUpEnabled(true);
//        //actionBar.setDisplayShowCustomEnabled(true);
//        // actionBar.setDisplayShowTitleEnabled(false);
//        // actionBar.setIcon(R.drawable.ic_action_search);
//
//        LayoutInflater inflator = (LayoutInflater) this
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflator.inflate(R.layout.toolbar_search_view, null);
//
//        actionBar.setCustomView(v);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//        AutoCompleteTextView textView = (AutoCompleteTextView) v
//                .findViewById(R.id.textView);
//        textView.setAdapter(adapter);


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
        getMenuInflater().inflate(R.menu.moringa, menu);

        myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint("Buscar Cidade...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                Log.d("BUSCAR POR", query);
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("TEXTO", s);
                //TODO: pode mudar os adapters aqui
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Snackbar.make(findViewById(R.id.content_moringa), "search", Snackbar.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class fragmentClass = AcudesFragment.class;

        if (id == R.id.nav_acudes) {
            fragmentClass = AcudesFragment.class;
            setTitle(item.getTitle());
        } else if (id == R.id.nav_chuvas) {
            fragmentClass = ChuvasFragment.class;
            setTitle(item.getTitle());
        } else if (id == R.id.nav_desenvolvedores) {
            fragmentClass = DesenvolvedoresFragment.class;
            setTitle(item.getTitle());
            closeOptionsMenu();
        } else if (id == R.id.nav_sobre) {
            fragmentClass = SobreFragment.class;
            setTitle(item.getTitle());
            closeOptionsMenu();
        } else if (id == R.id.nav_sair) {
            finish();
        }

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentView, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void openNavigation(View view){
        String URL = view.getTag().toString();
        Log.d("URL", URL);
        Uri uri = Uri.parse(URL);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
