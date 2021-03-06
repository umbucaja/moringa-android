package umbucaja.moringa.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jordaoesa on 20/06/2016.
 */
public class Connector extends AsyncTask<String, Void, JSONArray> {

    private final String DEBUG_TAG = "CONNECTOR";
    private Context context;
    private Response response;
    private ProgressDialog progressDialog;

    public Connector(Context context, Response response){
        this.context = context;
        this.response = response;
    }

    @Override
    protected void onPreExecute() {
        if(context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Requisitando dados...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected JSONArray doInBackground(String... urls) {
        HttpURLConnection urlConnection = null;
        JSONArray jsonArray = new JSONArray();

        try {
            URL url = new URL(urls[0]);
            System.out.println(url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            String line = bufferedReader.readLine();
            if(line==null){
                line = "";
            }
            jsonArray = new JSONArray(line);

        } catch (IOException e) {
            Log.e(DEBUG_TAG, e.getMessage());
        } catch (JSONException e) {
            Log.e(DEBUG_TAG, e.getMessage());
        } finally {
            urlConnection.disconnect();
        }
        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray output) {
        if(progressDialog != null)
            progressDialog.dismiss();
        response.handleResponse(output);
    }

    public interface Response{
        void handleResponse(JSONArray output);
    }
}