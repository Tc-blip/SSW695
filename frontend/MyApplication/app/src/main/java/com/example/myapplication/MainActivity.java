package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.ws.RealWebSocket;



public class MainActivity extends AppCompatActivity {
    private EditText username, password;
    private TextView textView;
    private Button signInButton, signOutButton, bussinessButton;
    String code;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the widgets reference from XML layout
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.SignInButton);
        signOutButton = (Button) findViewById(R.id.SignOutButton);
        textView = (TextView) findViewById(R.id.textView6);
        bussinessButton = (Button) findViewById(R.id.BussinessEntrance);


        validateUsername();
        //transfer(ans[0], ans[1]);
        //textView.setText("[0] is" + ans[0] + ", [1] is " + ans[1]);


        //postRequest(ans[0], ans[1]);

    }

    private void validateUsername(){
        //String usernameContent = username.getText().toString().trim();
        //String value;
        //Map<String, String> map = new HashMap<String, String>();
        bussinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BussinessMainPage.class);
                startActivity(intent);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            //String usernameContent = username.getText().toString().trim();
            @Override
                public void onClick(View v) {
                    String usernameContent = username.getText().toString().trim();
                    String passwordContent = password.getText().toString().trim();

                    if (usernameContent.isEmpty()) {
                        username.setError("User cannnot be null");
                        //return;
                    }
                    else if ( passwordContent.isEmpty()){
                        password.setError("Password cannnot be null");
                    }

                    else {
                        username.setError(null);
                        password.setError(null);

                        try {
                            postRequest(usernameContent,passwordContent);
                        } catch (Exception e) {
                            System.out.println("Something wrong");
                        }

                    }
            }
        });

    }




    private void postRequest(String username,String password)  {

        OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();

        FormBody formBody = new FormBody
                .Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request
                .Builder()
                .post(formBody)
                .url("http://flasktest-env.eba-ph7fbvid.us-east-1.elasticbeanstalk.com/auth/login")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                textView.setText("wuxiao");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {



                final String result = response.body().string();
                Log.d("androixx.xn", result);
                //response.body().close();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, String.valueOf(result), Toast.LENGTH_SHORT).show();

                    }
                });

                //textView.setText(result);

                response.body().close();

                if (result.equals("success")) {
                    Intent intent = new Intent(MainActivity.this, MapPage.class);
                    startActivity(intent);
                }
                /*if (result == "successful") {
                    textView.setText("login successful");
                    //Toast.makeText(MainActivity.this, "登陆成功！！！", Toast.LENGTH_SHORT).show();
                }
                else{
                    textView.setText(result);
                }

                //textView.setText("login successful！！！");*/



            }
        });

    }

    private void transfer(String username, String password){

        OkHttpClient client = new OkHttpClient();
       String url ="https://reqres.in/api/users?page=2";
       //String url1 = "http://10.0.2.2:5000/";
       Request request = new Request.Builder()
               .url(url)
               .build();

       client.newCall(request).enqueue(new Callback() {

           @Override
           public void onFailure(@NotNull Call call, @NotNull IOException e) {
               e.printStackTrace();
               textView.setText("wudi");
           }

           @Override
           public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               if (response.isSuccessful()){
                   final String myResponse = response.body().string();

                   MainActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                            //textView.setText(myResponse);
                       }
                   });
               }
           }
       });

    }
}


