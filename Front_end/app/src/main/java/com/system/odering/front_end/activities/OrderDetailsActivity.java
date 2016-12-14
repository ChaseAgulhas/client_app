package com.system.odering.front_end.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.system.odering.front_end.R;
import com.system.odering.front_end.domain.address.Address;
import com.system.odering.front_end.domain.user.Customer;
import com.system.odering.front_end.domain.order.FoodItem;
import com.system.odering.front_end.domain.order.Order;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class OrderDetailsActivity extends AppCompatActivity {

    private EditText txt_customer_name;
    private EditText txt_customer_address;
    private EditText txt_customer_email;
    private EditText txt_customer_phone;

    private Long orderID;
    private String name;
    private String buildAddress;
    private String email;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Bundle extras = getIntent().getExtras();
        orderID = extras.getLong("ORDER_ID");
    }

    public void actionCancelOrder(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void actionPlaceOrder(View view)
    {
        if(name.equals("") || buildAddress.equals("") || email.equals("") || phoneNumber.equals(""))
        {
            Toast.makeText(this, "All fields are required to place an order.", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(isInternetConnected(getBaseContext()))
            {
                name = txt_customer_name.getText().toString();
                buildAddress = txt_customer_address.getText().toString();
                email = txt_customer_email.getText().toString();
                phoneNumber = txt_customer_phone.getText().toString();

                Customer customer = new Customer.Builder()
                        .name(name)
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .build();
                Address address = new Address.Builder()
                        .streetName(buildAddress)
                        .build();
                FoodItem foodItem = new FoodItem.Builder()
                        .name("Name test")
                        .price(123)
                        .build();

                Order order = new Order.Builder()
                                        .customer(customer)
                                        .address(address)
                                        .foodItem(foodItem)
                                        .build();

                PlaceOrderTask placeOrderTask = new PlaceOrderTask(order);
                placeOrderTask.execute();
            }
        }
    }

    public static boolean isInternetConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    private class PlaceOrderTask extends AsyncTask<Void, Void, Order>
    {
        private final Order order;

        PlaceOrderTask(Order order)
        {
            this.order = order;
        }


        @Override
        protected Order doInBackground(Void... params)
        {
            final String url = "http://1234:8080/api/orders";

            HttpHeaders headers = new HttpHeaders();
            //headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(new MediaType("application", "json")));

            HttpEntity<Order> request = new HttpEntity<>(order, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

            ResponseEntity<Order> result = restTemplate.exchange(url, HttpMethod.POST, request, Order.class);


            //Order order = restTemplate.postForObject(url, request, Order.class);
            //Toast.makeText(this, "Order placed successfully.", Toast.LENGTH_LONG).show();
            System.out.println("Order placed successfully.");

            return result.getBody();
        }

        @Override
        protected void onPostExecute(final Order order)
        {
            runOnUiThread(new Runnable() {
                public void run(){
                    Toast.makeText(getBaseContext(), "Order placed successfully.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
