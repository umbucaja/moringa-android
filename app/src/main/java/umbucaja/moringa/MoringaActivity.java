package umbucaja.moringa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.rollbar.android.Rollbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import umbucaja.moringa.fragments.AcudesFragment;
import umbucaja.moringa.fragments.ChuvasEstacaoFragment;
import umbucaja.moringa.fragments.ChuvasFragment;
import umbucaja.moringa.fragments.DesenvolvedoresFragment;
import umbucaja.moringa.fragments.SobreFragment;
import umbucaja.moringa.fragments.WaterSourceFragment;

public class MoringaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AcudesFragment.OnFragmentInteractionListener, ChuvasFragment.OnFragmentInteractionListener, SobreFragment.OnFragmentInteractionListener, DesenvolvedoresFragment.OnFragmentInteractionListener, WaterSourceFragment.OnFragmentInteractionListener, ChuvasEstacaoFragment.OnFragmentInteractionListener {

    private final int REQUEST_LOCATION = 1;
    public CollapsingToolbarLayout collapsingToolbar;
    public AppBarLayout appBarLayout;
    public ImageView imageViewLogoTop;
    public Toolbar toolbar;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public FloatingActionButton fabFacebookShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moringa);

        //ROLLBAR CATCHING EXCEPTIONS
        Rollbar.init(this, "4b8bed86dc44451c8572ad5d719f2b75", "production");

        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        fabFacebookShare = (FloatingActionButton) findViewById(R.id.fab_fb_share);

        imageViewLogoTop = (ImageView) findViewById(R.id.backdrop);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.menos_35));
        }

        imageViewLogoTop.setImageResource(R.color.menos_35);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = AcudesFragment.newInstance("","");
        setTitle("Cidades");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentView, fragment).commit();
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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        System.out.println("ACTIVITY ALALALA " + item.getItemId());
        int id = item.getItemId();
        System.out.println("ITEM ID: "+id);
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

        if (id == R.id.nav_cidades) {
            fragmentClass = AcudesFragment.class;
            collapsingToolbar.setTitle(item.getTitle());
        } else if (id == R.id.nav_chuvas) {
            fragmentClass = ChuvasFragment.class;
            collapsingToolbar.setTitle(item.getTitle());
        } else if (id == R.id.nav_desenvolvedores) {
            fragmentClass = DesenvolvedoresFragment.class;
            collapsingToolbar.setTitle(item.getTitle());
            closeOptionsMenu();
        } else if (id == R.id.nav_sobre) {
            fragmentClass = SobreFragment.class;
            collapsingToolbar.setTitle(item.getTitle());
            closeOptionsMenu();
        } else if(id == R.id.nav_email){

            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "moringaapp@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Moringa - Feedback / Sugestão - "+ dt1.format(new Date(System.currentTimeMillis())));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new Intent(Intent.ACTION_SENDTO));
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));

        } else if (id == R.id.nav_sair) {
            finish();
            //This return solves the problem in exiting button
            return true;
        }

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
