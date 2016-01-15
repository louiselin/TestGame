package com.example.louise.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.ProtocolException;


public class ProfileUpdateActivity extends AppCompatActivity {
    String url = "http://140.119.163.40:8080/Spring08/app/user/"+IndexActivity.userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        String userjson="";


        try {
            userjson = Httpconnect.httpget(url);

        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        String stuid="";
        String name="";
        String email="";
        int level=0;
        int exp=0;
        int votes = 0;


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

        final EditText editText = (EditText) findViewById(R.id.nametext);
        editText.setText(name);

        final EditText editText2 = (EditText) findViewById(R.id.emailtext);
        editText2.setText(email);

        final TextView myTextView8 = (TextView)findViewById(R.id.keeperlevel);
        myTextView8.setText("遊戲等級：" + level);


        final TextView myTextView9 = (TextView)findViewById(R.id.keepervotes);
        myTextView9.setText("結緣種子：" + votes);


        final TextView myTextView10 = (TextView)findViewById(R.id.exp);
        myTextView10.setText("遊戲經驗：" + exp);


        final Button update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {

            String updatejson = "";

            @Override
            public void onClick(View v) {

                if (v == update) {
                    String name = editText.getText().toString(); // update name
                    String email = editText2.getText().toString(); // update email
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
            }
        });

    }

}
