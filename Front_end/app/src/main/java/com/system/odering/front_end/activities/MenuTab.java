package com.system.odering.front_end.activities;

/**
 * Created by Chase Agulhas on 2016/11/21.
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.system.odering.front_end.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

        searchResults = (ListView) rootView.findViewById(R.id.menu_list_view);
        myListAdapter = new MyListAdapter(getContext(), productResullts);

        //myListAdapter.notifyDataSetChanged();

        searchResults.setAdapter(myListAdapter);

        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        try {
            new HttpRequestTask(pref.getString("token", null), myListAdapter, productResullts).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return rootView;
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, Void> {


        String tokens;
        MyListAdapter a;
        ArrayList<FoodItem> b;

        public HttpRequestTask(String token, MyListAdapter mylistadapter, ArrayList<FoodItem> productResullts) {
            super();
            tokens = token;
            a = mylistadapter;
            b = productResullts;

        }

        @Override
        protected Void doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            Log.e("memessdsfg", tokens);
            Request request = new Request.Builder()
                    .url("http://192.168.10.107:8000/api/fooditems?token="+ tokens)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        String rbody = responseBody.string();
                        Log.e("1234", rbody);

                        Gson gson = new Gson();

                        JsonArray jsonArray = gson.fromJson(rbody, JsonArray.class);
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                            FoodItem fitem = new FoodItem(jsonObject.get("name").toString(), jsonObject.get("price").toString(), Integer.parseInt(jsonObject.get("amountAvailable").toString()));
                            Log.e("array",fitem.toString());
                            b.add(fitem);
                        }
                        MenuTab.this.getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                b =  productResullts;
                                a = new MyListAdapter(getContext(), b);
                                searchResults.setAdapter(a);
                            }
                        });
                         //Log.e("list", productResullts.toString());
                    }
                }

                public void onFailure(Call call, IOException e) {
                    Log.e("5678", e.getMessage());
                }
            });

            return null;
        }
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

