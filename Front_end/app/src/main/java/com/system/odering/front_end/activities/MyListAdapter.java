package com.system.odering.front_end.activities;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.system.odering.front_end.R;

import java.util.ArrayList;

/**
 * Created by Chase Agulhas on 2016-11-30.
 */
public class MyListAdapter extends ArrayAdapter<FoodItem> {
    public MyListAdapter(Context context, ArrayList<FoodItem> productResullts) {
        super(context, R.layout.custom_list_view, productResullts);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //View product = convertView;

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }

        //convertView.setTag(getItem(position));

        //ImageView productImage = (ImageView) convertView.findViewById(R.id.custom_list_view_product_image);
        final TextView productName = (TextView) convertView.findViewById(R.id.custom_list_view_product_name);
        //TextView size = (TextView) convertView.findViewById(R.id.custom_list_view_product_size_value);
        TextView price = (TextView) convertView.findViewById(R.id.custom_list_view_product_price_value);
        //Button addToCart = (Button) convertView.findViewById(R.id.btn_add_to_cart);
        productName.setText(getItem(position).getName());
        price.setText(getItem(position).getPrice());

        Button addToCart = (Button) convertView.findViewById(R.id.btn_add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Product " + productName.getText() +" added to cart.", Toast.LENGTH_SHORT).show();
                //this was where uwe was trying to implement the interface
                ((FastFoodTabs)getContext()).addToCartList(getItem(position), ((FastFoodTabs)getContext()).getLoggedInUser());
            }
        });
        return  convertView;
    }
    //here is where we will create the HTTPRequst to send singe food items to the Cart table in the DB
}
