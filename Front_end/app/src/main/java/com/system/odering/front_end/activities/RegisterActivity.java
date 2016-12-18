package com.system.odering.front_end.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.system.odering.front_end.R;
import com.system.odering.front_end.model.User;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText txt_name_register;
    private EditText txt_email_register;
    private EditText txt_password_register;
    private EditText txt_confirm_password_register;
    private String name = null;
    private String email = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_name_register = (EditText)findViewById(R.id.txt_register_name);
        txt_email_register = (EditText)findViewById(R.id.txt_register_email);
        txt_password_register = (EditText)findViewById(R.id.txt_register_password);
        txt_confirm_password_register = (EditText)findViewById(R.id.txt_register_confirm_password);
    }

    public void actiionRegister(View view) {
        if(txt_name_register.getText().equals("") && txt_email_register.getText().equals("") && txt_password_register.getText().equals("") && txt_confirm_password_register.getText().equals("")){
            Toast.makeText(this, "All fields are required to be filled in.", Toast.LENGTH_LONG).show();
        }else{
            name = txt_name_register.getText().toString();
            email = txt_email_register.getText().toString();
            password = txt_password_register.getText().toString();
            if(validateEmail(email)) {
                if (validatePassword(password)) {
                    if (password.equals(txt_confirm_password_register.getText().toString())) {
                        new HttpRequestTask(name, email, password).execute();
                    } else {
                        Toast.makeText(this, "Password must match.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Invalid password entered.", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Invalid email address entered.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public Boolean validatePassword(String password){
        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

   private class HttpRequestTask extends AsyncTask<Void, Void, User[]>{

       private String name;
       private String email;
       private String password;

       public HttpRequestTask(String name, String email, String password) {
           this.name = name;
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
               RequestBody body = RequestBody.create(mediaType, "-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"name\"\r\n\r\n"+name+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n"+email+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\n"+password+"\r\n-----011000010111000001101001--");
               Request request = new Request.Builder()
                       .url("http://192.168.10.107:8000/api/user/signup")
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

       @Override
       protected void onPostExecute(User[] users) {
           Intent intent = new Intent(RegisterActivity.this.getApplicationContext(), LoginActivity.class);
           intent.putExtra("userId", email);
           startActivity(intent);
            /*if(users != null) {
                intent = new Intent(RegisterActivity.this.getApplicationContext(), MenuTab.class);
                startActivity(intent);
            }*/
       }
   }
}


