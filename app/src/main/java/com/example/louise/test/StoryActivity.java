package com.example.louise.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    private String story = "";
    public static String party = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        story = "玩家需要在魔法學園各處，向神獸煉信任度。信任度最高的玩家，神獸會向他簽契約，成為神獸的守護者。玩家便要努力培養與各個神獸的信任度，爭取向神獸簽契約。當每次與神獸煉信任度的時候，玩家會得到神獸贈與的種子，種子可以用來減損/強化其他玩家的信任度，增加玩家間的競爭。而玩家成為守護者之後，隨著契約維持的時間夠長，神獸會再贈與守護者金幣（或是別的東西），以供獎勵。";
        TextView textstory = (TextView) findViewById(R.id.story);
        textstory.setText(story);

        Button storybtn = (Button) findViewById(R.id.party);
        storybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(StoryActivity.this);
                ad.setTitle("決定好陣營了嗎？");
                ad.setPositiveButton("席奈人", new DialogInterface.OnClickListener() {//退出按鈕
                    public void onClick(DialogInterface dialog, int i) {
                        // TODO Auto-generated method stub
                        party = "Sinae";
                        Intent intent = new Intent();
                        intent.setClass(StoryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                ad.setNegativeButton("安塔雅人", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        party = "Antayen";
                        Intent intent = new Intent();
                        intent.setClass(StoryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                ad.show();
            }
        });


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
        AlertDialog.Builder ad=new AlertDialog.Builder(StoryActivity.this);
        ad.setTitle("要退出");
        ad.setMessage("確定要退出了嗎？");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                StoryActivity.this.finish();//關閉activity
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
