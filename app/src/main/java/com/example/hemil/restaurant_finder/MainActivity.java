package com.example.hemil.restaurant_finder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    protected double lattitude = 0;
    protected double longitude = 0;
    protected String term;
    protected LinearLayout linearLayout;
    protected ListView listView;
    private static final int MY_APP_PERMISSION = 1;
    private EditText search_edittext_main_screen;
    private TextView textview_current_location;
    private MainMenuQueries mainMenuQueries;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        mainMenuQueries = new MainMenuQueries(this);

        linearLayout = (LinearLayout) findViewById(R.id.homeCategoryLayout);
        listView =  (ListView) findViewById(R.id.resultListView);
        textview_current_location = (TextView) findViewById(R.id.textview_current_location);
        search_edittext_main_screen = (EditText) findViewById(R.id.search_edittext_main_screen);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

//       findCurrentLocation();
    }

    private void findCurrentLocation() {

        final int GOOGLE_API_CLIENT_ID = 0;

        GoogleApiClient mGoogleApiClient;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .build();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_APP_PERMISSION);



                PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                        .getCurrentPlace(mGoogleApiClient, null);


                result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                    @Override
                    public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                            Log.d("Current Place", String.format("Place '%s' with " +
                                            "likelihood: %g",
                                    placeLikelihood.getPlace().getName(),
                                    placeLikelihood.getLikelihood()));
                        }
                        likelyPlaces.release();
                    }
                });

            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_search) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
      //      Toast.makeText(MainActivity.this, "Search Fragment!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_fav) {
            Intent intent = new Intent(this,FavoriteActivity.class);
            startActivity(intent);
      //      Toast.makeText(MainActivity.this, "Favourites Fragment!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void searchResults(View view) throws JSONException {

        if(search_edittext_main_screen.getText() != null && lattitude!=0 && longitude!=0 ) {

            term = search_edittext_main_screen.getText().toString();
            Log.d("Term", term);
            Log.d("Lattitude", "" + lattitude);
            Log.d("Longitude", "" + longitude);

            String latt = ""+lattitude;
            String longi = ""+longitude;
  //          new FindPlacesAsync(this).execute(term,latt,longi);

            showListView(term,latt,longi,0);

        }
        else
            Toast.makeText(MainActivity.this, "Please check input!", Toast.LENGTH_SHORT).show();
    }

    private void showListView(String term, String latt, String longi, int sort) throws JSONException {
        linearLayout.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        String result;
//        String latt = ""+lattitude;
//        String longi = ""+longitude;
        String response = null;
        try {
            response = (new FindPlacesAsync(this).execute(term, latt, longi,""+sort)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    //    Log.d("Main UI Response",response);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("businesses");

        JSONObject[] JOarray;

        if(jsonArray.length()>0) {
            JOarray = new JSONObject[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JOarray[i] = (JSONObject) jsonArray.get(i);
                Log.d(""+i,""+JOarray[i]);
            }

            listView.setAdapter(new CustomListAdapter(this, JOarray));

        }
        else
            Toast.makeText(MainActivity.this, "No results Found!", Toast.LENGTH_SHORT).show();
    }

    public void showRestaurants(View view) throws JSONException {

        showListView("restaurants","37.279518","-121.867905",0);
       // mainMenuQueries.showRestaurants();
    }

    public void showBars(View view) throws JSONException {
        mainMenuQueries.showBars();
        showListView("Bars", "37.279518", "-121.867905",0);
    }

    public void showCoffee(View view) throws JSONException {
        mainMenuQueries.showCoffee();
        showListView("coffee", "37.279518", "-121.867905",0);
    }

    public void showOrder(View view) throws JSONException {
        mainMenuQueries.showOrder();
        showListView("order online", "37.279518", "-121.867905",0);
    }

    public void showReservation(View view) throws JSONException {
        mainMenuQueries.showReserve();
        showListView("reservation", "37.279518", "-121.867905",0);
    }

    public void showPubs(View view) throws JSONException {
        mainMenuQueries.showPub();
        showListView("pubs", "37.279518", "-121.867905",0);
    }

    public void chooseLocation(View view) {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            builder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);

            Intent intent = builder.build(MainActivity.this);
            startActivityForResult(intent, 1);

        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Place place = PlacePicker.getPlace(this, data);
            LatLng latLng = place.getLatLng();

            lattitude = latLng.latitude;
            longitude = latLng.longitude;

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }
            if(name != null)
            textview_current_location.setText(name);
            else
                textview_current_location.setText(latLng.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hemil.restaurant_finder/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hemil.restaurant_finder/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
