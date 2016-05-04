package com.example.hemil.restaurant_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hemil.restaurant_finder.DBHelper.DBHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by hemil on 3/26/2016.
 */
public class DetailsLayoutActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView restname, address, reviewCount, rating, snippet, category, phone ,ratings;
    ImageView heart;
    boolean flag = false;
    double star,latt,longi;
    JSONObject jsonObject;

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.details_screen_single);

        setTitle("Restaurant Finder");
        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else
        getSupportActionBar().setIcon(R.drawable.app_icon);

        restname = (TextView) findViewById(R.id.restaurant_name);
        reviewCount = (TextView) findViewById(R.id.detail_review_count);
        category = (TextView) findViewById(R.id.details_category);
        phone = (TextView) findViewById(R.id.details_phone);
        snippet = (TextView) findViewById(R.id.detail_snippet);
        address = (TextView) findViewById(R.id.detail_address);
        heart = (ImageView) findViewById(R.id.heart);
        ratings = (TextView) findViewById(R.id.ratings);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        try {
             jsonObject  = new JSONObject(intent.getStringExtra("json"));

            JSONObject addressJson = ((JSONObject)(jsonObject.get("location")));
           String addr = addressJson.get("address").toString()  + ", "+
                   addressJson.get("city").toString()+ ", " + addressJson.get("state_code") + ", "+addressJson.get("postal_code");
                   ;
     //       Log.d("address",addr);
            restname.setText(jsonObject.get("name").toString());
        reviewCount.setText(jsonObject.get("review_count").toString()+" reviews");
        address.setText(addr);
        category.setText(intent.getStringExtra("category"));
        phone.setText("Phone: "+jsonObject.get("phone").toString());
        star = Double.parseDouble(jsonObject.get("rating").toString());
        snippet.setText(jsonObject.get("snippet_text").toString());
            ratings.setText("User Ratings: "+jsonObject.get("rating").toString()+" out of,");

            JSONObject coo = (JSONObject) ((JSONObject) jsonObject.get("location")).get("coordinate");
            latt = (double)(coo.get("latitude"));
            longi = (double)(coo.get("longitude"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(latt, longi);

        Log.d("L",""+latt+" "+longi);
        Log.d("LatLng", sydney.toString());

        try {
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in "+jsonObject.get("name").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));    }

    public boolean dbOps(View view) throws JSONException {
        DBHelper dbHelper = new DBHelper(this);;
        if(!flag){
            flag = true;
            heart.setImageResource(R.drawable.rh);
          boolean t = dbHelper.addDatabase(jsonObject.get("name").toString(), jsonObject.toString());
            dbHelper.getCount();
            return t;
        }
        else
        {
            flag = false;
            heart.setImageResource(R.drawable.wh);
            dbHelper.deleteData(jsonObject.get("name").toString());
            dbHelper.getCount();
            return true;
        }

    }
}

