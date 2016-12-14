package com.system.odering.front_end.activities;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.system.odering.front_end.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chase Agulhas on 2016-11-30.
 */

public class FastFoodTabs extends AppCompatActivity implements Communicator  {


    private ArrayList<UserFoodItem> userFoodItems;
    private String loggedInUser;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_food_tabs);

        userFoodItems = new ArrayList<UserFoodItem>();
        loggedInUser = getIntent().getStringExtra("email");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.getApplicationContext());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Fragment fragment = ((SectionsPagerAdapter)mViewPager.getAdapter()).getFragment(position);

                if (position ==1 && fragment != null)
                {
                    fragment.onResume();
                }
            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = ((SectionsPagerAdapter)mViewPager.getAdapter()).getFragment(position);

                if (position ==1 && fragment != null)
                {
                    fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fast_food_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_edit_user_details){
            Intent userDetails = new Intent(this, ProfileActivity.class);
            this.startActivity(userDetails);
        }
        if (id == R.id.action_log_out){
            Intent userDetails = new Intent(this, LoginActivity.class);
            this.startActivity(userDetails);
            //supposed to clear the backstack trace
            FragmentManager fm = this.getSupportFragmentManager();;
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public ArrayList<UserFoodItem> getCartList() {
        return userFoodItems;
    }

    @Override
    public void addToCartList(FoodItem foodItem, String loggedInUser) {
        Toast.makeText(this, "Product " + foodItem.getName() +" added to arraylist.", Toast.LENGTH_SHORT).show();
        userFoodItems.add(new UserFoodItem(foodItem, foodItem.getPrice(), 1));
        /*CartTab cartTab = (CartTab) getSupportFragmentManager().findFragmentById(R.id.myCheckList);
        cartTab.updateListView();*/
    }

    @Override
    public String getLoggedInUser() {
        return loggedInUser;
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;
        private Context mContext;

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mFragmentManager = fm;
            mContext = context;
            mFragmentTags = new HashMap<Integer, String>();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MenuTab();
                case 1:
                    return new CartTab();
                case 2:
                    return new CheckoutTab();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Menu";
                case 1:
                    return "Cart";
                case 2:
                    return "Checkout";
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                // record the fragment tag here.
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                mFragmentTags.put(position, tag);
            }
            return obj;
        }

        public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null)
                return null;
            return mFragmentManager.findFragmentByTag(tag);
        }

    }
}