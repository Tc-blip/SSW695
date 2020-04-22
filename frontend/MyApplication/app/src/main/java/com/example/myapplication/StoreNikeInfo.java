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
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.myapplication.BussinessMainPage.okclient;

public class StoreNikeInfo extends AppCompatActivity {

    private EditText store_name, store_description, store_latitude, store_longitude;
    private Button finishButton;
    //public static String usernameContent;
    //public String passwordContent;
    //public static String bussiness_shop_list;s


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_nike_info);

        store_name = (EditText) findViewById(R.id.editText7);
        store_description = (EditText) findViewById(R.id.editText);
        store_latitude = (EditText) findViewById(R.id.editText2);
        store_longitude = (EditText) findViewById(R.id.editText4);
        finishButton= (Button) findViewById(R.id.button2);


        store_name.setText(ShopList.storelist.get(ShopList.store_num).StoreName);  //initial text content
        store_description.setText(ShopList.storelist.get(ShopList.store_num).StoreDescription);
        double default_latitude = ShopList.storelist.get(ShopList.store_num).StoreLatitude;
        double default_longitude = ShopList.storelist.get(ShopList.store_num).StoreLongitude;

        store_latitude.setText(String.valueOf(default_latitude));
        store_longitude.setText(String.valueOf(default_longitude));

        valid_info_change();

    }

    private void valid_info_change(){

        finishButton.setOnClickListener(new View.OnClickListener() {
            //String usernameContent = username.getText().toString().trim();
            @Override
            public void onClick(View v) {
                int storeId = ShopList.storelist.get(ShopList.store_num).StoreId;
                String storeNameContent = store_name.getText().toString().trim();
                String descriptionContent = store_description.getText().toString().trim();
                String storeLatitude = store_latitude.getText().toString().trim();
                String storeLongitude = store_longitude.getText().toString().trim();

                if (storeNameContent.isEmpty()) {
                    store_name.setError("Store name cannnot be null");
                    //return;
                }
                else if ( descriptionContent.isEmpty()){
                    store_description.setError("Store Description cannnot be null");
                }
                else if ( storeLatitude.isEmpty()){
                    store_latitude.setError("Store Latitude cannnot be null");
                }
                else if ( storeLongitude.isEmpty()){
                    store_longitude.setError("Store Longitude cannnot be null");
                }

                else {
                    store_name.setError(null);
                    store_description.setError(null);
                    store_latitude.setError(null);
                    store_longitude.setError(null);

                    try {
                        postRequest(storeId, storeNameContent, descriptionContent, storeLatitude, storeLongitude);
                    } catch (Exception e) {
                        System.out.println("Something wrong");
                    }


                    //textView.setText("Your inputted username is : " + username.getText().toString().trim()+ "password is " + password.getText().toString().trim());
                    //return 0;
                }
            }
        });
    }

    private void postRequest(int StoreId, String store_name,String store_description, String store_latitude, String store_longitude) {

        String string_store_id = Integer.toString(StoreId);


        FormBody formBody = new FormBody
                .Builder()
                .add("shop_id", string_store_id)
                .add("storename", store_name)
                .add("description", store_description)
                .add("latitute", store_latitude)
                .add("longitude", store_longitude)
                .build();

        Request request = new Request
                .Builder()
                .post(formBody)
                .url("http://flasktest-env.eba-ph7fbvid.us-east-1.elasticbeanstalk.com/shop/set_shop_infor")
                .build();

        okclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //textView.setText("wuxiao");
                Toast.makeText(StoreNikeInfo.this, "fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {

                final String result = response.body().string();
                Log.d("androixx.xn", result);
                //response.body().close();

                StoreNikeInfo.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StoreNikeInfo.this, String.valueOf(result), Toast.LENGTH_SHORT).show();
                        //textView.setText(result);

                        Intent intent = new Intent(StoreNikeInfo.this, ChangeStoreInfoSuccessful.class);
                        startActivity(intent);
                    }
                });

                //textView.setText(result);

                response.body().close();





            }
        });

    }


}
