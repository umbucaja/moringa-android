package umbucaja.moringa.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import umbucaja.moringa.R;
import umbucaja.moringa.adapter.CityArrayAdapter;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.service.CityService;
import umbucaja.moringa.service.mock.CityServiceMock;

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

    private String selectedCity;
    private City[] cities = {};

    private OnFragmentInteractionListener mListener;

    /*
    Fragment Views
     */
    private AppCompatAutoCompleteTextView autocompleteCidades;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CityService cityService = CityServiceMock.getInstance();
        cities = cityService.listCities().toArray(cities);

        if (getArguments() != null) {
            selectedCity = getArguments().getString(ARG_SELECTED_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        if (savedInstanceState != null) {
            // Use savedInstanceState
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        else {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_chuvas, container, false);
        }

        if (view != null) {
            // Get layout views
            autocompleteCidades = (AppCompatAutoCompleteTextView) view.findViewById(R.id.fragment_chuvas_autocomplete_cidades);

            // load autocomplete content
            autocompleteCidades.setAdapter(new CityArrayAdapter(getContext(), cities));
            autocompleteCidades.setText(selectedCity);
        }

        return view;
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
