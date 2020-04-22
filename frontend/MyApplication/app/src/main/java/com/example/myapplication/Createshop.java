package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.myapplication.BussinessMainPage.okclient;

public class Createshop extends AppCompatActivity {

    private EditText store_name, store_description, store_latitude, store_longitude;
    private Button finishButton;
    //public static String usernameContent;
    //public String passwordContent;
    //public static String bussiness_shop_list;s


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_shop);

        store_name = (EditText) findViewById(R.id.editText7);
        store_description = (EditText) findViewById(R.id.editText);
        store_latitude = (EditText) findViewById(R.id.editText2);
        store_longitude = (EditText) findViewById(R.id.editText4);
        finishButton = (Button) findViewById(R.id.button2);


        valid_info_change();

    }

    private void valid_info_change() {

        finishButton.setOnClickListener(new View.OnClickListener() {
            //String usernameContent = username.getText().toString().trim();
            @Override
            public void onClick(View v) {
                //int storeId = ShopList.storelist.get(ShopList.store_num).StoreId;
                String storeNameContent = store_name.getText().toString().trim();
                String descriptionContent = store_description.getText().toString().trim();
                String storeLatitude = store_latitude.getText().toString().trim();
                String storeLongitude = store_longitude.getText().toString().trim();

                if (storeNameContent.isEmpty()) {
                    store_name.setError("Store name cannnot be null");
                    //return;
                } else if (descriptionContent.isEmpty()) {
                    store_description.setError("Store Description cannnot be null");
                } else if (storeLatitude.isEmpty()) {
                    store_latitude.setError("Store Latitude cannnot be null");
                } else if (storeLongitude.isEmpty()) {
                    store_longitude.setError("Store Longitude cannnot be null");
                } else {
                    store_name.setError(null);
                    store_description.setError(null);
                    store_latitude.setError(null);
                    store_longitude.setError(null);

                    try {
                        postRequest(storeNameContent, descriptionContent, storeLatitude, storeLongitude);
                    } catch (Exception e) {
                        System.out.println("Something wrong");
                    }


                    //textView.setText("Your inputted username is : " + username.getText().toString().trim()+ "password is " + password.getText().toString().trim());
                    //return 0;
                }
            }
        });
    }

    private void postRequest(String store_name,String store_description, String store_latitude, String store_longitude) {


        FormBody formBody = new FormBody
                .Builder()
                .add("storename", store_name)
                .add("description", store_description)
                .add("latitute", store_latitude)
                .add("longitude", store_longitude)
                .build();

        Request request = new Request
                .Builder()
                .post(formBody)
                .url("http://10.0.2.2:5000/shop/create_shop")
                .build();

        okclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //textView.setText("wuxiao");
                Toast.makeText(Createshop.this, "fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {

                final String result = response.body().string();
                Log.d("androixx.xn", result);
                //response.body().close();

                Createshop.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Createshop.this, String.valueOf(result), Toast.LENGTH_SHORT).show();
                        //textView.setText(result);

                        Intent intent = new Intent(Createshop.this, BussinessMainPage.class);
                        startActivity(intent);
                    }
                });

                //textView.setText(result);

                response.body().close();





            }
        });

    }
}