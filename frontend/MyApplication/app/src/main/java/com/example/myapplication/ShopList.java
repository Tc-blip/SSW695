package com.example.myapplication;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


public class ShopList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listview;
    private TextView textView;
    // 模拟数据
    private List<String> dataList = null;
    public String store_things;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_list);
        listview = (ListView) findViewById(R.id.listview);
        textView = findViewById(R.id.textView4);
        dataList = new ArrayList<String>();
        get_store_infor();
        //getDate();

        ListAdapter adapter = new ArrayAdapter<String>(ShopList.this,
                android.R.layout.simple_list_item_1, dataList);
        // 获得ListActivity中的listview控件，注意布局文件中listview的id必须是android:id="@android:id/list"

        listview.setAdapter(adapter);
        // 绑定item点击事件
        listview.setOnItemClickListener(this);


    }
    

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(ShopList.this, "点击了第" + position + "条数据", Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("ShowToast")
    private void getDate() {
        // 初始化数据
        /*for (int i = 0; i < 20; i++) {
            dataList.add("第" + i + "条数据");
        }*/
        dataList.add("第条数据");
        dataList.add("你很好");
        dataList.add("我真棒");
    }

    private void get_store_infor(){


        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        //String url = "https://reqres.in/api/users?page=2";
        String url ="http://10.0.2.2:5000/shop/";
        //String url = "http://10.0.2.2:5000/shop/get_shop_infor";
        //String url = "https://my-json-server.typicode.com/typicode/demo/comments";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                //textView.setText("wudi");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()){
                     final String result = response.body().string();
                    /*try {
                        //JSONArray jsonarray = new JSONArray(response.body().string());
                        //JSONObject json = new JSONObject(result);
                        //final String code = jsonarray.optString("code");
                        //JSONArray jsonarray = json.getJSONArray()

                        //store_things = code;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                    ShopList.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(result);
                        }


                    //response.body().close();
                    });
                }
            }
        });

    }
}
