package com.system.odering.front_end.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.system.odering.front_end.R;
import com.system.odering.front_end.model.Address;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class EditHomeActivity extends AppCompatActivity {

    private EditText txtLine1;
    private EditText txtLine2;
    private EditText txtSuburb;
    private EditText txtCity;

    private String userId;
    private String number;
    private String line1;
    private String line2;
    private String suburb;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_home);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            userId = "456";
            return;
        }
        userId = bundle.getString("userId");

        txtLine1 = (EditText) findViewById(R.id.txtLine1);
        txtLine2 = (EditText) findViewById(R.id.txtLine2);
        txtSuburb = (EditText) findViewById(R.id.txtSuburb);
        txtCity = (EditText) findViewById(R.id.txtCity);
    }

    public void saveHomeLocation(View view) {

        if(txtLine1.getText().equals("") && txtLine2.getText().equals("") && txtSuburb.getText().equals("") && txtCity.getText().equals("")){
            Toast.makeText(this,"All fields are required.",Toast.LENGTH_SHORT).show();
        }else{
            line1 = txtLine1.getText().toString();
            line2 = txtLine2.getText().toString();
            suburb = txtSuburb.getText().toString();
            city = txtCity.getText().toString();
            number = "0";
            String[] sep = line1.split(" ");
            if(sep.length > 1){
                number = sep[0];
                line1 = sep[1];
                //**********************************************
                try{
                    new HttpRequestTask().execute();
                }catch(Exception e){

                }
            }else{
                Toast.makeText(this,"Address needs a number.",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Address[]> {
        Address[] address = new Address[1];
        @Override
        protected Address[] doInBackground(Void... params) {
            try{
                final String url = "http://0.0.0.0:8080/api/users";
                RestTemplate rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpEntity<Address> request = new HttpEntity<>(new Address(userId, number, line1+", "+line2, suburb, city));
                return rest.postForObject(url, request, Address[].class);
            }catch(HttpClientErrorException registerError) {
                System.out.println("ERROR: SEAND/RECEIVE_REQUEST - " + registerError);
            }catch(Exception e){
                System.out.println("ERROR: OTHER - " + e);
            }
            return address;
        }

        @Override
        protected void onPostExecute(Address[] address) {
            Intent intent = new Intent(EditHomeActivity.this.getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            /*if(users != null) {
                Intent intent = new Intent(RegisterActivity.this.getApplicationContext(), ...class);
                startActivity(intent);
            }*/
        }
    }
}
