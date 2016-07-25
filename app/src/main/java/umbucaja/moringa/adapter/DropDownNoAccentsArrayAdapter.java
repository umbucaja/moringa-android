package umbucaja.moringa.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import umbucaja.moringa.entity.City;
import umbucaja.moringa.util.GlobalData;

/**
 * Created by jordaoesa on 7/24/16.
 */
public class DropDownNoAccentsArrayAdapter extends ArrayAdapter<City> implements Filterable {

    private List<City> cities;
    private List<City> results;
    private Filter filter;

    public DropDownNoAccentsArrayAdapter(Context context, int resource, List<City> cities) {
        super(context, resource, cities);
        this.cities = cities;
        this.results = this.cities;
        this.filter = new FilterNoAccent();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public City getItem(int position) {
        if (results != null && results.size() > 0 && position < results.size()){
            return results.get(position);
        } else {
            return null;
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class FilterNoAccent extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();
            ArrayList<City> temp = new ArrayList<City>();

            if (constraint != null) {
                String term = GlobalData.removeAcentos(constraint.toString().trim().toLowerCase());

                String placeStr;
                for (City c : cities) {
                    placeStr = GlobalData.removeAcentos(c.getName().toLowerCase());
                    if (placeStr.indexOf(term) > -1){
                        temp.add(c);
                    }
                }
            }
            filterResults.values = temp;
            filterResults.count = temp.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            results = (ArrayList<City>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
