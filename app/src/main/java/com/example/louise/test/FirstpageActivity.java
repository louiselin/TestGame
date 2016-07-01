package com.example.louise.test;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class FirstpageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        final Button loginbtn = (Button) findViewById(R.id.first);
        loginbtn.setTextColor(0xffffffff);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(FirstpageActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });

        // or intent to another page after n sec
        final Intent intent=new Intent(this,IndexActivity.class);
        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                startActivity(intent);
            }
        };
        timer.schedule(task, 2000);

//        Button bartest = (Button) findViewById(R.id.bartest);
//        bartest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(FirstpageActivity.this, BartestActivity.class);
//                startActivity(intent);
//            }
//        });


    }

}
