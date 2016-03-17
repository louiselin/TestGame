package com.example.louise.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CoinActivity extends AppCompatActivity {

    private ImageView searchimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);

        searchimage = (ImageView) findViewById(R.id.searchimage);
        searchimage.setImageResource(R.drawable.searchrange);
    }
}
