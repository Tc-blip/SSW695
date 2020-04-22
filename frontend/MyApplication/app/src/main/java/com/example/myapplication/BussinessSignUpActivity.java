package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BussinessSignUpActivity extends AppCompatActivity {

    private EditText username, password, gender, birthday;
    private TextView textView;
    private Button finishButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bussiness_signup_activity);

        textView = findViewById(R.id.signup_page_text);
        username = findViewById(R.id.username);
        password =  findViewById(R.id.editText5);
        gender = findViewById(R.id.editText6);
        birthday = findViewById(R.id.editText9);
        finishButton = (Button) findViewById(R.id.button);

        getRegisterInformation();

    }

    private void getRegisterInformation(){


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameContent = username.getText().toString().trim();
                String passwordContent = password.getText().toString().trim();
                String genderContent = gender.getText().toString().trim();
                String birthdayContent = birthday.getText().toString().trim();


                if (usernameContent.isEmpty()) {
                    username.setError("Username cannnot be null");
                    //return;
                } else if (passwordContent.isEmpty()) {
                    password.setError("Password cannnot be null");
                } else if (genderContent.isEmpty()) {
                    gender.setError("Gender cannnot be null");
                } else if (birthdayContent.isEmpty()) {
                    birthday.setError("Birthday cannnot be null");
                } else {
                    username.setError(null);
                    password.setError(null);
                    gender.setError(null);
                    birthday.setError(null);


                    try {
                        postRequest(usernameContent,passwordContent, genderContent, birthdayContent);
                    } catch (Exception e) {
                        System.out.println("Something wrong");
                    }
                }

            }
        });

    }

    private void postRequest(String username,String password, String gender, String birthday)  {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        FormBody formBody = new FormBody
                .Builder()
                .add("username", username)
                .add("password", password)
                .add("gender", gender)
                .add("birthday", birthday)
                .build();

        Request request = new Request
                .Builder()
                .post(formBody)
                .url("http://flasktest-env.eba-ph7fbvid.us-east-1.elasticbeanstalk.com/auth_shop_owner/register")

                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String result = response.body().string();
                Log.d("androixx.xn", result);
                response.body().close();

                Intent intent = new Intent(BussinessSignUpActivity.this, SignUpSuccessful.class);
                startActivity(intent);
            }
        });

    }
}
