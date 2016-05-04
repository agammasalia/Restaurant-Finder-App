package com.example.hemil.restaurant_finder.ViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hemil.restaurant_finder.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hemil on 3/26/2016.
 */
public class DetailsPageFragment extends Fragment {

    String businessName,rating,reviewCount,category,phone,snippet;
    String lat,lon;

//    public static DetailsPageFragment newInstance(JSONObject jsonObject) throws JSONException {
public static DetailsPageFragment newInstance(String name) throws JSONException {

    DetailsPageFragment detailsPageFragment = new DetailsPageFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("name",(String)jsonObject.get("name"));
//        bundle.putString("rating",jsonObject.get("rating").toString());
//        bundle.putString("phone",jsonObject.get("phone").toString());
//        bundle.putString("reviewCount",jsonObject.get("review_count").toString());
//        bundle.putString("lattitude",((JSONObject)jsonObject.get("coordinate")).get("lattitude").toString());
//        bundle.putString("longitude",((JSONObject)jsonObject.get("coordinate")).get("longitude").toString());
//        bundle.putString("snippet",jsonObject.get("snippet_text").toString());
            bundle.putString("name",name);
       detailsPageFragment.setArguments(bundle);

        return detailsPageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstateState){
        super.onCreate(savedInstateState);
        businessName = getArguments().getString("name");
//        rating = getArguments().getString("rating");
//        reviewCount = getArguments().getString("reviewCount");
//        phone = getArguments().getString("phone");
//        snippet = getArguments().getString("snippet");
//        lat = getArguments().getString("lattitude");
//        lon = getArguments().getString("longitude");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.details_screen_single, container, false);

        return rootView;
    }
}
