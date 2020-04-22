package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangeStoreInfoSuccessful extends AppCompatActivity {

    private TextView textView1, textView2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_store_info_successful);

        // Get the widgets reference from XML layout
        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView3);


        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //实现页面跳转
                startActivity(new Intent(ChangeStoreInfoSuccessful.this, ShopList.class));
                return false;
            }
        }).sendEmptyMessageDelayed(0,3000);//表示延迟3秒发送任务


        //jumpTo();

    }

    private void jumpTo(){


    }
}
