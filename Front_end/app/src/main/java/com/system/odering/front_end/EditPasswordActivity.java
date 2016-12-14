package com.system.odering.front_end;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.system.odering.front_end.model.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditPasswordActivity extends AppCompatActivity {

    private EditText txt_new_password;
    private EditText txt_confirm_password;
    private String password = null;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            userId = "456";
            return;
        }
        userId = bundle.getString("userId");

        txt_new_password = (EditText)findViewById(R.id.txtNewPassword);
        txt_confirm_password = (EditText)findViewById(R.id.txtConfirmPassword);
    }

    public void saveNewPassword(View view) {
        if(txt_new_password.getText().equals("") && txt_confirm_password.getText().equals("")){
            Toast.makeText(this, "All fields are required to be filled in.", Toast.LENGTH_LONG).show();
        }else{
            password = txt_new_password.getText().toString();
            if (validatePassword(password)) {
                if (password.equals(txt_confirm_password.getText().toString())) {
                    new EditPasswordActivity.HttpRequestTask().execute();
                } else {
                    Toast.makeText(this, "Password must match.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Invalid password entered.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Boolean validatePassword(String password){
        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, User[]> {
        User[] users = new User[0];
        User user = new User();
        @Override
        protected User[] doInBackground(Void... params) {
            try{
                final String url = "http://0.0.0.0:8080/api/users";
                RestTemplate rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HashMap<String, String> map = new HashMap<>();
                map.put("id", userId);
                user.setEmail(userId);
                user.setPassword(password);
                HttpEntity<User> request = new HttpEntity<>(user);
                ResponseEntity<User[]> currentUser = rest.exchange(url, HttpMethod.PUT, request, User[].class, map);
                users = currentUser.getBody();
                return users;
            }catch(HttpClientErrorException registerError) {
                System.out.println("ERROR: SEAND/RECEIVE_REQUEST - " + registerError);
            }catch(Exception e){
                System.out.println("ERROR: OTHER - " + e);
            }
            return users;
        }

        @Override
        protected void onPostExecute(User[] users) {
            Intent intent = new Intent(EditPasswordActivity.this.getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }
}
