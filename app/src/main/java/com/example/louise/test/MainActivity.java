package com.example.louise.test;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.ProtocolException;


public class MainActivity extends AppCompatActivity
{
    private Button play;
    private Button profile;
    private Button manual;
    private Button award;
    private TextView textView;
    private String story = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        Toast toast = Toast.makeText(MainActivity.this, "Hello! " + IndexActivity.userid, Toast.LENGTH_SHORT);
//        toast.show();
//
//        if(IndexActivity.userid != "" ) {
//
//            if(IndexActivity.userid != "35") {
                String keepername = "";
                String userjson = "";
                try {
                    userjson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/user/" + IndexActivity.userid);

                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray userlist = new JSONArray(userjson);
                    keepername = userlist.getJSONObject(0).getString("name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                story = "玩家需要在魔法學園各處，向神獸煉信任度。信任度最高的玩家，神獸會向他簽契約，成為神獸的守護者。玩家便要努力培養與各個神獸的信任度，爭取向神獸簽契約。當每次與神獸煉信任度的時候，玩家會得到神獸贈與的種子，種子可以用來減損/強化其他玩家的信任度，增加玩家間的競爭。而玩家成為守護者之後，隨著契約維持的時間夠長，神獸會再贈與守護者金幣（或是別的東西），以供獎勵。";
                TextView textstory = (TextView) findViewById(R.id.story);
                textstory.setText(story);

                textView = (TextView) findViewById(R.id.keeper);
                textView.setText("攻佔吧！" + keepername);
                textView.setTextSize(15);
                textView.setTextColor(Color.BLACK);

                play = (Button) findViewById(R.id.playgame);
                play.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                    }
                });

                profile = (Button) findViewById(R.id.profile);
                profile.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, ProfileUpdateActivity.class);
                        startActivity(intent);
                    }
                });

                award = (Button) findViewById(R.id.award);
                award.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, AwardActivity.class);
                        startActivity(intent);
                    }
                });

                manual = (Button) findViewById(R.id.manual);
                manual.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, ManualActivity.class);
                        startActivity(intent);
                    }
                });
//            }
//        } else {
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, IndexActivity.class);
//            finish();
//            startActivity(intent);
//        }
    }


        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                ConfirmExit();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

        public void ConfirmExit(){
            AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
            ad.setTitle("要退出");
            ad.setMessage("確定要退出了嗎？");
            ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
                public void onClick(DialogInterface dialog, int i) {
                    // TODO Auto-generated method stub
                    MainActivity.this.finish();//關閉activity
                }
            });
            ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int i) {
                    return;
                }
            });
            ad.show();
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            //System.exit(0);
        }
}