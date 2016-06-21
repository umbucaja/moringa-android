package umbucaja.moringa.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import umbucaja.moringa.R;
import umbucaja.moringa.entity.WaterSource;

/**
 * Created by Romeryto on 18/12/2015.
 */
public class WaterSourceListAdapter extends BaseAdapter {

    private Context context;
    private List<WaterSource> list;

    public WaterSourceListAdapter(Context context, List<WaterSource> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public List<WaterSource> getAllBarcodeFields() {
        return list;
    }

    public List<String> getAllBarcodeInStringFields() {

        List<String> barcodeStrings = new ArrayList<String>();
        for (WaterSource barcodeField:list) {
            //barcodeStrings.add(barcodeField.getField());
        }
        return barcodeStrings;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView barcodeFieldTV;
        ImageButton imgBtnDeleteField;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WaterSource waterSource = list.get(position);
        View rowView = convertView;

        // reuse views
        if (rowView == null) {
            rowView = View.inflate(context, R.layout.add_watersource_list, null);

            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.barcodeFieldTV = (TextView) rowView.findViewById(R.id.textViewBarcodeField);
            viewHolder.imgBtnDeleteField = (ImageButton) rowView.findViewById(R.id.imageButtonDeleteField);
            viewHolder.imgBtnDeleteField.setTag(position);
            rowView.setTag(viewHolder);

        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.barcodeFieldTV.setText(waterSource.getName());
        return rowView;
    }

    public void updateBarcodeFieldList(List<WaterSource> newlist) {
        this.list.clear();
        this.list = newlist;
        this.notifyDataSetChanged();
    }
}
