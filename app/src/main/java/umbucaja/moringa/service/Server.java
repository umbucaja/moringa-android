package umbucaja.moringa.service;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import umbucaja.moringa.R;
import umbucaja.moringa.entity.City;

/**
 * Created by jordaoesa on 17/06/2016.
 */
public class Server {

    private final String URL = "http://192.168.1.105:8080/";
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
        this.gson = new Gson();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void populateTextViewWithCities(final FragmentActivity activity, final View rootView){
        new Connector(context, new Connector.Response() {
            @Override
            public void handleResponse(JSONArray output) {
                List<String> cities = new ArrayList<String>();
                for(int i=0; i < output.length(); i++){
                    try {
                        City city = gson.fromJson(output.getJSONObject(i).toString(), City.class);
                        cities.add(city.getName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(cities.toArray());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, (String[])cities.toArray());
                AutoCompleteTextView textView = (AutoCompleteTextView) rootView.findViewById(R.id.acudes_list);
                textView.setAdapter(adapter);

            }
        }).execute(URL + "cities");
    }


}
