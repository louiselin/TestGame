package com.example.louise.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

public class CoinListActivity extends AppCompatActivity implements LocationListener {

    private ListView coinlist;
    private Button searchcoin;
    private String userrunelistjson = "";

    private List<Integer> i = new ArrayList<>();
    private List<Integer> r = new ArrayList<>();
    private List<Integer> s = new ArrayList<>();

    private String txt_party = "";
    private String txt_user = "";
    private Double mapcurrla = MapsActivity.currla;
    private Double mapcurrlo = MapsActivity.currlo;

    Location location;
    LocationManager mgr;
    String best;
//    Double latitude=24.987155 , longitude=121.573579;

    private MediaPlayer coinbtn;
    private MediaPlayer ditchbtn;
    private String che_vi, che_me;
    private String switchOn = "ON";
    private String switchOff = "OFF";

    public static final String intent_me="ON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_list);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        try {

            FileReader fr = new FileReader(new File("sdcard/darkempire/output.txt"));
            BufferedReader br = new BufferedReader(fr);

            String temp = br.readLine(); //readLine()讀取一整行
            if (temp != null) {
                String[] datas = temp.split(",");
                che_vi = datas[1];
                che_me = datas[0];
            } else {
                che_vi = che_me = switchOff;
            }
        } catch (Exception e) {}

        try {

            FileReader fr = new FileReader(new File("sdcard/darkempire/profile.txt"));
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

        searchcoin = (Button) findViewById(R.id.searchcoin);
        searchcoin.setTextColor(0xffffffff);
        searchcoin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent_me.equals(che_me)) {
                    coinbtn = MediaPlayer.create(CoinListActivity.this, R.raw.searchcoin);
                    coinbtn.start();
                    coinbtn.seekTo(200);
                }
                Intent intent = new Intent();
                intent.setClass(CoinListActivity.this, CoinActivity.class);
                startActivity(intent);
            }
        });

        coinlist=(ListView) findViewById(R.id.coinlist);


        try {
            userrunelistjson = Httpconnect.httpget("http://140.119.163.40:8080/GeniusLoci/userRuneList/app/list/" + txt_user);
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
                final View view = inflater.inflate(R.layout.ditchcoin2, null);

                TextView selfuserid = (TextView) (view.findViewById(R.id.coinlist_userid2));
                selfuserid.setText("選擇贈送金幣id "+ r.get(mPosition)+"\n您的使用者 id 爲: " + txt_user);

                if (intent_me.equals(che_me)) {
                    ditchbtn = MediaPlayer.create(CoinListActivity.this, R.raw.ditchbtn);
                    ditchbtn.start();
                }

                final EditText ditchcoinnum = (EditText) (view.findViewById(R.id.ditchcoinnum2));
//                ditchcoinnum.setText(s.get(mPosition));

//                final EditText senduserid = (EditText) (view.findViewById(R.id.senduserid));
//                senduserid.setText("1");

                //語法一：new AlertDialog.Builder(主程式類別).XXX.XXX.XXX;
                new AlertDialog.Builder(CoinListActivity.this)
                        .setView(view)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


//                                String userid = IndexActivity.userid;
                                int runeid = r.get(mPosition);
                                String runenum = ditchcoinnum.getText().toString();
//                                String sendid = senduserid.getText().toString();

                                try {
                                    int last = Integer.valueOf(s.get(mPosition)).intValue() - Integer.valueOf(runenum).intValue();
                                    if (last < 0) {
                                        Toast.makeText(CoinListActivity.this, "沒有那麼多的金幣喔 > <", Toast.LENGTH_SHORT).show();
                                    } else {
                                        try {
                                            if (mapcurrlo == null || mapcurrla == null) {
                                                mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
                                                Criteria criteria = new Criteria();
                                                best = mgr.getBestProvider(criteria, true);
                                                location = mgr.getLastKnownLocation(best);

                                                if(location != null){
                                                    mapcurrla = location.getLatitude();
                                                    mapcurrlo = location.getLongitude();

                                                    res = Httpconnect.httpget("http://140.119.163.40:8080/GeniusLoci/runeTransaction/app/throw/" + txt_user
                                                            + "/" + runeid + "/" + runenum + "/" + mapcurrlo + "/" + mapcurrla + "/");
                                                    if (!res.equals("error")) {
                                                        Toast.makeText(CoinListActivity.this, "剩下 " + Integer.toString(last) + " 個, 重整畫面更新 ><", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(CoinListActivity.this, "輸入有誤 QQQQ", Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    Toast.makeText(CoinListActivity.this, "請先到開始遊戲做巡邏或淨化動作 ><", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                res = Httpconnect.httpget("http://140.119.163.40:8080/GeniusLoci/runeTransaction/app/throw/" + txt_user
                                                        + "/" + runeid + "/" + runenum + "/" + mapcurrlo + "/" + mapcurrla + "/");
                                                if (!res.equals("error")) {
                                                    Toast.makeText(CoinListActivity.this, "剩下 " + Integer.toString(last) + " 個, 重整畫面更新 ><", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(CoinListActivity.this, "輸入有誤 QQQQ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(CoinListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                } catch (Exception e) {
                                    Toast.makeText(CoinListActivity.this, "請填入數值！", Toast.LENGTH_SHORT).show();
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

    //implements LocationListener所自動產生的函式
    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}