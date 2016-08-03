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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import umbucaja.moringa.MoringaActivity;
import umbucaja.moringa.R;
import umbucaja.moringa.adapter.SearchViewAdapter;
import umbucaja.moringa.analytics.MoringaApplication;
import umbucaja.moringa.entity.City;
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
    private ImageView imageViewSearch;
    private Tracker mTracker;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView waterSourcesRecyclerView;

    private OnFragmentInteractionListener mListener;
    private MenuItem searchItem;



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
        MoringaApplication application = (MoringaApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchViewAdapter) MenuItemCompat.getActionView(searchItem);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MoringaActivity)getActivity()).appBarLayout.setExpanded(false);
            }
        });

        int searchImgId = android.support.v7.appcompat.R.id.search_button; // I used the explicit layout ID of searchview's ImageView
        imageViewSearch = (ImageView) searchView.findViewById(searchImgId);
        imageViewSearch.setImageResource(R.drawable.ic_search_moringa);

        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);

        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                System.out.println("Clicou fechar");
                //Find EditText view
                EditText et = (EditText) searchView.findViewById(R.id.search_src_text);

                //Clear the text from EditText view
                et.setText("");

                //Collapse the action view
                searchView.onActionViewCollapsed();

                //Collapse the search widget
                searchItem.collapseActionView();
                ((MoringaActivity)getActivity()).appBarLayout.setExpanded(true);
            }
        });


        searchView.setQueryHint("Buscar Cidade...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (GlobalData.isConnected(getContext())) {
                    City city = GlobalData.getCityByName(query);
                    //City city = (City) parent.getAdapter().getItem(position);
                    searchView.setText(city.getName());
                    GlobalData.setCurrCity(city);

                    searchView.clearFocus();
                    searchView.setQuery("", false);
                    searchView.setIconified(true);

                    waterSourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.water_source_recycler_view);
                    waterSourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    Server.getInstance(getContext()).getWaterAllSourcesFromCity(getView(), waterSourcesRecyclerView, city);

                    ((MoringaActivity) getActivity()).appBarLayout.setExpanded(true);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Acudes")
                            .setAction("Busca")
                            .setLabel(city.getName())
                            .setValue(1)
                            .build());
                } else {
                    Snackbar.make(((MoringaActivity)getContext()).appBarLayout, "Verifique sua conexão com a internet!", Snackbar.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //does nothing
                return false;
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getAdapter().getItem(position);
                searchView.setText(city.getName());
                GlobalData.setCurrCity(city);

                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);

                waterSourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.water_source_recycler_view);
                waterSourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                Server.getInstance(getContext()).getWaterAllSourcesFromCity(getView(), waterSourcesRecyclerView, city);

                ((MoringaActivity)getActivity()).appBarLayout.setExpanded(true);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Acudes")
                        .setAction("Busca")
                        .setLabel(city.getName())
                        .setValue(1)
                        .build());
            }
        });

        if (GlobalData.isConnected(getContext())) {
            Server.getInstance(getContext()).populateToolbarCities(searchView, waterSourcesRecyclerView);
        } else {
            Snackbar.make(((MoringaActivity)getContext()).appBarLayout, "Verifique sua conexão com a internet!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_acudes, container, false);
        //utilizado para salvar o estado atual do fragment
        waterSourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.water_source_recycler_view);
        waterSourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(GlobalData.currCity != null){
            ((MoringaActivity)getActivity()).collapsingToolbar.setTitle(GlobalData.currCity.getName());

            Server.getInstance(getContext()).getWaterAllSourcesFromCity(getView(),waterSourcesRecyclerView, GlobalData.currCity);
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Acudes")
                    .setAction("BuscaGPS")
                    .setLabel(GlobalData.currCity.getName())
                    .setValue(1)
                    .build());
        }
        ((MoringaActivity)getActivity()).collapsingToolbar.setStatusBarScrimColor(Color.parseColor("#00000000"));
        ((MoringaActivity)getActivity()).collapsingToolbar.setContentScrimColor(Color.parseColor("#66000000"));
        ((MoringaActivity)getContext()).appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MoringaActivity) getActivity()).appBarLayout.setExpanded(false);
                imageViewSearch.callOnClick();

            }
        });
        ((MoringaActivity)getContext()).actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((MoringaActivity)getContext()).fabFacebookShare.setVisibility(View.GONE);
        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mTracker.setScreenName("AcudesFragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
