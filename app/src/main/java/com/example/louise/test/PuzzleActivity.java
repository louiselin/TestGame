package com.example.louise.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PuzzleActivity extends AppCompatActivity {

    private String txt_party = "";
    private String txt_user = "";
    private ImageView iv_sp;
    private TextView superpower;
    private Button sp;
    private int res = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);
        try {

            FileReader fr = new FileReader(new File("sdcard/profile.txt"));
            BufferedReader br = new BufferedReader(fr);

            String temp = br.readLine(); //readLine()讀取一整行
//            Toast.makeText(SettingActivity.this, temp, Toast.LENGTH_LONG).show();

            if (temp != null) {
                String[] datas = temp.split(",");
                txt_party = datas[1];
                txt_user = datas[0];

            } else {
                txt_party = StoryActivity.party;
                txt_user = IndexActivity.userid;
            }
        } catch (Exception e) {}
        iv_sp = (ImageView) findViewById(R.id.iv_sp);
        iv_sp.setImageResource(R.drawable.power);
        superpower = (TextView) findViewById(R.id.superpower);
        superpower.setText(txt_party+" : "+0+", Other: "+0);
        sp = (Button) findViewById(R.id.releasesp);

        sp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ++res;
                int o_res = 0;

                superpower.setText(txt_party+" : "+res+", Other: "+o_res);
                int a = res + o_res;

                if (a == 6) {
                    if (txt_party.equals("Sinae")) {
                        iv_sp.setImageResource(R.drawable.rpower);
                    } else {
                        iv_sp.setImageResource(R.drawable.bpower);
                    }
                }
                if (a >5) res = 0;
            }
        });


    }

}
