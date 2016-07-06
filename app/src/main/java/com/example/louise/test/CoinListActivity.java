package com.example.louise.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CoinListActivity extends AppCompatActivity {

    private ListView coinlist;
    private Button searchcoin;
    public int[] prgmImages={R.drawable.coin,R.drawable.coin2};
    public String[] prgmNameList={"100","1000"};
    private String userrunelistjson = "";
//    private int[] rune;
//    private int[] stone;
    private List<Integer> runeid;
    private List<Integer> stone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_list);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        searchcoin = (Button) findViewById(R.id.searchcoin);
        searchcoin.setTextColor(0xffffffff);
        searchcoin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CoinListActivity.this, CoinActivity.class);
                startActivity(intent);
            }
        });

        coinlist=(ListView) findViewById(R.id.coinlist);


        try {
            userrunelistjson = Httpconnect.httpget("http://140.119.163.40:8080/GeniusLoci/userRuneList/app/list/" + IndexActivity.userid);
            JSONArray userrunelist = new JSONArray(userrunelistjson);

            for (int i = 0; i < userrunelist.length(); i++) {
                runeid.add(i);
                Toast.makeText(CoinListActivity.this, userrunelist.length(), Toast.LENGTH_SHORT).show();
//                runeid.add(userrunelist.getJSONObject(i).getInt("runeid"));
//                stone.add(userrunelist.getJSONObject(i).getInt("stone"));

            }








            final MyAdapter adapter;
            adapter = new MyAdapter(this, runeid, stone, prgmImages);
            coinlist.setAdapter(adapter);

//        coinlist = (ListView) findViewById(R.id.coinlist);
//        final MyAdapter adapter;
//        adapter = new MyAdapter(this, IndexActivity.userid);
//        coinlist.setAdapter(adapter);
        } catch (Exception e) {

        }
    }

    public class MyAdapter extends BaseAdapter{
        String [] prgmNameList;
        int [] prgmImages;
        private List<Integer> runeid;
        private List<Integer> stone;

        private LayoutInflater myInflater;
        public MyAdapter(Context c, List<Integer> runeid, List<Integer> stone, int[] prgmImages) {
            // TODO Auto-generated constructor stub
//            this.prgmNameList=prgmNameList;
            this.runeid = runeid;
            this.stone = stone;
            this.prgmImages=prgmImages;
            myInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return runeid.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return runeid.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        private TextView tv;
        private ImageView img;
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View rowView;
            rowView = myInflater.inflate(R.layout.coinlist, null);
            tv=(TextView) rowView.findViewById(R.id.coinnum);
            img=(ImageView) rowView.findViewById(R.id.coinimg);

            if (runeid.get(position) == 1) {
                img.setImageResource(prgmImages[0]);
                tv.setText(stone.get(position) + " 個");
            } else if (runeid.get(position) == 2) {
                img.setImageResource(prgmImages[1]);
                tv.setText(stone.get(position) + " 個");
            }




            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//                    Toast.makeText(CoinListActivity.this, "You Clicked " + prgmNameList[position], Toast.LENGTH_LONG).show();

                    LayoutInflater inflater = LayoutInflater.from(CoinListActivity.this);
                    final View view = inflater.inflate(R.layout.ditchcoin, null);
                    final EditText ditchcoinnum = (EditText) (view.findViewById(R.id.ditchcoinnum));
                    ditchcoinnum.setText(prgmNameList[position]);
                    //語法一：new AlertDialog.Builder(主程式類別).XXX.XXX.XXX;
                    new AlertDialog.Builder(CoinListActivity.this)
                            .setView(view)
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String name = ditchcoinnum.getText().toString(); // update name
                                    String re = "";


//                                            // create connection to post update data to web api
//                                            try {
//
//                                                updatejson = Httpconnect.httpget(url);
//                                                JSONArray userlist = new JSONArray(updatejson);
//
//                                                String id = userlist.getJSONObject(0).getString("id");
//                                                int level = userlist.getJSONObject(0).getInt("level");
//                                                int exp = userlist.getJSONObject(0).getInt("exp");
//                                                int votes = userlist.getJSONObject(0).getInt("votes");
//                                                String stuid= userlist.getJSONObject(0).getString("studentid");
//
//                                                if(id == IndexActivity.userid) {
//                                                    re = Httpconnect.httpost2("http://140.119.163.40:8080/Spring08/app/user/" + IndexActivity.userid,
//                                                            "name=" + name + "&studentid=" + stuid + "&email=" + email + "&level=" + level + "&exp=" + exp + "&votes=" + votes);
//                                                } else {
//                                                    re = "failed";
//                                                }
//                                            } catch (Exception e) {
            //                                    re = "輸入有誤 QQQQ";
            //                                    Toast toast2 = Toast.makeText(CoinListActivity.this, re, Toast.LENGTH_SHORT);
            //                                    toast2.show();
//                                            }
                                    try {
                                        int last = Integer.valueOf(prgmNameList[position]).intValue() - Integer.valueOf(name).intValue();
                                        if (last < 0) {
                                            re = "沒有那麼多的金幣喔 > <";
                                        } else {
                                            re = "剩下 " + Integer.toString(last) + " 個";
                                        }
                                        Toast toast2 = Toast.makeText(CoinListActivity.this, re, Toast.LENGTH_SHORT);
                                        toast2.show();
                                    } catch (Exception e) {
                                        re = "輸入有誤 QQQQ";
                                        Toast toast2 = Toast.makeText(CoinListActivity.this, re, Toast.LENGTH_SHORT);
                                        toast2.show();
                                    }

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

            return rowView;
        }

    }
}