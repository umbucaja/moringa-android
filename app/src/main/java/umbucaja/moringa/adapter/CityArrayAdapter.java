package umbucaja.moringa.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Collection;

import umbucaja.moringa.entity.City;

/**
 * Created by Andre on 17/06/2016.
 */
public class CityArrayAdapter extends ArrayAdapter<City> {

    public CityArrayAdapter(Context context, City[] cities) {
        super(context, android.R.layout.simple_list_item_1, cities);
    }
}
