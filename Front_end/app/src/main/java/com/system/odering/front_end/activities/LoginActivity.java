package com.system.odering.front_end.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.system.odering.front_end.R;
import com.system.odering.front_end.model.User;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_login_email;
    private EditText txt_login_password;
    private String email = null;
    private String password = null;
    AlertDialog.Builder builder1;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_login_email = (EditText)findViewById(R.id.txt_login_email);
        txt_login_password = (EditText)findViewById(R.id.txt_login_password);

       builder1 = new AlertDialog.Builder(this);
    }

    public void actionLoginLogin(View view) {
        //Toast.makeText(this, ""+txt_login_email.getText(), Toast.LENGTH_SHORT).show();
        if(txt_login_email.getText().equals("") && txt_login_password.getText().equals("")){
            Toast.makeText(this, "A name and password are required.", Toast.LENGTH_LONG).show();
        }else{
            email = txt_login_email.getText().toString();
            password = txt_login_password.getText().toString();
            User[] response = validateCredentials();
            if(response.length > 0){
                Intent intent = new Intent(view.getContext(), FastFoodTabs.class);
                intent.putExtra("userId", response[0].getEmail());
                startActivity(intent);
            }else if(response.length == 0){
                Toast.makeText(this, "Incorrect password for this email.", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "User doesn't exist, please register.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void actionLoginRegister(View view) {
        /*Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);*/
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /*
    * Method - validateCredentials(User)
    * This method is used to validate user login details.
    * This method will determine if the user is allowed
    * access to the application. If user exists the method
    * will return 1, if user exists but password is incorrect
    * the method will return 2, if user does not exist will
    * return 3. */
    public User[] validateCredentials(){
        User[] response = new User[0];
        try {
            email = txt_login_email.getText().toString();
            password = txt_login_password.getText().toString();
            response = new HttpRequestTask(email, password).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, User[]>{

        private String email;
        private String password;

        public HttpRequestTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected User[] doInBackground(Void... params) {
            User test = new User("test", "testuser@gmail.com", "test");
            User[] userLogedIn = new User[1];


            try{


                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("multipart/form-data; boundary=---011000010111000001101001");
                RequestBody body = RequestBody.create(mediaType, "-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n"+email+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\n"+password+"\r\n-----011000010111000001101001--");
                Request request = new Request.Builder()
                        .url("http://192.168.10.107:8000/api/user/login")
                        .post(body)
                        .addHeader("content-type", "multipart/form-data; boundary=---011000010111000001101001")
                        .addHeader("cache-control", "no-cache")
                        .build();

                Response response = client.newCall(request).execute();

                JSONObject json = new JSONObject(response.body().string().toString());

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("token", json.get("token").toString());
                editor.commit();

                Log.e("1234", pref.getString("token", null));

            }catch(HttpClientErrorException loginError){
                /*if(loginError.getStatusCode() == HttpStatus.NOT_ACCEPTABLE)
                    return 2;
                }else if(loginError.getStatusCode() == HttpStatus.NOT_FOUND){
                    return 3;
                }*/

                System.out.println("ERROR: SEND/RECEIVE_REQUEST - " + loginError);
            }catch(Exception e){
                System.out.println("ERROR: OTHER - " + e);
            }
            //if other exception
            userLogedIn[0] = test;
            userLogedIn[0].setCustId(234L);
            return userLogedIn;
        }
    }
}
