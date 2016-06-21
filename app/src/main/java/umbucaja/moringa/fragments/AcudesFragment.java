package umbucaja.moringa.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import umbucaja.moringa.R;
import umbucaja.moringa.adapter.WaterSourceRecyclerAdapter;
import umbucaja.moringa.entity.WaterSource;
import umbucaja.moringa.service.Server;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcudesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcudesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcudesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final int REQUEST_LOCATION = 1;
    private final String DEBUG_TAG = "ACUDES_DEBUG";
    private TextView tvLocation;
    private LocationManager locationManager;
    private View rootView;
    //private String[] ACUDES = new String[]{"Boqueirão", "Coremas", "Vaca Brava"};
    //private ArrayAdapter<String> adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView waterSourcesRecyclerView;
    private List<WaterSource> waterSourcesList;

    private OnFragmentInteractionListener mListener;

    public AcudesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcudesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcudesFragment newInstance(String param1, String param2) {
        AcudesFragment fragment = new AcudesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_acudes, container, false);

        // check permissions to use GPS
        tvLocation = (TextView) rootView.findViewById(R.id.tv_acudes_location);
        waterSourcesList = new ArrayList<>();
        WaterSource w1 = new WaterSource(1, "Farinha", 200, "m³", "Açude", "40%", "323315", "20/06/2016");
        WaterSource w2 = new WaterSource(2, "Jatobá", 300, "m³", "Açude",  "20%", "323315", "01/06/2016");
        waterSourcesList.add(w1);
        waterSourcesList.add(w2);
        WaterSourceRecyclerAdapter waterSourceRecyclerAdapter = new WaterSourceRecyclerAdapter(this.getContext(),waterSourcesList);
        waterSourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.water_source_recycler_view);
        waterSourcesRecyclerView.setAdapter(waterSourceRecyclerAdapter);
        waterSourcesRecyclerView.setAnimation(new Animation() {
            @Override
            protected Animation clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });
        waterSourcesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));



        if(isConnected(getContext())) {
            getLocation();
        }else{
            Snackbar.make(rootView, "Verifique sua conexão com a internet!", Snackbar.LENGTH_LONG).show();
        }

        //textView.setThreshold(1);

        return rootView;
    }

    public String getLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }else {
            Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d(DEBUG_TAG, loc.toString());
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> locations = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if(locations.size() > 0){
                    String cityName = locations.get(0).getLocality();
                    tvLocation.setText("(" + loc.getLatitude() + ", " + loc.getLongitude() + ") | " + cityName);

                    //Populate autocomplete textview
                    Server.getInstance(getContext()).populateTextViewWithCities(rootView, cityName);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    public boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_LOCATION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Log.d(DEBUG_TAG, loc.toString());
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> locations = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                    if(locations.size() > 0){
                        String cityName = locations.get(0).getLocality();
                        tvLocation.setText("(" + loc.getLatitude() + ", " + loc.getLongitude() + ") | " + cityName);

                        //Populate autocomplete textview
                        Server.getInstance(getContext()).populateTextViewWithCities(rootView, cityName);

                        //set current city as default
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                Log.wtf(DEBUG_TAG, "Go to app settings to change its permissions related to GPS usage!");
            }
        }else{
            Log.wtf(DEBUG_TAG, "Go to app settings to change its permissions related to GPS usage!");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
