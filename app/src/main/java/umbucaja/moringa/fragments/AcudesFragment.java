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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import umbucaja.moringa.MoringaActivity;
import umbucaja.moringa.R;
import umbucaja.moringa.adapter.SearchViewAdapter;
import umbucaja.moringa.adapter.WaterSourceRecyclerAdapter;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.entity.WaterSource;
import umbucaja.moringa.service.Server;
import umbucaja.moringa.util.GlobalData;

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
    private View rootView;
    private SearchViewAdapter searchView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView waterSourcesRecyclerView;

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchViewAdapter) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Buscar Cidade...");
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getAdapter().getItem(position);
                searchView.setText(city.getName());
                GlobalData.setCurrCity(city);

                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                ((MoringaActivity)getActivity()).collapsingToolbar.setTitle(city.getName());



                waterSourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.water_source_recycler_view);
                waterSourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                Server.getInstance(getContext()).getWaterAllSourcesFromCity(waterSourcesRecyclerView, city.getId());

            }
        });

        if (GlobalData.isConnected(getContext())) {
            //GlobalData.getLocation(getContext());
            Server.getInstance(getContext()).populateToolbarCities(searchView);
        } else {
            Snackbar.make(rootView, "Verifique sua conexão com a internet!", Snackbar.LENGTH_LONG).show();
        }

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.d("BUSCAR POR", query);
//                //TODO: buscar pela cidade e atualizar o view
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Log.d("TEXTO", newText);
//                //TODO: pode mudar os adapters aqui
//                return false;
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_acudes, container, false);
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                //GlobalData.getLocation(getContext());
                Server.getInstance(getContext()).populateToolbarCities(searchView);
            } else {
                Log.wtf(DEBUG_TAG, "Go to app settings to change its permissions related to GPS usage!");
            }
        } else {
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
