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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import umbucaja.moringa.adapter.ChuvasRecyclerAdapter;
import umbucaja.moringa.adapter.SearchViewAdapter;
import umbucaja.moringa.adapter.WaterSourceRecyclerAdapter;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.entity.MeasurementStation;
import umbucaja.moringa.entity.RainFallMeasurement;
import umbucaja.moringa.entity.WaterSource;
import umbucaja.moringa.entity.WaterSourceMeasurement;
import umbucaja.moringa.fragments.WaterSourceFragment;
import umbucaja.moringa.util.GlobalData;

/**
 * Created by jordaoesa on 17/06/2016.
 */
public class Server {

    //private final String URL = "http://150.165.98.43:8080/";
    private final String URL = "http://192.168.1.109:8080/";
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

    public void getWaterAllSourcesFromCity2(final WaterSourceFragment waterSourceFragment, int idCity) {
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

                if(waterSources.size()>0){
                    //waterSourceFragment.tvName.setText(waterSource.getType()+" "+waterSource.getName());
                    WaterSource waterSource = waterSources.get(0);
                    float capacity = waterSource.getCapacity();
                    List<WaterSourceMeasurement> wsms = waterSource.getReservoirMeasurements();

                    float percentage = 0;
                    float actualVolume = 0;

                    String date = "";
                    waterSourceFragment.getTvCurrentCapacity().setText(String.format("%.1f",capacity/(1000000)));
                   // holder.currentWaterSource = waterSource;
                    if(wsms!=null){
                        if(wsms.size()>0){
                            actualVolume = wsms.get(wsms.size()-1).getValue();
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            date = formatter.format(wsms.get(wsms.size()-1).getDate());

                        }
                    }
                    percentage = (actualVolume*100)/capacity;
                    waterSourceFragment.getTvActualWaterSourceVolume().setText(String.format("%.1f",actualVolume/1000000));
                    waterSourceFragment.getTvCurrentWaterSourcePercentage().setText(String.format("%.1f%s",percentage,"%"));
                    waterSourceFragment.getTvWaterSourceLastMeasurementDate().setText(date);
                    waterSourceFragment.getProgressBarWaterSource().setProgress((int)percentage);
                }
                System.out.println("=============================> "+waterSources);
            }
        }).execute(URL + "cities/"+idCity+"/watersources");
        //execute(URL + "watersources/"+idCity);
        //execute(URL + "cities/"+idCity+"/watersources");
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

    public void getMeasurementStationsFromCity(final RecyclerView recyclerView, long cityId) {
        List<MeasurementStation> list = new ArrayList<MeasurementStation>();
        List<RainFallMeasurement> list2 = new ArrayList<RainFallMeasurement>();
        list2.add(new RainFallMeasurement(0, new Date(), 50.2f, "mm"));
        MeasurementStation ms = new MeasurementStation(0, "Remigio", 1f, 1f);
        ms.setRainFallMeasurements(list2);
        list.add(ms);
        ChuvasRecyclerAdapter chuvasRecyclerAdapter = new ChuvasRecyclerAdapter(list);
        recyclerView.setAdapter(chuvasRecyclerAdapter);
        new Connector(context, new Connector.Response() {
            @Override
            public void handleResponse(JSONArray output) {
                if(output == null)
                    return;
                List<MeasurementStation> list = new ArrayList<MeasurementStation>();
                for(int i=0; i < output.length(); i++){
                    try {
                        MeasurementStation station = gson.fromJson(output.getJSONObject(i).toString(), MeasurementStation.class);
                        list.add(station);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ChuvasRecyclerAdapter chuvasRecyclerAdapter = new ChuvasRecyclerAdapter(list);
                recyclerView.setAdapter(chuvasRecyclerAdapter);

            }
        }).execute(URL + "cities/" + cityId + "/measurementstations");
    }
}
