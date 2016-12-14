package com.system.odering.front_end.activities;

/**
 * Created by Chase Agulhas on 2016/11/21.
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpRequest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static project.chase.fastfood.R.id.txt_login_email;

public class MenuTab extends Fragment {

    View rootView;
    ListView searchResults;
    MyListAdapter myListAdapter;
    Button addToCart;
    //Data Pulled from the server into an arraylist
    ArrayList<FoodItem> productResullts = new ArrayList<FoodItem>();

    //Based on the search string certain filtered resuts will be displayed
    //ArrayList<String> filteredProductResults = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.menu_tab, container, false);
        myListAdapter = new MyListAdapter(getContext(), productResullts);

        searchResults = (ListView) rootView.findViewById(R.id.menu_list_view);
        generateListContent();
        //searchResults.setAdapter(new MyListAdapter(getContext(), productResullts));
        searchResults.setAdapter(myListAdapter);

        return rootView;
    }

    private void generateListContent() {
        /*for (int i = 0; i < 10; i++) {
            productResullts.add(new FoodItem("Test"+i, "R "+i, i));
        }*/
        /*for (int i = 0; i <= 100; i++) {
            productResullts.add(new FoodItem("Test"+i, "R: 1", 1));
        }*/
        try {

            FoodItem [] foodItemArray = new HttpRequestTask().execute().get();
            for (int i = 0; i < foodItemArray.length; i++) {
                productResullts.add(foodItemArray[i]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, FoodItem[]> {

        FoodItem[] response = new FoodItem[80];

        @Override
        protected FoodItem[] doInBackground(Void... params) {
            try{
                final String url = "http://localhost:8080/api/users/verify/{username}/{password}";
                RestTemplate rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                FoodItem[] fooditems = rest.getForObject(url, FoodItem[].class);
                return fooditems;

            }catch(HttpClientErrorException loginError) {

            }catch(Exception e){
                System.out.println("ERROR: SEAND/RECEIVE_REQUEST - " + e);
            }
            for (int i = 0; i < 80; i++) {
                response[i] = (new FoodItem("Test"+i, "1", 1));
            }
            return response;
        }
    }

    /*private class MyListAdapter extends ArrayAdapter<FoodItem>{
        //private int layout;
        public MyListAdapter(Context context, List<FoodItem> objects) {
            super(context, R.layout.custom_list_view, objects);
            //layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View OrderView = convertView;
            //ViewHolder mainViewHolder = null;
            if(OrderView == null){
                LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); //LayoutInflater.from(getContext());
                OrderView = layoutInflater.inflate(R.layout.custom_list_view, parent, false);
                /*ViewHolder viewHolder = new ViewHolder();
                viewHolder.productImage = (ImageView) convertView.findViewById(R.id.custom_list_view_product_image);
                viewHolder.productName = (TextView) convertView.findViewById(R.id.custom_list_view_product_name);
                viewHolder.size = (TextView) convertView.findViewById(R.id.custom_list_view_product_size_value);
                viewHolder.price = (TextView) convertView.findViewById(R.id.custom_list_view_product_price_value);
                viewHolder.addToCart = (Button) convertView.findViewById(R.id.btn_add_to_cart);
                viewHolder.addToCart.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Button was clicked for item number " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(viewHolder);*/


            /*else{
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.productName.setText(getItem(position));
            }*/

            /*OrderView.setTag(getItem(position));

            ImageView productImage = (ImageView) convertView.findViewById(R.id.custom_list_view_product_image);
            TextView productName = (TextView) convertView.findViewById(R.id.custom_list_view_product_name);
            TextView size = (TextView) convertView.findViewById(R.id.custom_list_view_product_size_value);
            TextView price = (TextView) convertView.findViewById(R.id.custom_list_view_product_price_value);
            Button addToCart = (Button) convertView.findViewById(R.id.btn_add_to_cart);

            return convertView;
        }
    }*/

    /*public class ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView size;
        TextView price;
        Button addToCart;
    }*/

}