package com.example.louise.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.ProtocolException;

public class StoryActivity extends AppCompatActivity {

    private String story = "";
    public static String party = "";
    private ImageView party_s;
    private ImageView party_a;
//    private String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);


            story = "500年前，統治暗黑大陸的印琛(Jinzen)家族第七代傳人發生內戰，戰敗的蚋轅(Ruyen)決定尋找4000年前脫逃者所帶走的超原力，因而一路找尋到新世界。來到新世界的蚋轅為了在拳杉堡建立基地，殺害了無數安塔雅人與席奈人，並挑起兩族仇恨，擾亂了千年的和平。\n\n"
                    + "蚋轅直到臨終前都沒有找到超原力，他與惡魔立約，如果能讓他找到超原力，他的靈魂願意成為惡魔的坐騎。惡魔於是結束了蚋轅的生命，將他化為一匹黑馬，並開始在拳杉堡四處潛伏，搜尋超原力。而席奈人與安塔亞人則聯合起來保護超原力，驅趕暗黑勢力。參與這場保衛戰的我們，都是席奈人與安塔雅人的後代，是兩族人共同的血脈。\n\n"
                    + "不管你投入哪一方，只代表你決定在這場戰役中扮演甚麼任務。這場戰役，唯有靠我們通力合作，才可能守住拳杉堡、保護超原力不被奪走。\n\n";
            TextView textstory = (TextView) findViewById(R.id.story);
            textstory.setText(story);
            textstory.setTextSize(18);
            System.gc();

        party_a = (ImageView) findViewById(R.id.party_a);
        party_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(StoryActivity.this);
                ad.setTitle("決定好陣營了嗎?");
                ad.setPositiveButton("我要當安塔雅人^^", new DialogInterface.OnClickListener() {//退出按鈕
                    public void onClick(DialogInterface dialog, int i) {
                        // TODO Auto-generated method stub
                        party = "Antayen";
                        String re = "";
                        String url = "http://140.119.163.40:8080/Spring08/app/usercamp/"+IndexActivity.userid+"/3/";
                        try {

                            re = Httpconnect.httpget(url);
                            Toast.makeText(StoryActivity.this, re.replace("\n", ""), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(StoryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }


                        if (re.replace("\n", "").replace(" ", "").equals("success")) {
                            try {
                                String res = IndexActivity.userid + "," + party + ",3";
                                FileWriter fw = new FileWriter(new File("sdcard/profile.txt"));
                                final BufferedWriter bw = new BufferedWriter(fw); //將BufferedWeiter與FileWrite物件做連結
                                bw.write(res);
                                bw.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(StoryActivity.this, "Not Save!", Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent();
                        intent.setClass(StoryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                ad.setNegativeButton("不要啦我要換><", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        return;
                    }
                });
                ad.show();
            }
        });
        party_s = (ImageView) findViewById(R.id.party_s);
        party_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(StoryActivity.this);
                ad.setTitle("決定好陣營了嗎?");
                ad.setPositiveButton("我要當席奈人^^", new DialogInterface.OnClickListener() {//退出按鈕
                    public void onClick(DialogInterface dialog, int i) {
                        // TODO Auto-generated method stub
                        party = "Sinae";
                        String re = "";
                        String url = "http://140.119.163.40:8080/Spring08/app/usercamp/"+IndexActivity.userid+"/2/";

                        try {
                            re = Httpconnect.httpget(url);
                            Toast.makeText(StoryActivity.this, re.replace("\n", "").replace(" ", ""), Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            Toast.makeText(StoryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        if (re.replace("\n", "").replace(" ", "").equals("success")) {
                            try {
                                String res = IndexActivity.userid + "," + party + ",2";
                                FileWriter fw = new FileWriter(new File("sdcard/profile.txt"));
                                final BufferedWriter bw = new BufferedWriter(fw); //將BufferedWeiter與FileWrite物件做連結
                                bw.write(res);
                                bw.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(StoryActivity.this, "Not Save!", Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent();
                        intent.setClass(StoryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                ad.setNegativeButton("不要啦我要換><", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        return;
                    }
                });
                ad.show();
            }
        });


//            Button storybtn = (Button) findViewById(R.id.party);
//            storybtn.setTextColor(0xffffffff);
//            storybtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    AlertDialog.Builder ad = new AlertDialog.Builder(StoryActivity.this);
//                    ad.setTitle("決定好陣營了嗎？");
//                    ad.setPositiveButton("席奈人", new DialogInterface.OnClickListener() {//退出按鈕
//                        public void onClick(DialogInterface dialog, int i) {
//                            // TODO Auto-generated method stub
//                            party = "Sinae";
//                            Intent intent = new Intent();
//                            intent.setClass(StoryActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    ad.setNegativeButton("安塔雅人", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int i) {
//                            party = "Antayen";
//                            Intent intent = new Intent();
//                            intent.setClass(StoryActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    ad.show();
//                }
//            });
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            ConfirmExit();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    public void ConfirmExit(){
//        AlertDialog.Builder ad=new AlertDialog.Builder(StoryActivity.this);
//        ad.setTitle("退出確認");
//        ad.setMessage("您確定要退出了嗎？");
//        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
//            public void onClick(DialogInterface dialog, int i) {
//                // TODO Auto-generated method stub
//                finish();//關閉activity
//            }
//        });
//        ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int i) {
//                return;
//            }
//        });
//        ad.show();
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //System.exit(0);
//    }
}
