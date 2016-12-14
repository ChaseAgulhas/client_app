package com.system.odering.front_end.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.system.odering.front_end.R;
import com.system.odering.front_end.model.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    if (password.equals(txt_confirm_password_register.getText())) {
                        new HttpRequestTask().execute();
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

    private class HttpRequestTask extends AsyncTask<Void, Void, User[]> {
        User test = new User("test", "test", "test");
        User[] user = new User[0];
        @Override
        protected User[] doInBackground(Void... params) {
            try{
                final String url = "http://0.0.0.0:8080/api/users";
                RestTemplate rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpEntity<User> request = new HttpEntity<>(new User(name, email, password));
                return rest.postForObject(url, request, User[].class);
            }catch(HttpClientErrorException registerError) {
                System.out.println("ERROR: SEAND/RECEIVE_REQUEST - " + registerError);
            }catch(Exception e){
                System.out.println("ERROR: OTHER - " + e);
            }
            user[0] = test;
            user[0].setCustId(432L);
            return user;
        }

        @Override
        protected void onPostExecute(User[] users) {
            Intent intent = new Intent(RegisterActivity.this.getApplicationContext(), ProfileActivity.class);
            intent.putExtra("userId", user[0].getEmail());
            startActivity(intent);
            /*if(users != null) {
                Intent intent = new Intent(RegisterActivity.this.getApplicationContext(), ...class);
                startActivity(intent);
            }*/
        }
    }
}
