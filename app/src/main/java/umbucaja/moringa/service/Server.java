package umbucaja.moringa.service;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import umbucaja.moringa.adapter.SearchViewAdapter;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.entity.WaterSource;
import umbucaja.moringa.util.GlobalData;

/**
 * Created by jordaoesa on 17/06/2016.
 */
public class Server {

    private final String URL = "http://192.168.1.100:8080/";
    private Context context;
    private Gson gson;

    private static Server server;

    public static Server getInstance(Context context){
        if(server == null)
            server = new Server();
        server.setContext(context);
        return server;
    }

    private Server(){
        GsonBuilder builder = new GsonBuilder();

// Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        //Gson gson = builder.create();
        this.gson = builder.create();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void populateToolbarCities(final SearchViewAdapter searchView, final String cityName) {
        if(GlobalData.cities == null)
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

                    City[] arrayCities = new City[cities.size()];
                    for(int i = 0; i < cities.size(); i++){
                        arrayCities[i] = cities.get(i);
                    }

                    GlobalData.setCities(arrayCities);

                    ArrayAdapter<City> adapter = new ArrayAdapter<City>(context, android.R.layout.simple_dropdown_item_1line, arrayCities);
                    searchView.setText(cityName);
                    searchView.setAdapter(adapter);
                }
            }).execute(URL + "cities");
        else{
            ArrayAdapter<City> adapter = new ArrayAdapter<City>(context, android.R.layout.simple_dropdown_item_1line, GlobalData.cities);
            searchView.setText(cityName);
            searchView.setAdapter(adapter);
        }
    }


    public List<WaterSource> getWaterAllSourcesFromCity(long idCity) {
        final List<WaterSource> waterSources = new ArrayList<>();
            new Connector(context, new Connector.Response() {
                @Override
                public void handleResponse(JSONArray output) {
                    if(output == null)
                        return;

                    for(int i=0; i < output.length(); i++){
                        try {
                            //String gsonS = "[{"id":72,"name":"Epitácio Pessoa","measurementUnit":"m³","capacity":4.11686272E8,"type":"Açude","waterSourceMeasurements":[{"id":91,"value":3.6371552E7,"date":1466564400000}]}]""
                            WaterSource waterSource = gson.fromJson(output.getJSONObject(i).toString(), WaterSource.class);
                            waterSources.add(waterSource);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).execute(URL + "cities/"+idCity+"/watersources");
        return waterSources;
    }

}
