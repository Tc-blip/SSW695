package com.example.myapplication;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import com.google.gson.Gson;

import static com.example.myapplication.BussinessMainPage.okclient;


public class ShopList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listview;
    private TextView textView;
    private Button logOutButton, addNewShop;
    // 模拟数据
    private List<String> dataList = null;
    static String bussiness_shop_list;
    static List<StoreInfo> storelist;
    static int store_num = -1;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_list);
        listview = (ListView) findViewById(R.id.listview);
        //textView = findViewById(R.id.textView4);
        logOutButton = (Button) findViewById(R.id.button3);
        addNewShop = (Button) findViewById(R.id.button4);
        dataList = new ArrayList<String>();


        read_shop_list();



        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                log_out();

            }
        });

        addNewShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShopList.this, Createshop.class);
                startActivity(intent);

            }
        });

    }

    private void show_listview(){
        ListAdapter adapter = new ArrayAdapter<String>(ShopList.this,
                android.R.layout.simple_list_item_1, dataList);
        // 获得ListActivity中的listview控件，注意布局文件中listview的id必须是android:id="@android:id/list"

        listview.setAdapter(adapter);
        // 绑定item点击事件
        listview.setOnItemClickListener(this);

    }

    private void read_shop_list(){


        String url = "http://flasktest-env.eba-ph7fbvid.us-east-1.elasticbeanstalk.com/shop/get_shop_infor";

        Request request = new Request.Builder()
                .url(url)
                .build();

        okclient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                //textView.setText("wudi");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()){
                    final String result = response.body().string();
                    //String jsonStr = bussiness_shop_list;
                    Gson gson = new Gson();
                    storelist = gson.fromJson(result, new TypeToken<List<StoreInfo>>(){}.getType());
                    int size = storelist.size();   //add data to list view
                    //int size = 3;   //add data to list view
                    for (int i = 0; i < size; i++) {
                        dataList.add(storelist.get(i).StoreName);
                    }

                    ShopList.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show_listview();
                        }
                    });
                }
            }
        });



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String store_name = storelist.get(position).StoreName;
        store_num = position;
        Toast.makeText(ShopList.this, "Enter to edit " + store_name, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ShopList.this, StoreNikeInfo.class);
        startActivity(intent);

    }



    private void add_list(String bussiness_shop_list){


        String jsonStr = bussiness_shop_list;
        Gson gson = new Gson();
        storelist = gson.fromJson(jsonStr, new TypeToken<List<StoreInfo>>(){}.getType());

        if (storelist == null) {

            textView.setText("没有");
        }
        else{
            int size = storelist.size();   //add data to list view
            //int size = 3;   //add data to list view
            for (int i = 0; i < size; i++){
                dataList.add(storelist.get(i).StoreName);
            }
        }
        textView.setText(jsonStr);




    }


    private void log_out(){

        String url = "http://flasktest-env.eba-ph7fbvid.us-east-1.elasticbeanstalk.com/auth_shop_owner/logout";


        Request request = new Request.Builder()
                .url(url)
                .build();

        okclient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                //textView.setText("wudi");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()){
                    final String result = response.body().string();
                    ShopList.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShopList.this, result, Toast.LENGTH_SHORT).show();

                        }
                    });


                    Intent intent = new Intent(ShopList.this, MainActivity.class);
                    startActivity(intent);




                }
            }
        });

    }


}
