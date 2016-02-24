package com.example.louise.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    private String story = "";
    public static String party = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        story = "拳杉堡是新世界的聖地。\n\n五百年前，印琛(Jinzen)的第七代傳人發生內戰，戰敗的蚋轅(Ruyen)決定尋找四千年前脫逃者所帶走的超原力，因而一路找尋到新世界。來到新世界的蚋轅為了在拳杉堡建立基地，殺害了無數安塔雅人與席奈人，甚至挑起兩族仇恨，擾亂了二千五百年來的和平。但是，直到臨終前蚋轅都沒有找到超原力，於是他與惡魔立約，如果能讓他找到超原力，他的靈魂願意成為惡魔的坐騎。惡魔於是結束了蚋轅的生命，將他化為一匹黑馬，並開始在拳杉堡四處潛伏，搜尋超原力。而席奈人與阿塔亞人也為了信守族訓，聯合起來保護超原力，並驅趕暗黑勢力。\n\n" +
                "安塔雅人在新世界住了五萬年，他們對拳杉堡瞭若指掌，善於防衛；席人奈則擅長攻擊，當年才能一路從暗黑大陸登上新世界。但是，參與這場保衛戰的我們，都是席奈人與安塔雅人的後代，我們同時流著兩族人的血脈。不管你投入哪一方，只代表你決定在這場戰役中扮演甚麼任務。這場戰役，只有靠我們通力合作，才可能守住拳杉堡、保護超原力不被奪走。";
        TextView textstory = (TextView) findViewById(R.id.story);
        textstory.setText(story);
        textstory.setTextSize(18);


        Button storybtn = (Button) findViewById(R.id.party);
        storybtn.setTextColor(0xffffffff);
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
        ad.setTitle("退出確認");
        ad.setMessage("您確定要退出了嗎？");
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
