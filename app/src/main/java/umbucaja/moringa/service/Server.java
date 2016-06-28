package umbucaja.moringa.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import umbucaja.moringa.adapter.WaterSourceRecyclerAdapter;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.entity.WaterSource;
import umbucaja.moringa.util.GlobalData;

/**
 * Created by jordaoesa on 17/06/2016.
 */
public class Server {

    private final String URL = "http://150.165.98.43:8080/";
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

    public void populateToolbarCities(final SearchViewAdapter searchView) {
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
                    searchView.setAdapter(adapter);
                }
            }).execute(URL + "cities");
        else{
            ArrayAdapter<City> adapter = new ArrayAdapter<City>(context, android.R.layout.simple_dropdown_item_1line, GlobalData.cities);
            searchView.setAdapter(adapter);
        }
    }


    public void getWaterAllSourcesFromCity(final RecyclerView waterSourcesRecyclerView, long idCity) {
            new Connector(context, new Connector.Response() {
                @Override
                public void handleResponse(JSONArray output) {
                    if(output == null)
                        return;
                    List<WaterSource> list = new ArrayList<WaterSource>();
                    for(int i=0; i < output.length(); i++){
                        try {
                            WaterSource waterSource = gson.fromJson(output.getJSONObject(i).toString(), WaterSource.class);
                            list.add(waterSource);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    WaterSourceRecyclerAdapter waterSourceRecyclerAdapter = new WaterSourceRecyclerAdapter(context, list);
                    waterSourcesRecyclerView.setAdapter(waterSourceRecyclerAdapter);

                }
            }).execute(URL + "cities/"+idCity+"/watersources");
    }

    public City getCityByName(String cityName){
        if(GlobalData.cities != null){
            for (int i=0; i < GlobalData.cities.length; i++){
                if(GlobalData.cities[i].getName().equals(cityName)){
                    return GlobalData.cities[i];
                }
            }
        }
        return null;
    }

}
