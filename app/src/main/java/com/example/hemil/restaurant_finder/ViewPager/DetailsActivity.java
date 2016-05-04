package com.example.hemil.restaurant_finder.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.hemil.restaurant_finder.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hemil on 3/26/2016.
 */
public class DetailsActivity extends FragmentActivity{

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    Intent intent;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        intent = getIntent();
//        try {
//            jsonObject = new JSONObject(intent.getStringExtra("JSONObject"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DetailsPageFragment detailsPageFragment = null;

            try {
                detailsPageFragment = new DetailsPageFragment().newInstance(intent.getStringExtra("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return detailsPageFragment;
        }

        @Override
        public int getCount() {
            intent = getIntent();
            Log.d("Intent",intent.getExtras().getString("JSONObject"));
            Log.d("Intent",""+intent.getExtras().getInt("size"));
            return intent.getExtras().getInt("size", 10);
        }
    }
}
