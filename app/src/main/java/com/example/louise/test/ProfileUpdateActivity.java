package com.example.louise.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.ProtocolException;


public class ProfileUpdateActivity extends AppCompatActivity {
    String url = "http://140.119.163.40:8080/Spring08/app/user/"+IndexActivity.userid;
    private String stuid="";
    private String name="";
    private String email="";
    private int level=0;
    private int exp=0;
    private int votes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
//        switch (StoryActivity.party) {
//            case "Sinae": getWindow().setBackgroundDrawableResource(R.drawable.blue); break;
//            default: getWindow().setBackgroundDrawableResource(R.drawable.red); break;
//        }
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        String userjson="";


        try {
            userjson = Httpconnect.httpget(url);

        } catch (ProtocolException e) {
            e.printStackTrace();
        }



        try {
            JSONArray userlist = new JSONArray(userjson);

            stuid= userlist.getJSONObject(0).getString("studentid");
            name= userlist.getJSONObject(0).getString("name");
            email= userlist.getJSONObject(0).getString("email");
            level= userlist.getJSONObject(0).getInt("level");
            exp= userlist.getJSONObject(0).getInt("exp");
            votes= userlist.getJSONObject(0).getInt("votes");


        } catch (JSONException e) {
            e.printStackTrace();
        }

//        final EditText editText = (EditText) findViewById(R.id.nametext);
//        editText.setText(name);
//
//        final EditText editText2 = (EditText) findViewById(R.id.emailtext);
//        editText2.setText(email);

        final TextView myTextView5 = (TextView)findViewById(R.id.keepername);
        myTextView5.setText("玩家匿稱：" + name);

        final TextView myTextView6 = (TextView)findViewById(R.id.upemail);
        myTextView6.setText("玩家信箱：" + email);

        final TextView myTextView8 = (TextView)findViewById(R.id.keeperlevel);
        myTextView8.setText("遊戲等級：" + level);


        final TextView myTextView9 = (TextView)findViewById(R.id.keepervotes);
        myTextView9.setText("馬納數值：" + votes);


        final TextView myTextView10 = (TextView)findViewById(R.id.exp);
        myTextView10.setText("遊戲經驗：" + exp);


//        final Button update = (Button) findViewById(R.id.update);
//        update.setTextColor(0xffffffff);
//        update.setOnClickListener(new View.OnClickListener() {
//
//            String updatejson = "";
//
//            @Override
//            public void onClick(View v) {
//
//                if (v == update) {
//                    String name = editText.getText().toString(); // update name
//                    String email = editText2.getText().toString(); // update email
//                    String re = "";
//
//
//                    // create connection to post update data to web api
//                    try {
//
//                        updatejson = Httpconnect.httpget(url);
//                        JSONArray userlist = new JSONArray(updatejson);
//
//                        String id = userlist.getJSONObject(0).getString("id");
//                        int level = userlist.getJSONObject(0).getInt("level");
//                        int exp = userlist.getJSONObject(0).getInt("exp");
//                        int votes = userlist.getJSONObject(0).getInt("votes");
//                        String stuid= userlist.getJSONObject(0).getString("studentid");
//
//                        if(id == IndexActivity.userid) {
//                            re = Httpconnect.httpost2("http://140.119.163.40:8080/Spring08/app/user/" + IndexActivity.userid,
//                                    "name=" + name + "&studentid=" + stuid + "&email=" + email + "&level=" + level + "&exp=" + exp + "&votes=" + votes);
//                        } else {
//                            re = "failed";
//                        }
//                    } catch (Exception e) {
//                    }
//                    Toast toast2 = Toast.makeText(ProfileUpdateActivity.this, re, Toast.LENGTH_SHORT);
//                    toast2.show();
//
//                    Intent intent = new Intent();
//                    intent.setClass(ProfileUpdateActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });

        final Button alerchange = (Button) findViewById(R.id.alertchange);
        alerchange.setOnClickListener(new View.OnClickListener() {
            String updatejson = "";
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(ProfileUpdateActivity.this);
                final View v = inflater.inflate(R.layout.porfileupdate, null);
                final EditText nametext = (EditText) (v.findViewById(R.id.a_nametext));
                final EditText emailtext = (EditText) (v.findViewById(R.id.a_emailtext));
                nametext.setText(name);
                emailtext.setText(email);
                //語法一：new AlertDialog.Builder(主程式類別).XXX.XXX.XXX;
                new AlertDialog.Builder(ProfileUpdateActivity.this)
                        .setView(v)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                    String name = nametext.getText().toString(); // update name
                                    String email = emailtext.getText().toString(); // update email
                                    String re = "";


                                    // create connection to post update data to web api
                                    try {

                                        updatejson = Httpconnect.httpget(url);
                                        JSONArray userlist = new JSONArray(updatejson);

                                        String id = userlist.getJSONObject(0).getString("id");
                                        int level = userlist.getJSONObject(0).getInt("level");
                                        int exp = userlist.getJSONObject(0).getInt("exp");
                                        int votes = userlist.getJSONObject(0).getInt("votes");
                                        String stuid= userlist.getJSONObject(0).getString("studentid");

                                        if(id == IndexActivity.userid) {
                                            re = Httpconnect.httpost2("http://140.119.163.40:8080/Spring08/app/user/" + IndexActivity.userid,
                                                    "name=" + name + "&studentid=" + stuid + "&email=" + email + "&level=" + level + "&exp=" + exp + "&votes=" + votes);
                                        } else {
                                            re = "failed";
                                        }
                                    } catch (Exception e) {
                                    }
                                    Toast toast2 = Toast.makeText(ProfileUpdateActivity.this, re, Toast.LENGTH_SHORT);
                                    toast2.show();

                                    Intent intent = new Intent();
                                    intent.setClass(ProfileUpdateActivity.this, MainActivity.class);
                                    startActivity(intent);

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .show();
            }
        });


    }

}
