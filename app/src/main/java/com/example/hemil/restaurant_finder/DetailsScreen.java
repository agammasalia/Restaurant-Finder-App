package com.example.hemil.restaurant_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hemil on 3/26/2016.
 */
public class DetailsScreen extends FragmentActivity {

    JSONObject jsonObject;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen_activity);

        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null){
                return;
            }
        }
        Intent intent = getIntent();
//        DetailsFragment detailsFragment = new DetailsFragment(intent.getStringExtra("name"),intent.getStringExtra("count"),
//                intent.getStringExtra("category"),intent.getStringExtra("phone"));


 //       Log.d("DetailsCreen",intent.getStringExtra("name"));

//        DetailsFragment detailsFragment = new DetailsFragment();
//        detailsFragment.setLayout(intent.getStringExtra("name"),intent.getStringExtra("count"),
//                intent.getStringExtra("category"),intent.getStringExtra("phone"));


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //    Log.d("DetailScreen",intent.getStringExtra("name"));
        Log.d("DEtailScfreen", intent.getStringExtra("name"));
        Bundle bundle = new Bundle();
       bundle.putString("name", intent.getStringExtra("name"));

    //    DetailsFragment detailsFragment = new DetailsFragment().newInstance();
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        ft.replace(R.id.fragment_container, detailsFragment);
        ft.commit();
    }


}
