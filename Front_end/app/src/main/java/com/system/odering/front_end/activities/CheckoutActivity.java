package com.system.odering.front_end.activities;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.system.odering.front_end.R;
import com.system.odering.front_end.model.Order;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class CheckoutActivity extends AppCompatActivity {

    private String location;
    private String time;
    private String laterTime;
    private String loggedInUser;
    private String timeInput = "now";
    private Button selectTime;
    private ArrayList<UserFoodItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        selectTime = (Button) findViewById(R.id.buttonTime);
        selectTime.setVisibility(View.INVISIBLE);
        orderItems = CartTab.getOrderItems();
        loggedInUser = getIntent().getStringExtra("userId");

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        selectTime.setText( selectedHour + ":" + selectedMinute);
                        timeInput = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    public void processOrder(View view) {
        String response = null;
        if(location.equals(null) && time.equals(null)){
            Toast.makeText(this, "Both location and time are required", Toast.LENGTH_SHORT).show();
        }else{
            try{
                response = new HttpRequestTask().execute().get();
            }catch(Exception e ){

            }
            if(response.equals("Order_Generated")) {
                Random r = new Random();
                int num = r.nextInt(999999 - 44444) + 1;
                Toast.makeText(this, "Order for "+loggedInUser+" has been processed: Order number is " + num, Toast.LENGTH_SHORT).show();
            }else if(!response.equals("Order_Generated")){
                Toast.makeText(this, "Order for "+loggedInUser+" has been processed: Order number is " + response, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void selectLocation(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId())
        {
            case R.id.rbtnHome:
                if(checked)
                {
                    location = "homeLocation";
                }
                break;
            case R.id.rbtnCurrent:
                if(checked)
                {
                    location = "currentLocation";
                }
                break;
        }
    }

    public void selectTime(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId())
        {
            case R.id.rbtnNow:
                if(checked)
                {
                    time = "now";
                    selectTime.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.rbtnLater:
                if(checked)
                {
                    time = "later";
                    selectTime.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        String response="Order_Generated";

        @Override
        protected String doInBackground(Void... params) {
                try{
                    final String url = "http://0.0.0.0:8080/api/orders/placeOrder";
                    RestTemplate rest = new RestTemplate();
                    rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    HttpEntity<Order> request = new HttpEntity<>(new Order(loggedInUser, location, timeInput, orderItems));
                    Order order = rest.postForObject(url, request, Order.class);
                    if(order != null){
                        return ""+order.getId();
                    }
                    response = "Order_Generated";

                }catch(HttpClientErrorException registerError) {
                    response = "Order_Generated";
                }catch(Exception e){
                    System.out.println("ERROR: SEAND/RECEIVE_REQUEST - " + e);
                    response = "Order_Generated";
                }
            return response;
        }
    }
}
