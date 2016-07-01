package umbucaja.moringa.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import umbucaja.moringa.R;
import umbucaja.moringa.adapter.ChuvasMedicaoArrayAdapter;
import umbucaja.moringa.adapter.ChuvasRecyclerAdapter;
import umbucaja.moringa.adapter.SearchViewAdapter;
import umbucaja.moringa.adapter.WaterSourceRecyclerAdapter;
import umbucaja.moringa.entity.City;
import umbucaja.moringa.entity.MeasurementStation;
import umbucaja.moringa.entity.RainFallMeasurement;
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

    public void getMeasurementsFromStation(final View rootView, final GridView gridView, long stationId){
        List<RainFallMeasurement> measurements = new ArrayList<RainFallMeasurement>();
        measurements.add(new RainFallMeasurement(0, new Date(), 10, "mm"));
        measurements.add(new RainFallMeasurement(1, new Date(), 20, "mm"));
        measurements.add(new RainFallMeasurement(2, new Date(), 30, "mm"));
        measurements.add(new RainFallMeasurement(3, new Date(), 40, "mm"));
        measurements.add(new RainFallMeasurement(4, new Date(), 50, "mm"));
        measurements.add(new RainFallMeasurement(5, new Date(), 60, "mm"));
        measurements.add(new RainFallMeasurement(5, new Date(), 70, "mm"));
        measurements.add(new RainFallMeasurement(5, new Date(), 80, "mm"));
        measurements.add(new RainFallMeasurement(5, new Date(), 90, "mm"));

        Collections.sort(measurements, new Comparator<RainFallMeasurement>() {
            @Override
            public int compare(RainFallMeasurement rfm1, RainFallMeasurement rfm2) {
                return rfm1.getDate().compareTo(rfm2.getDate());
            }
        });
        RainFallMeasurement lastMeasurement = measurements.remove(measurements.size()-1);
        ImageView iv = (ImageView) rootView.findViewById(R.id.image_view_chuvas);
        TextView tvValue = (TextView)  rootView.findViewById(R.id.tv_chuvas_milimetragem);
        TextView tvDate = (TextView)  rootView.findViewById(R.id.tv_chuvas_last_measurement_date);
        if(lastMeasurement.getValue() == 0f)
            iv.setImageResource(R.drawable.moringa);
        else if(lastMeasurement.getValue() <= 10)
            iv.setImageResource(R.drawable.moringa);
        else if(lastMeasurement.getValue() <= 25)
            iv.setImageResource(R.drawable.moringa);
        else if(lastMeasurement.getValue() <= 50)
            iv.setImageResource(R.drawable.moringa);
        else if(lastMeasurement.getValue() > 50)
            iv.setImageResource(R.drawable.moringa);
        tvValue.setText(lastMeasurement.getValue()+"mm");
        String date = new SimpleDateFormat("dd/MM/yyyy").format(lastMeasurement.getDate());
        tvDate.setText(date);

        if(measurements.size() > 5)
            measurements = measurements.subList(measurements.size()-5, measurements.size());

        ChuvasMedicaoArrayAdapter adapter = new ChuvasMedicaoArrayAdapter(context, R.layout.grid_view_chuvas_item, measurements);
        gridView.setAdapter(adapter);
        /*new Connector(context, new Connector.Response() {
            @Override
            public void handleResponse(JSONArray output) {
                if(output == null)
                    return;

                List<RainFallMeasurement> measurements = new ArrayList<RainFallMeasurement>();
                for(int i=0; i < output.length(); i++){
                    try {
                        RainFallMeasurement measurement = gson.fromJson(output.getJSONObject(i).toString(), RainFallMeasurement.class);
                        measurements.add(measurement);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Collections.sort(measurements, new Comparator<RainFallMeasurement>() {
                    @Override
                    public int compare(RainFallMeasurement rfm1, RainFallMeasurement rfm2) {
                        return rfm1.getDate().compareTo(rfm2.getDate());
                    }
                });
                RainFallMeasurement lastMeasurement = measurements.remove(measurements.size()-1);
                ImageView iv = (ImageView) rootView.findViewById(R.id.image_view_chuvas);
                TextView tvValue = (TextView)  rootView.findViewById(R.id.tv_chuvas_milimetragem);
                TextView tvDate = (TextView)  rootView.findViewById(R.id.tv_chuvas_last_measurement_date);
                if(lastMeasurement.getValue() == 0f)
                    iv.setImageResource(R.drawable.moringa);
                else if(lastMeasurement.getValue() <= 10)
                    iv.setImageResource(R.drawable.moringa);
                else if(lastMeasurement.getValue() <= 25)
                    iv.setImageResource(R.drawable.moringa);
                else if(lastMeasurement.getValue() <= 50)
                    iv.setImageResource(R.drawable.moringa);
                else if(lastMeasurement.getValue() > 50)
                    iv.setImageResource(R.drawable.moringa);
                tvValue.setText(lastMeasurement.getValue()+"mm");
                String date = new SimpleDateFormat("dd/MM/yyyy").format(lastMeasurement.getDate());
                tvDate.setText(date);

                if(measurements.size() > 5)
                    measurements = measurements.subList(measurements.size()-5, measurements.size());

                ChuvasMedicaoArrayAdapter adapter = new ChuvasMedicaoArrayAdapter(context, R.layout.grid_view_chuvas_item, measurements);
                gridView.setAdapter(adapter);
            }
        }).execute(URL + "stations/" + stationId + "/measurements");*/
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
        MeasurementStation ms = new MeasurementStation(0, "Estação Cidade", 1f, 1f);
        ms.setRainFallMeasurements(list2);
        list.add(ms);
        ChuvasRecyclerAdapter chuvasRecyclerAdapter = new ChuvasRecyclerAdapter(list);
        recyclerView.setAdapter(chuvasRecyclerAdapter);
        /*new Connector(context, new Connector.Response() {
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
        }).execute(URL + "cities/" + cityId + "/measurementstations");*/
    }
}
