package umbucaja.moringa;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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

import com.squareup.picasso.Picasso;

import umbucaja.moringa.fragments.AcudesFragment;
import umbucaja.moringa.fragments.ChuvasEstacaoFragment;
import umbucaja.moringa.fragments.ChuvasFragment;
import umbucaja.moringa.fragments.DesenvolvedoresFragment;
import umbucaja.moringa.fragments.SobreFragment;
import umbucaja.moringa.fragments.WaterSourceFragment;

public class MoringaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AcudesFragment.OnFragmentInteractionListener, ChuvasFragment.OnFragmentInteractionListener, SobreFragment.OnFragmentInteractionListener, DesenvolvedoresFragment.OnFragmentInteractionListener, WaterSourceFragment.OnFragmentInteractionListener, ChuvasEstacaoFragment.OnFragmentInteractionListener {

    private final int REQUEST_LOCATION = 1;
    private final String DEBUG_TAG = "MORINGA_ACTIVITY";
    public CollapsingToolbarLayout collapsingToolbar;
    public AppBarLayout appBarLayout;
    public ImageView imageViewLogoTop;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moringa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
//        else{
//            GlobalData.getLocation(getApplicationContext(), null);
//        }
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);


        imageViewLogoTop = (ImageView) findViewById(R.id.backdrop);
        //Glide.with(this).load(R.drawable.menos_35_v2).centerCrop().into(imageViewLogoTop);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.menos_35_v2));

        Picasso.with(getApplicationContext()).load(R.drawable.menos_35_v2).into(imageViewLogoTop);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = AcudesFragment.newInstance("","");
        setTitle("Cidades");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentView, fragment).commit();
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == REQUEST_LOCATION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                GlobalData.getLocation(getApplicationContext(), null);
//            } else {
//                Log.wtf(DEBUG_TAG, "Acesse as configurações do aplicativo para modificar as permissões!");
//            }
//        } else {
//            Log.wtf(DEBUG_TAG, "Acesse as configurações do aplicativo para modificar as permissões!");
//        }
//    }


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
//        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
//            fragmentManager.popBackStack();
//        }
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
