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
    public String store_things;

    static String jsonStr = BussinessMainPage.bussiness_shop_list;
    static Gson gson = new Gson();
    static List<StoreInfo> storelist = gson.fromJson(jsonStr, new TypeToken<List<StoreInfo>>(){}.getType());
    static int store_num = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_list);
        listview = (ListView) findViewById(R.id.listview);
        textView = findViewById(R.id.textView4);
        logOutButton = (Button) findViewById(R.id.button3);
        addNewShop = (Button) findViewById(R.id.button4);
        dataList = new ArrayList<String>();
        add_list();


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

        ListAdapter adapter = new ArrayAdapter<String>(ShopList.this,
                android.R.layout.simple_list_item_1, dataList);
        // 获得ListActivity中的listview控件，注意布局文件中listview的id必须是android:id="@android:id/list"

        listview.setAdapter(adapter);
        // 绑定item点击事件
        listview.setOnItemClickListener(this);




    }
    

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String store_name = storelist.get(position).StoreName;
        store_num = position;
        Toast.makeText(ShopList.this, "Enter to edit " + store_name, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ShopList.this, StoreNikeInfo.class);
        startActivity(intent);

    }


    private void add_list(){


        int size = storelist.size();   //add data to list view
        for (int i = 0; i < size; i++){
            dataList.add(storelist.get(i).StoreName);
        }



    }

    private void add_store(){



    }
    private void log_out(){

        String url = "http://10.0.2.2:5000/auth_shop_owner/logout";


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
