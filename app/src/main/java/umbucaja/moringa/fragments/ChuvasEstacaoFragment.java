package umbucaja.moringa.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import umbucaja.moringa.MoringaActivity;
import umbucaja.moringa.R;
import umbucaja.moringa.entity.MeasurementStation;
import umbucaja.moringa.service.Server;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChuvasEstacaoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChuvasEstacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChuvasEstacaoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "station_id";

    // TODO: Rename and change types of parameters
    private static MeasurementStation station;
    private Long stationId;
    private View rootView;
    private GridView gridView;

    private OnFragmentInteractionListener mListener;

    public ChuvasEstacaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChuvasEstacaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChuvasEstacaoFragment newInstance(MeasurementStation station) {
        ChuvasEstacaoFragment.station = station;
        ChuvasEstacaoFragment fragment = new ChuvasEstacaoFragment();
        Bundle args = new Bundle();
        //args.putLong(ARG_PARAM, station.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        if(station != null)
            ((MoringaActivity)getActivity()).collapsingToolbar.setTitle(station.getName());
        ((MoringaActivity)getActivity()).appBarLayout.setExpanded(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stationId = getArguments().getLong(ARG_PARAM);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chuvas_estacao, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridview_chuva_item);

        //TODO: criar metodo em Server para requisitar os dados de uma dada estacao por ID
        if(station != null)
            Server.getInstance(getContext()).getMeasurementsFromStation(rootView, gridView, station);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), "OI", Toast.LENGTH_SHORT).show();
//            }
//        });

        return rootView;
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
