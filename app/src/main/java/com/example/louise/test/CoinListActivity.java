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
    private String userrunelistjson = "";

    private List<Integer> i = new ArrayList<>();
    private List<Integer> r = new ArrayList<>();
    private List<Integer> s = new ArrayList<>();

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

            i.add(R.drawable.coin);
            i.add(R.drawable.coin2);
            r.add(userrunelist.getJSONObject(0).getInt("runeid"));
            r.add(userrunelist.getJSONObject(1).getInt("runeid"));
            s.add(userrunelist.getJSONObject(0).getInt("stone"));
            s.add(userrunelist.getJSONObject(1).getInt("stone"));


            final MyAdapter adapter;
            adapter = new MyAdapter(this, r, s, i);
            coinlist.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(CoinListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public class MyAdapter extends BaseAdapter{
        private List<Integer> i = new ArrayList<>();
        private List<Integer> r = new ArrayList<>();
        private List<Integer> s = new ArrayList<>();

        private LayoutInflater myInflater;
        public MyAdapter(Context c, List<Integer> r, List<Integer> s,  List<Integer> i) {
            // TODO Auto-generated constructor stub
            this.i = i;
            this.r = r;
            this.s = s;
            myInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
//            return runeid.length;
            return s.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
//            return runeid[position];
            return s.get(position);
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

            img.setImageResource(i.get(position));
            tv.setText(s.get(position) + " 個");
            rowView.setOnClickListener(new OnClick(position));
            return rowView;
        }

        class OnClick implements View.OnClickListener{
            final int mPosition;
            public OnClick(int position){
                mPosition = position;
            }
            private String res ="";

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(CoinListActivity.this, "You Clicked " + r.get(mPosition) + "HAVE: " + s.get(mPosition), Toast.LENGTH_LONG).show();

                LayoutInflater inflater = LayoutInflater.from(CoinListActivity.this);
                final View view = inflater.inflate(R.layout.ditchcoin, null);

                TextView selfuserid = (TextView) (view.findViewById(R.id.coinlist_userid));
                selfuserid.setText("選擇贈送金幣id "+ r.get(mPosition)+"\n您的使用者 id 爲: " + IndexActivity.userid);

                final EditText ditchcoinnum = (EditText) (view.findViewById(R.id.ditchcoinnum));
//                ditchcoinnum.setText(s.get(mPosition));

                final EditText senduserid = (EditText) (view.findViewById(R.id.senduserid));
//                senduserid.setText("1");

                //語法一：new AlertDialog.Builder(主程式類別).XXX.XXX.XXX;
                new AlertDialog.Builder(CoinListActivity.this)
                        .setView(view)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                String userid = IndexActivity.userid;
                                int runeid = r.get(mPosition);
                                String runenum = ditchcoinnum.getText().toString();
                                String sendid = senduserid.getText().toString();


                                int last = Integer.valueOf(s.get(mPosition)).intValue() - Integer.valueOf(runenum).intValue();
                                if (last < 0) {
                                        Toast.makeText(CoinListActivity.this, "沒有那麼多的金幣喔 > <", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {
                                        res = Httpconnect.httpget("http://140.119.163.40:8080/GeniusLoci/userRuneList/app/send/" + userid
                                                + "/" + sendid + "/" + runeid + "/" + runenum);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                                    Toast.makeText(CoinListActivity.this, res, Toast.LENGTH_SHORT).show();
                                        if (!res.equals("error")) {
                                            Toast.makeText(CoinListActivity.this, "剩下 " + Integer.toString(last) + " 個\n重整畫面更新><", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(CoinListActivity.this, "輸入有誤 QQQQ", Toast.LENGTH_SHORT).show();
                                        }
                                }
//                                Intent intent = new Intent();
//                                intent.setClass(CoinListActivity.this, MainActivity.class);
//                                startActivity(intent);

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
        }
    }
}