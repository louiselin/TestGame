package com.example.louise.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Switch switchButton, switchButton2;
    String switchOn = "ON";
    String switchOff = "OFF";
    String che_vi = "";
    String che_me = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);


        // For first switch button
        switchButton = (Switch) findViewById(R.id.switchButton);
        switchButton.setChecked(false);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    Toast.makeText(SettingActivity.this, "1 switch MediaPlayer on!", Toast.LENGTH_SHORT).show();
                    che_me = switchOn;
                } else {
                    Toast.makeText(SettingActivity.this, "1 switch MediaPlayer off!", Toast.LENGTH_SHORT).show();
                    che_me = switchOff;
                }
            }
        });

        if (switchButton.isChecked()) {
//            Toast.makeText(SettingActivity.this, "2 switch MediaPlayer on!", Toast.LENGTH_SHORT).show();
            che_me = switchOn;
        } else {
//            Toast.makeText(SettingActivity.this, "2 switch MediaPlayer off!", Toast.LENGTH_SHORT).show();
            che_me = switchOff;
        }

        // for second switch button
        switchButton2 = (Switch) findViewById(R.id.switchButton2);
        switchButton2.setChecked(false);
        switchButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    Toast.makeText(SettingActivity.this, "1 switch Vibrator on!", Toast.LENGTH_SHORT).show();
                    che_vi = switchOn;
                } else {
                    Toast.makeText(SettingActivity.this, "1 switch Vibrator off!", Toast.LENGTH_SHORT).show();
                    che_vi = switchOff;
                }
            }
        });
        if (switchButton2.isChecked()) {
//            Toast.makeText(SettingActivity.this, "2 switch MediaPlayer on!", Toast.LENGTH_SHORT).show();
            che_vi = switchOn;
        } else {
//            Toast.makeText(SettingActivity.this, "2 switch MediaPlayer off!", Toast.LENGTH_SHORT).show();
            che_vi = switchOff;
        }

//        Intent intent = new Intent(this, PlaceSecActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("che_me", che_me);
//        bundle.putString("che_vi", che_vi);
//        intent.putExtras(bundle);


    }


}