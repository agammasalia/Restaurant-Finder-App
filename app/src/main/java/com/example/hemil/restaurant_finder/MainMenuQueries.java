package com.example.hemil.restaurant_finder;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hemil on 3/15/2016.
 */
public class MainMenuQueries {

    Context context;

    public MainMenuQueries(Context context){
        this.context = context;
    }
    public void showRestaurants(){
      //  new FindPlacesAsync(context).execute();
        Toast.makeText(context, "Restaurants", Toast.LENGTH_SHORT).show();
    }

    public void showBars(){
        Toast.makeText(context, "Bars", Toast.LENGTH_SHORT).show();
    }

    public void showCoffee(){
        Toast.makeText(context, "Coffee", Toast.LENGTH_SHORT).show();
    }

    public void showOrder(){
        Toast.makeText(context, "Order Pickup or Delivery", Toast.LENGTH_SHORT).show();
    }

    public void showReserve(){
        Toast.makeText(context, "Reservation", Toast.LENGTH_SHORT).show();
    }

    public void showPub(){
        Toast.makeText(context, "Pubs", Toast.LENGTH_SHORT).show();
    }

}
