package com.system.odering.front_end.activities;

/**
 * Created by Chase Agulhas on 2016/11/21.
 */

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.system.odering.front_end.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CartTab extends Fragment {

    int totPrice;
    int totItem;
    View rootView;
    ListView searchResults;
    MyListAdapter2 myListAdapter;
    TextView totalPrice;
    TextView totalItems;
    //TextView empty;
    //Data Pulled from the server into an arraylist
    ArrayList<UserFoodItem> productResullts;

    //Based on the search string certain filtered resuts will be displayed
    //ArrayList<String> filteredProductResults = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cart_tab, container, false);

        productResullts = getListResults();
        generateListContent();

        myListAdapter = new MyListAdapter2(getContext(), productResullts);
        //empty = (TextView) rootView.findViewById((R.id.cart_empty));

        searchResults = (ListView) rootView.findViewById(R.id.cart_tab_customer_list_view);

        //searchResults.setEmptyView(empty);
        searchResults.setAdapter(myListAdapter);

        return rootView;
    }

    @Override
    public void onResume() {

        TextView totalPrice = (TextView) rootView.findViewById(R.id.total_amount);
        TextView totalItems = (TextView) rootView.findViewById(R.id.item_count);

        totItem = productResullts.size();
        totPrice = 0;
        //calculate total price
        for(int i=0; i < productResullts.size(); i++){
            totPrice = totPrice + Integer.parseInt(productResullts.get(i).getPrice());
        }

        searchResults.setAdapter(myListAdapter);
        totalItems.setText("("+totItem+")");
        totalPrice.setText(""+totPrice+".00");
        super.onResume();

    }

    private void generateListContent() {
        for (int i = 0; i <= productResullts.size() - 1; i++) {
            FoodItem foodItem = productResullts.get(i).getProduct();
            productResullts.add(new UserFoodItem(foodItem, foodItem.getPrice(), 1));
        }
    }

    public ArrayList<UserFoodItem> getListResults() {
        return ((FastFoodTabs) getContext()).getCartList();
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, UserFoodItem[]> {

        UserFoodItem[] response = new UserFoodItem[100];

        @Override
        protected UserFoodItem[] doInBackground(Void... params) {
            try{
                final String url = "http://localhost:8080/api/users/verify/{username}/{password}";
                RestTemplate rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                UserFoodItem[] fooditems = rest.getForObject(url, UserFoodItem[].class);
                return fooditems;

            }catch(HttpClientErrorException loginError) {

            }catch(Exception e){
                System.out.println("ERROR: SEAND/RECEIVE_REQUEST - " + e);
            }
            ArrayList<UserFoodItem> temp = ((FastFoodTabs)getContext()).getCartList();
            if(temp.size() > 0) {
                System.out.println("################## wer");
                for (int i = 0; i < 100; i++) {
                    response[i] = (new UserFoodItem(temp.get(i).getProduct(), "0.00", 1));
                }
            }
            else{
                FoodItem foodItem = new FoodItem("Testerrrr", "1.00", 1);
                response[0] = (new UserFoodItem(foodItem, foodItem.getPrice(), 1));
                System.out.println("+++++++++++++++++10000000000000");
            }
            return response;
        }
    }

}

