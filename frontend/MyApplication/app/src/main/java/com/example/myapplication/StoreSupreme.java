package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StoreSupreme extends AppCompatActivity {

    private TextView description;
    private ImageView logo, introduction;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_supremem);

        description = findViewById(R.id.textView5);
        logo = findViewById(R.id.imageView);
        introduction =  findViewById(R.id.imageView5);

    }

}
