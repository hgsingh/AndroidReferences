package com.singh.harsukh.jsonparsing;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip%3D%2794085%27%20and%20query%3D%27pizza%27&format=json&callback=";
    private static ArrayList<String> pizzalocations;
    private static ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pizzalocations = new ArrayList<String>();
        mListView = (ListView) findViewById(R.id.listView);
        DownloadJson downloadJson = new DownloadJson();
        downloadJson.execute();
    }

    public class DownloadJson extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL urlobj = new URL(SERVER_URL);
                //buffered reader will read everything into a input stream buffer and specify size
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlobj.openConnection().getInputStream(), "UTF-8"), 256);
                String jsonString = reader.readLine();
                //JSONObject jsonObject = new JSONObject()
                //Log.d("JSON", jsonString);
                JSONObject jsonObject = new JSONObject(jsonString);

                JSONObject queryObj = jsonObject.getJSONObject("query");
                JSONObject resultsObj = queryObj.getJSONObject("results");
                JSONArray resultArray = resultsObj.getJSONArray("Result");

                for(int i=0;i<resultArray.length(); i++){
                    JSONObject theObject = resultArray.getJSONObject(i);
                    Log.d("JSON", theObject.getString("Title"));
                    pizzalocations.add(theObject.getString("Title"));
                }
            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e) {
               e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void param) {
            super.onPostExecute(param);
            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, pizzalocations);
            mListView.setAdapter(adapter);
        }
    }
}
