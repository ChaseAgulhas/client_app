package com.system.odering.front_end.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.system.odering.front_end.R;

import org.springframework.web.client.HttpClientErrorException;

public class OrderDetailsActivity extends Activity {

    private EditText txt_customer_name;
    private EditText txt_customer_address;
    private EditText txt_customer_email;
    private EditText txt_customer_phone;

    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        txt_customer_name = (EditText)findViewById(R.id.txt_customer_name);
        txt_customer_address = (EditText)findViewById(R.id.txt_customer_address);
        txt_customer_email = (EditText)findViewById(R.id.txt_customer_email);
        txt_customer_phone = (EditText)findViewById(R.id.txt_customer_phone);
    }

    public void actionCancelOrder(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void actionPlaceOrder(View view)
    {
        if(txt_customer_name.getText().equals("") || txt_customer_address.getText().equals("") || txt_customer_email.getText().equals("") || txt_customer_phone.getText().equals(""))
        {
            Toast.makeText(this, "All fields are required to place an order.", Toast.LENGTH_LONG).show();
        }
        else
        {

        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Integer>
    {
        @Override
        protected Integer doInBackground(Void... params)
        {
            try
            {
                final String url = "http://1234:8080/api/orders";

            }
            catch(HttpClientErrorException orderError)
            {
                System.out.println("Error: " + orderError);
            }
            catch(Exception exception)
            {
                System.out.println("Error: " + exception);
            }

            return 0;
        }
    }
}
