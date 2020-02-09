package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textview1;
    private Button getbutton;
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview1 = findViewById(R.id.textView);
        getbutton = findViewById(R.id.button);

        getbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebservice();
            }
        });

        }
        private void getWebservice(){

            new Thread(new Runnable() {

                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    String path1 = "http://127.0.0.1:5000/";
                    Request request = new Request.Builder().url(path1).build();
                    try {
                        Response response = client.newCall(request).execute();
                        textview1.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }
}
