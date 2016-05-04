package com.example.hemil.restaurant_finder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hemil on 3/26/2016.
 */
public class DetailsFragment extends Fragment {

    TextView restaurantName, reviewCount, details_category, details_phone;
  //  String name,count, category, phone;

    public static DetailsFragment newInstance(){
        DetailsFragment df = new DetailsFragment();
        return df;
    }
//    public DetailsFragment(String name, String count, String category, String phone){
//        this.name = name;
//        this.count = count;
//        this.category = category;
//        this.phone = phone;
//    }

    @Override
    public void onCreate(Bundle bd){
        super.onCreate(bd);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup,Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.details_screen_single,viewGroup,false);

        restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
        reviewCount = (TextView) view.findViewById(R.id.detail_review_count);
        details_category = (TextView) view.findViewById(R.id.details_category);
        details_phone = (TextView) view.findViewById(R.id.details_phone);

        Bundle bundle = this.getArguments();
        if(bundle==null){
            Log.d("Null","NUll");
        }
//        Log.d("Fragment",bundle.getString("name"));
//        restaurantName.setText(getArguments().getString("name"));
//        restaurantName.setText(name);
//        reviewCount.setText(count);
//        details_category.setText(category);
//        details_phone.setText(phone);
//
//        Log.d("Fragment", name+" "+count+" "+category+" " +phone);

        return view;
    }

    public void setLayout(String name, String count, String category, String phone) {
        restaurantName.setText(name);
        reviewCount.setText(count);
        details_category.setText(category);
        details_phone.setText(phone);


 }
}
