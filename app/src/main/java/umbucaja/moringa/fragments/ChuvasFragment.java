package umbucaja.moringa.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import umbucaja.moringa.MoringaActivity;
import umbucaja.moringa.R;
import umbucaja.moringa.adapter.ChuvasRecyclerAdapter;
import umbucaja.moringa.adapter.SearchViewAdapter;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.service.Server;
import umbucaja.moringa.util.GlobalData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChuvasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChuvasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChuvasFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SELECTED_CITY = "default_city";

    private View rootView;
    private RecyclerView recyclerView;
    private ChuvasRecyclerAdapter chuvasRecyclerAdapter;
    private SearchViewAdapter searchView;

    private OnFragmentInteractionListener mListener;

    public ChuvasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param selectedCity The default city to load rain measurements.
     * @return A new instance of fragment ChuvasFragment.
     */
    public static ChuvasFragment newInstance(String selectedCity, City[] cities) {
        ChuvasFragment fragment = new ChuvasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SELECTED_CITY, selectedCity);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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



    /*
    Android Lifecycle
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
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


                recyclerView = (RecyclerView) rootView.findViewById(R.id.chuvas_recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                Server.getInstance(getContext()).getMeasurementStationsFromCity(recyclerView, city.getId());

            }
        });

        if (GlobalData.isConnected(getContext())) {
            //GlobalData.getLocation(getContext());
            Server.getInstance(getContext()).populateToolbarCities(searchView);
        } else {
            Snackbar.make(rootView, "Verifique sua conexão com a internet!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chuvas, container, false);

        ((MoringaActivity)getActivity()).collapsingToolbar.setStatusBarScrimColor(Color.parseColor("#00000000"));
        ((MoringaActivity)getActivity()).collapsingToolbar.setContentScrimColor(Color.parseColor("#66000000"));
        //utilizado para salvar o estado atual do fragment
        if(GlobalData.currCity != null){
            recyclerView = (RecyclerView) rootView.findViewById(R.id.chuvas_recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ((MoringaActivity)getActivity()).collapsingToolbar.setTitle(GlobalData.currCity.getName());
            Server.getInstance(getContext()).getMeasurementStationsFromCity(recyclerView, GlobalData.currCity.getId());
        }
        return rootView;
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
}
