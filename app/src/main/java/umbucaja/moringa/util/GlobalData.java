package umbucaja.moringa.util;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import umbucaja.moringa.MoringaActivity;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.service.Server;

/**
 * Created by jordaoesa on 21/06/2016.
 */
public class GlobalData {
    public static City[] cities = null;
    public static City location = null;
    public static City currCity = null;
    public static City defaultCity = new City(13, "João Pessoa", "PB");

    public static void setCities(City[] cities) {
        GlobalData.cities = cities;
    }

    public static void setLocation(City location) {
        GlobalData.location = location;
    }

    public static void setCurrCity(City currCity) {
        GlobalData.currCity = currCity;
    }

    public static void getLocation(final Context context, final RecyclerView waterSourcesRecyclerView) {
        if(isConnected(context))
            new Localizacao(context, new Localizacao.Response() {
                @Override
                public void handleResponse(City output) {
                    GlobalData.setLocation(output);
                    if(waterSourcesRecyclerView != null) {
                        GlobalData.setCurrCity(output);
                        ((MoringaActivity) context).collapsingToolbar.setTitle(location.getName());
                        Server.getInstance(context).getWaterAllSourcesFromCity(waterSourcesRecyclerView, location);
                    }
                }
            }).execute();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private static class Localizacao extends AsyncTask<String, Void, City> {

        private final String DEBUG_TAG = "LOCALIZACAO";
        private Response response;
        private Context context;
        private ProgressDialog progressDialog;

        public Localizacao(Context context, Response response) {
            this.context = context;
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            if (context != null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Requisitando Localizacao...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }

        @Override
        protected City doInBackground(String... strings) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            City city = GlobalData.defaultCity;
            if(locationManager == null) return city;

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return city;
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                if(lastKnownLocation == null) city = GlobalData.defaultCity;
                else {
                    List<Address> locations = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
                    if (locations.size() > 0) {
                        String cityName = locations.get(0).getLocality();
                        city = Server.getInstance(context).getCityByName(cityName);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(DEBUG_TAG, city.getName());
            return city;
        }

        @Override
        protected void onPostExecute(City output) {
            if(progressDialog != null)
                progressDialog.dismiss();
            response.handleResponse(output);
        }

        public interface Response{
            void handleResponse(City output);
        }
    }

}
