package com.example.hemil.restaurant_finder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hemil.restaurant_finder.ViewPager.DetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;

/**
 * Created by hemil on 3/24/2016.
 */
public class CustomListAdapter extends BaseAdapter {

    JSONObject[] jsonArray;
    Context context;
    LayoutInflater layoutInflater = null;
    static ViewHolder viewHolder = new ViewHolder();
//    ImageView businessImage, star_one,star_two,star_three,star_four,star_five;
//    TextView businessName, reviewCount, address, category;

    static class ViewHolder{
        ImageView businessImage, star_one,star_two,star_three,star_four,star_five;
        TextView businessName, reviewCount, address, category, ratingView;
    }

    public CustomListAdapter(Context context, JSONObject[] jsonArray){
        this.context = context;
        this.jsonArray = jsonArray;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return jsonArray.length;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

 //       ViewHolder viewHolder = new ViewHolder();
        View view = null;

        try {
            if(view == null) {
                view = layoutInflater.inflate(R.layout.single_result, null);
                viewHolder.businessImage = (ImageView) view.findViewById(R.id.resultImageView);

//                viewHolder.star_one = (ImageView) view.findViewById(R.id.star_one);
//                viewHolder.star_two = (ImageView) view.findViewById(R.id.star_two);
//                viewHolder.star_three = (ImageView) view.findViewById(R.id.star_three);
//                viewHolder.star_four = (ImageView) view.findViewById(R.id.star_four);
//                viewHolder.star_five = (ImageView) view.findViewById(R.id.star_five);


                viewHolder.ratingView = (TextView) view.findViewById(R.id.ratingText);
                viewHolder.category = (TextView) view.findViewById(R.id.categoryTextView);
                viewHolder.reviewCount = (TextView) view.findViewById(R.id.reviewCountTextView);
                viewHolder.businessName = (TextView) view.findViewById(R.id.businessName);
                viewHolder.address = (TextView) view.findViewById(R.id.addressTextView);
                view.setTag(viewHolder);
            }
            else
            viewHolder =(ViewHolder) view.getTag();

            JSONArray categoryArray = (JSONArray)jsonArray[position].get("categories");

            String categoryString = "";
            for (int i = 0; i < categoryArray.length(); i++ ){
                JSONArray x = (JSONArray)categoryArray.get(i);
                categoryString += " "+x.get(0);
            }
            viewHolder.ratingView.setText(" User rating: "+jsonArray[position].get("rating").toString()+" out of ");
            viewHolder.category.setText(categoryString);

            final String star =  jsonArray[position].get("rating").toString();
            Log.d("Star",""+position + "   "+star);
//            switch (star){
//                case "0" : starOne(0); starTwo(0); starThree(0); starFour(0); starFive(0); break;
//                case "1" : starOne(1); starTwo(0); starThree(0); starFour(0); starFive(0); break;
//                case "1.5" : starOne(1); starTwo(1); starThree(0); starFour(0); starFive(0); break;
//                case "2" :starOne(1); starTwo(2); starThree(0); starFour(0); starFive(0); break;
//                case "2.5" : starOne(1); starTwo(2); starThree(1); starFour(0); starFive(0); break;
//                case "3" : starOne(1); starTwo(2); starThree(2); starFour(0); starFive(0); break;
//                case "3.5" : starOne(1); starTwo(2); starThree(3); starFour(1); starFive(0); break;
//                case "4" : starOne(1); starTwo(2); starThree(2); starFour(2); starFive(0); break;
//                case "4.5" : starOne(1); starTwo(2); starThree(2); starFour(2); starFive(1); break;
//                case "5" : starOne(1); starTwo(2); starThree(2); starFour(2); starFive(2); break;
//            }



            viewHolder.businessName.setText("" + (position + 1) + ". " + jsonArray[position].get("name"));
            viewHolder.businessName.setTypeface(null,Typeface.BOLD);

            viewHolder.reviewCount.setText(""+jsonArray[position].get("review_count")+" reviews");

            JSONArray addressJson = (JSONArray)((JSONObject)(jsonArray[position].get("location"))).get("display_address");
            String addr ="";
         for(int i = 0; i < addressJson.length()-1; i++){
             if(i == 0){
                 addr += addressJson.get(i);
                 continue;
             }
            addr += ", "+addressJson.get(i);
         }

            viewHolder.address.setText(addr);
    //        Picasso.with(context).load(Uri.parse(jsonArray[position].get("image_url").toString())).into(businessImage);

            new DownloadImageTask().execute(jsonArray[position].get("image_url").toString());


            final String finalCategoryString = categoryString;
            final String finalAddr = addr;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DetailsLayoutActivity.class );
                    intent.putExtra("category", finalCategoryString);
                    intent.putExtra("json",jsonArray[position].toString());
                    context.startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    void starOne(int i){
        if(i == 0)
            viewHolder.star_one.setImageResource(R.drawable.a10x10_0);
        else
            viewHolder.star_one.setImageResource(R.drawable.a10x10_1);
    }

    void starTwo(int i){
        if(i == 0)
            viewHolder.star_two.setImageResource(R.drawable.a10x10_0);
        else if( i ==1)
            viewHolder.star_two.setImageResource(R.drawable.a10x10_1_5);
        else
            viewHolder.star_two.setImageResource(R.drawable.a10x10_2);
    }

    void starThree(int i){
        if(i == 0)
            viewHolder.star_three.setImageResource(R.drawable.a10x10_0);
        else if( i ==1)
        viewHolder.star_three.setImageResource(R.drawable.a10x10_2_5);
        else
            viewHolder.star_three.setImageResource(R.drawable.a10x10_3);
    }

    void starFour(int i){
        if(i == 0)
            viewHolder.star_four.setImageResource(R.drawable.a10x10_0);
        else if(i == 1)
        viewHolder.star_four.setImageResource(R.drawable.a10x10_3_5);
        else
            viewHolder.star_four.setImageResource(R.drawable.a10x10_4);
    }

    void starFive(int i){
        if(i == 0)
            viewHolder.star_five.setImageResource(R.drawable.a10x10_0);
        else if(i == 1)
            viewHolder.star_five.setImageResource(R.drawable.a10x10_4_5);
        else
            viewHolder.star_five.setImageResource(R.drawable.a10x10_5);
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


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
            viewHolder.businessImage.setImageBitmap(result);
        }
    }

    }
