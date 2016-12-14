package com.system.odering.front_end;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.system.odering.front_end.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_login_email;
    private EditText txt_login_password;
    private String email = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_login_email = (EditText)findViewById(R.id.txt_login_email);
        txt_login_password = (EditText)findViewById(R.id.txt_login_password);
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
                Intent intent = new Intent(this, ProfileActivity.class);
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
            response = new HttpRequestTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, User[]>{

        @Override
        protected User[] doInBackground(Void... params) {
            User test = new User("test", "test", "test");
            User[] userLogedIn = new User[1];
            try{
                final String url = "http://0.0.0.0:8080/api/users/verify/{email}/{password}";
                RestTemplate rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HashMap<String, String> map = new HashMap<String, String>(2);
                map.put("email", email);
                map.put("password", password);
                ResponseEntity<User> userResponseEntity = rest.postForEntity(url, null, User.class, map);

                return rest.getForObject(url, User[].class, map);


            }catch(HttpClientErrorException loginError){
                /*if(loginError.getStatusCode() == HttpStatus.NOT_ACCEPTABLE)
                    return 2;
                }else if(loginError.getStatusCode() == HttpStatus.NOT_FOUND){
                    return 3;
                }*/

                System.out.println("ERROR: SEAND/RECEIVE_REQUEST - " + loginError);
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
