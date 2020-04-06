package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShopList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listview;
    // 模拟数据
    private List<String> dataList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_list);
        listview = (ListView) findViewById(R.id.listview);
        dataList = new ArrayList<String>();
        getDate();

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

}
