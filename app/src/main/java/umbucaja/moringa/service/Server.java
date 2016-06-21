package umbucaja.moringa.service;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import umbucaja.moringa.R;
import umbucaja.moringa.adapter.SearchViewAdapter;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.util.GlobalData;

/**
 * Created by jordaoesa on 17/06/2016.
 */
public class Server {

    private final String URL = "http://192.168.1.100:8080/";
    private Context context;
    private Gson gson;
    private ArrayAdapter<String> adapter;

    private static Server server;

    public static Server getInstance(Context context){
        if(server == null)
            server = new Server();
        server.setContext(context);
        return server;
    }

    private Server(){
        this.gson = new Gson();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void populateToolbarCities(final SearchViewAdapter searchView, final String cityName) {
        if(GlobalData.cityNames == null)
            new Connector(context, new Connector.Response() {
                @Override
                public void handleResponse(JSONArray output) {
                    if(output == null)
                        return;

                    List<City> cities = new ArrayList<City>();
                    for(int i=0; i < output.length(); i++){
                        try {
                            City city = gson.fromJson(output.getJSONObject(i).toString(), City.class);
                            cities.add(city);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    String[] nomes = new String[cities.size()];
                    for(int i = 0; i < cities.size(); i++){
                        nomes[i] = cities.get(i).getName();
                    }

                    GlobalData.setCityNames(nomes);

                    adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, nomes);
                    searchView.setText(cityName);
                    searchView.setAdapter(adapter);
                }
            }).execute(URL + "cities");
        else{
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, GlobalData.cityNames);
            searchView.setText(cityName);
            searchView.setAdapter(adapter);
        }
    }

}
