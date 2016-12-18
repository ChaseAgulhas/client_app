package com.system.odering.front_end.activities;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.security.AccessController.getContext;

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
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        if(location.equals(null) && time.equals(null)){
            Toast.makeText(this, "Both location and time are required", Toast.LENGTH_SHORT).show();
        }else{
            try{
                for(Iterator<UserFoodItem> i = orderItems.iterator(); i.hasNext(); ) {
                    UserFoodItem item = i.next();
                    response = new HttpRequestTask(location, time, laterTime, loggedInUser, timeInput, item.getProduct().getName(), pref.getString("token", null)).execute().get();
                }

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

        String location,  time,  laterTime,  loggedInUser,  timeInput,  item, resp, token;

        public HttpRequestTask(String location, String time, String laterTime, String loggedInUser, String timeInput, String item, String token) {
        this.location = location;
        this.time = time;
        this.laterTime = laterTime;
        this.loggedInUser = loggedInUser;
        this.timeInput = timeInput;
        this.item = item;
        this.token = token;
        }



        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=---011000010111000001101001");
            RequestBody body = RequestBody.create(mediaType, "-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"location\"\r\n\r\n"+location+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"time\"\r\n\r\n"+time+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"laterTime\"\r\n\r\n"+laterTime+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"timeInput\"\r\n\r\n"+timeInput+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"selectTime\"\r\n\r\n"+selectTime+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"item\"\r\n\r\n"+item+"\r\n-----011000010111000001101001--");
            Request request = new Request.Builder()
                    .url("http://192.168.10.107:8000/api/orders/store?token="+token)
                    .post(body)
                    .addHeader("content-type", "multipart/form-data; boundary=---011000010111000001101001")
                    .addHeader("cache-control", "no-cache")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Order_Generated";
        }


    }
}
