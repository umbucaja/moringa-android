package umbucaja.moringa.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import umbucaja.moringa.entity.City;

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
                String term = removeAcentos(constraint.toString().trim().toLowerCase());

                String placeStr;
                for (City c : cities) {
                    placeStr = removeAcentos(c.getName().toLowerCase());
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

    public static String[] REPLACES = { "a", "e", "i", "o", "u", "c" };

    public static Pattern[] PATTERNS = null;

    public static void compilePatterns() {
        PATTERNS = new Pattern[REPLACES.length];
        PATTERNS[0] = Pattern.compile("[âãáàä]", Pattern.CASE_INSENSITIVE);
        PATTERNS[1] = Pattern.compile("[éèêë]", Pattern.CASE_INSENSITIVE);
        PATTERNS[2] = Pattern.compile("[íìîï]", Pattern.CASE_INSENSITIVE);
        PATTERNS[3] = Pattern.compile("[óòôõö]", Pattern.CASE_INSENSITIVE);
        PATTERNS[4] = Pattern.compile("[úùûü]", Pattern.CASE_INSENSITIVE);
        PATTERNS[5] = Pattern.compile("[ç]", Pattern.CASE_INSENSITIVE);
    }

    public static String removeAcentos(String text) {
        if (PATTERNS == null) {
            compilePatterns();
        }

        String result = text;
        for (int i = 0; i < PATTERNS.length; i++) {
            Matcher matcher = PATTERNS[i].matcher(result);
            result = matcher.replaceAll(REPLACES[i]);
        }
        return result.toUpperCase();
    }
}
