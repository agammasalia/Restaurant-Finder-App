package com.example.hemil.restaurant_finder;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hemil.restaurant_finder.DBHelper.DBHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by hemil on 3/26/2016.
 */
public class FavoriteActivity extends AppCompatActivity {

    ListView listView;

//    static class Holder {
//        ImageView businessImage, star_one, star_two, star_three, star_four, star_five;
//        TextView businessName, reviewCount, address, category, ratingView;
//        String url;
//    }

    ImageView businessImage;
    TextView businessName, reviewCount, address, category, ratingView;
    String url;
    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.favorite_activity);

        setTitle("Restaurant Finder");

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }else
        getSupportActionBar().setIcon(R.drawable.app_icon);
        listView = (ListView) findViewById(R.id.fav_listview);
        listView.setAdapter(new FavAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage("Delete Favorite")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                                Log.d("Delete", "Fire");
                            }
                        });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

     class FavAdapter extends BaseAdapter {
        //    Holder holder = new Holder();
        Cursor cursor = new DBHelper(getApplicationContext()).getAllData();

        @Override
        public int getCount() {
            return new DBHelper(getApplicationContext()).getCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            JSONObject jsonObject = null;
            cursor.moveToFirst();
            while (position-- != 0) {
                cursor.moveToNext();
            }
            String result = cursor.getString(1);

            try {
                jsonObject = new JSONObject(result);
                Log.d("RESULT", jsonObject.toString());

                //    try {
                //         holder.url = jsonObject.get("image_url").toString();
                //          new DownloadImageTask().execute(jsonObject.get("image_url").toString());
                //            new DownloadImageTask().execute(jsonObject.get("image_url").toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            View view = null;

            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext()
                        .LAYOUT_INFLATER_SERVICE);

                view = layoutInflater.inflate(R.layout.single_fav, null);
                ratingView = (TextView) view.findViewById(R.id.ratingText1);
                category = (TextView) view.findViewById(R.id.categoryTextView1);
                businessName = (TextView) view.findViewById(R.id.businessName1);
                reviewCount = (TextView) view.findViewById(R.id.reviewCountTextView1);
                address = (TextView) view.findViewById(R.id.addressTextView1);

                //     view.setTag(holder);
            }
            //  else holder = (Holder) view.getTag();


            try {

                JSONArray categoryArray = (JSONArray) jsonObject.get("categories");
                String categoryString = "";
                for (int i = 0; i < categoryArray.length(); i++) {
                    JSONArray x = (JSONArray) categoryArray.get(i);
                    categoryString += " " + x.get(0);
                }

                JSONObject addressJson = ((JSONObject) (jsonObject.get("location")));
                String addr = addressJson.get("address").toString() + ", " +
                        addressJson.get("city").toString() + ", " + addressJson.get("state_code") + ", " + addressJson.get("postal_code");
                ;

                ratingView.setText("User Rating: " + jsonObject.get("rating").toString() + " out of ");
                businessName.setText(jsonObject.get("name").toString());
                category.setText(categoryString);
                reviewCount.setText(jsonObject.get("review_count").toString());
                address.setText(addr);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return view;
            // }
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

            //    Holder holder;

//        public DownloadImageTask(Holder holder) {
//            this.holder = holder;
//        }

            String position;

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                //         businessImage.setImageBitmap(result);
            }
        }
    }
}
