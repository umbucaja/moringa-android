package umbucaja.moringa.fragments;

import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import umbucaja.moringa.R;
import umbucaja.moringa.adapter.WaterSourceListAdapter;
import umbucaja.moringa.entity.WaterSource;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class WaterSourceListFragment extends ListFragment {


    View inflateFragmentView;
    private static final int NO_BARCODE_FIELD = 0;
    private WaterSourceListAdapter adapter;
    private List<WaterSource> fields;
   // private BatchFieldController batchFieldController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflateFragmentView = inflater.inflate(R.layout.fragment_item_list, container, false);
        //batchFieldController = BatchFieldController.getInstance();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        listView.setDivider(new ColorDrawable(Color.RED));
        //listView.setSelector(R.drawable.user_list_view_selector);
        //listView.setClipToPadding(false);

        if (listView.getCount() > NO_BARCODE_FIELD) {
            listView.performItemClick(getView(), 0, 0);
        } else if ( listView.getCount() == NO_BARCODE_FIELD) {

        }

       // fields = (List<BatchField>)(List<?>) batchFieldController.getAll();
//        if(fields!=null){
//            adapter = new BarcodeFieldListAdapter(getActivity(), fields);
//        }else{
//            adapter = new BarcodeFieldListAdapter(getActivity(), new ArrayList<BatchField>());
//        }

        setListAdapter(adapter);

        if(!getListAdapter().isEmpty()) {
           // onListItemClick(getListView(), getView(), 0, 0);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Toast toast = Toast.makeText(inflateFragmentView.getContext(), "Clicou Posicao: "+position, Toast.LENGTH_SHORT);
        //toast.show();
    }

    public void updateListAdapter() {
        //fields = (List<BatchField>)(List<?>) batchFieldController.getAll();
        adapter.updateBarcodeFieldList(fields);
        setListAdapter(adapter);
    }

    public void updateListAdapter(List<WaterSource> list) {
        fields = list;
        //fields = (List<WaterSource>)(List<?>) batchFieldController.getAll();

        adapter.updateBarcodeFieldList(fields);
        setListAdapter(adapter);
    }



    public WaterSourceListAdapter getListViewAdapter(){
        return this.adapter;
    }

    public void clearSelection() {
        getListView().clearFocus();
    }

    public View getInflateFragmentView(){
        return inflateFragmentView;
    }
}
