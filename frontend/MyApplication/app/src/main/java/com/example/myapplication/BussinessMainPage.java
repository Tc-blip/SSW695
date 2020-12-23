package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BussinessMainPage extends AppCompatActivity {

    private EditText username, password;
    private TextView textView, textView6;
    private Button signInButton, signOutButton;
    public static String usernameContent;
    public String passwordContent;
    //public static String bussiness_shop_list;

    static final OkHttpClient okclient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                private HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    cookieStore.put(httpUrl.host(), list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })

            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bussiness_mainpage);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.SignInButton);
        signOutButton = (Button) findViewById(R.id.SignOutButton);
        textView = (TextView) findViewById(R.id.textView);
        textView6 = (TextView) findViewById(R.id.textView6);

        validateUsername();



    }

    private void validateUsername(){

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(BussinessMainPage.this, BussinessSignUpActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            //String usernameContent = username.getText().toString().trim();
            @Override
            public void onClick(View v) {
                usernameContent = username.getText().toString().trim();
                passwordContent = password.getText().toString().trim();

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




        /*OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();*/

        FormBody formBody = new FormBody
                .Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request
                .Builder()
                .post(formBody)
                .url("http://flasktest-env.eba-ph7fbvid.us-east-1.elasticbeanstalk.com/auth_shop_owner/login")
                .build();

        okclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //textView.setText("wuxiao");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {



                final String result = response.body().string();
                Log.d("androixx.xn", result);

                BussinessMainPage.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BussinessMainPage.this, String.valueOf(result), Toast.LENGTH_SHORT).show();
                        //textView6.setText(result);
                    }
                });

                //response.body().close();

                if (result.equals("success")) {
                    Intent intent = new Intent(BussinessMainPage.this, ShopList.class);
                    startActivity(intent);
                }

            }
        });




    }


}
