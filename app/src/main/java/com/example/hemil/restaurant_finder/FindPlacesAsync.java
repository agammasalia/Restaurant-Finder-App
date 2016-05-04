package com.example.hemil.restaurant_finder;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hemil on 3/16/2016.
 */
class FindPlacesAsync extends AsyncTask<String, String, String> {

    Context context;
 //   private String[] params;

    public FindPlacesAsync(Context context){
        this.context = context;
    }
        String response;
    JSONArray jsonArray;
    JSONObject jsonObject;
    @Override
    public String doInBackground(String... params) {

        Yelp yelp = Yelp.getYelp(context);
        String businesses = yelp.search(params[0], Double.parseDouble(params[1]), Double.parseDouble(params[2]),Integer.parseInt(params[3]));

        Log.d("Businesses", businesses);

        return businesses;
    }

    protected void onProgressUpdate(){

    }

    protected void onPostExecute(){

    }



}
