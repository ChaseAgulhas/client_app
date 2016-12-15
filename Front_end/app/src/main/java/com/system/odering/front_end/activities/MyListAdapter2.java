package com.system.odering.front_end.activities;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.system.odering.front_end.R;

import java.util.ArrayList;

/**
 * Created by Chase Agulhas on 2016-12-09.
 */

public class MyListAdapter2 extends ArrayAdapter<UserFoodItem> {

    final String[] qtyValues = {"1","2","3"};
    final ArrayList<UserFoodItem> products;
    int totPrice;
    int totItem;

    public MyListAdapter2(Context context, ArrayList<UserFoodItem> productResullts) {
        super(context, R.layout.custom_list_view2, productResullts);
        products = productResullts;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final UserFoodItem tempProduct = products.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.from(getContext()).inflate(R.layout.custom_list_view2, parent, false);
        }

        final TextView productName = (TextView) convertView.findViewById(R.id.custom_list_view2_product_name);
        TextView price = (TextView) convertView.findViewById(R.id.custom_list_view2_product_price_value);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete);
        Spinner qty = (Spinner) convertView.findViewById(R.id.custom_list_view2_spinner1);
        price.setText(getItem(position).getPrice().toString());
        if(getItem(position) == null){
            productName.setText("TestingTesting123");
        }
        else{
            productName.setText(getItem(position).getProduct().getName());
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Product " + productName.getText() +" deleted from cart.", Toast.LENGTH_SHORT).show();
                products.remove(position);
                notifyDataSetChanged();
                View parentView = (View) v.getParent().getParent().getParent().getParent();
                TextView totalPrice = (TextView) parentView.findViewById(R.id.total_amount);
                TextView totalItems = (TextView) parentView.findViewById(R.id.item_count);

                totItem = products.size();
                totPrice = 0;
                //calculate total price
                for(int i=0; i < products.size(); i++){
                    totPrice = totPrice + Integer.parseInt(products.get(i).getPrice());
                }
                totalItems.setText("("+totItem+")");
                totalPrice.setText(""+totPrice+".00");
            }
        });



        //Set up the spinner item
        final ArrayAdapter<String> qtyAdapter = new ArrayAdapter<String>(getContext(),R.layout.qty_spinner, qtyValues);
        qtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qty.setAdapter(qtyAdapter);
        qty.setSelection(tempProduct.getQuantity()-1);
        qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(parent.getSelectedItemPosition()+1 != tempProduct.getQuantity()){
                if(tempProduct.getQuantity() > 1){
                    //Toast.makeText(getContext(), "Product quantity changed" + productName.getText(), Toast.LENGTH_SHORT).show();
                    //refresh data outside of view
                    products.add(position, tempProduct);
                    qtyAdapter.notifyDataSetChanged();
                    View parentView = (View) view.getParent().getParent().getParent().getParent();
                    TextView totalPrice = (TextView) parentView.findViewById(R.id.total_amount);
                    TextView totalItems = (TextView) parentView.findViewById(R.id.item_count);
                    TextView productPrice = (TextView) parentView.findViewById(R.id.custom_list_view2_product_price_value);
                    totItem = products.size();
                    totPrice = 0;
                    //calculate total price
                    for(int i=0; i < products.size(); i++){
                        totPrice = totPrice + Integer.parseInt(products.get(i).getPrice());
                    }
                    totalItems.setText("("+totItem+")");
                    totalPrice.setText(""+totPrice+".00");
                    productPrice.setText(""+tempProduct.getPrice());
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        notifyDataSetChanged();


        return  convertView;
    }
    //here is where we will create the HTTPRequst to send singe food items to the Cart table in the DB
}