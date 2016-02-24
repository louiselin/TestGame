package com.example.louise.test;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

public class PlaceSecActivity extends AppCompatActivity {

    private ArrayAdapter listAdapter;
    private List<String> listname = new ArrayList<String>();
    private List<String> listcheck = new ArrayList<String>();
    private Integer placeid;
    private String placename;
    private RatingBar ratingBar;
    private float hp_init = 20;
    private String hp = "";
    private String name = "";
    private String username = "";
    private ProgressBar myProgressBar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_sec);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);
//        switch (StoryActivity.party) {
//            case "Sinae": getWindow().setBackgroundDrawableResource(R.drawable.blue); break;
//            default: getWindow().setBackgroundDrawableResource(R.drawable.red); break;
//        }

        Bundle bundle = this.getIntent().getExtras();
        placeid = bundle.getInt("placeid");
        placename = bundle.getString("placename");
        AlertDialog.Builder ad = new AlertDialog.Builder(PlaceSecActivity.this);
        ad.setTitle("遊戲規則");
        ad.setMessage("您點選的是" + placename + "\n\n選擇「巡邏」增加馬納值來守護石碑;但是當不幸石碑守護者是敵方的時候，以「淨化」掠取。\n\n開始吧！勇士！\n");
        ad.setNegativeButton("開始遊戲", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                return;
            }
        });
        ad.show();


        String placejson = "";
        String imagejson = "";
        String stelejson = "";
        try {
            placejson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/place/" + placeid);
            imagejson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/image/" + placeid);
            stelejson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/stele/" + placeid);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        String placename = "";
        String image = "";

//            String hp = "";
        String camp = "";
//            String name = "";

        try {
            JSONArray placelist = new JSONArray(placejson);
            JSONArray imagelist = new JSONArray(imagejson);
            JSONArray stelelist = new JSONArray(stelejson);

            placename = placelist.getJSONObject(0).getString("name");
            image = imagelist.getJSONObject(0).getString("image");
            hp = stelelist.getJSONObject(0).getString("hp");
            camp = stelelist.getJSONObject(0).getString("camp");
            name = stelelist.getJSONObject(0).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        TextView myTextView5 = (TextView) findViewById(R.id.locationname);
        myTextView5.setText(placename);
        myTextView5.setTextSize(30);
        myTextView5.setTextColor(Color.BLACK);

        TextView tv = (TextView) findViewById(R.id.belong);
        switch (name) {
            case "Ruyen":
                tv.setText("屬於:" + camp);
                break;
            default:
                tv.setText("屬於:" + StoryActivity.party + camp);
                break;
        }
        tv.setTextSize(15);
        tv.setTextColor(Color.BLACK);

        TextView myTextView0 = (TextView) findViewById(R.id.lordname);
        myTextView0.setText("石碑守護者:" + name);
        myTextView0.setTextSize(15);
        myTextView0.setTextColor(Color.BLACK);

        TextView myTextView1 = (TextView) findViewById(R.id.blood);
        switch (name) {
            case "Ruyen":
                myTextView1.setText("敵方的生命值:" + hp);
                break;
            default:
                myTextView1.setText(StoryActivity.party + "方生命值:" + hp);
                break;
        }
        myTextView1.setTextSize(15);
        myTextView1.setTextColor(Color.BLACK);

//            ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//            ratingBar.setRating((Float.parseFloat(hp))/5);
//        Resources res = getResources();
//        switch (name) {
//            case "Ruyen": ratingBar.setProgressDrawable(res.getDrawable(R.drawable.ratingbar_color));break;
//            default: {
//                if(StoryActivity.party == "Sinae") ratingBar.setProgressDrawable(res.getDrawable(R.drawable.ratingbar_color_s));
//                else ratingBar.setProgressDrawable(res.getDrawable(R.drawable.ratingbar_color_a)); break;
//            }
//        }
//            Toast.makeText(PlaceSecActivity.this, String.valueOf((Float.parseFloat(hp))/5) + " " + name, Toast.LENGTH_SHORT).show();

        myProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        myProgressBar.setProgress(Integer.parseInt(hp) * 5);
        Resources res = getResources();
        switch (name) {
            case "Ruyen":
                myProgressBar.setProgressDrawable(res.getDrawable(R.drawable.black_progressbar));
                break;
            default: {
                if (StoryActivity.party == "Sinae") {
                    myProgressBar.setProgressDrawable(res.getDrawable(R.drawable.green_progressbar));
                    break;
                } else {
                    myProgressBar.setProgressDrawable(res.getDrawable(R.drawable.red_progressbar));
                    break;
                }
            }
        }
        //import image
        findViews();

        thirdImage.setImageDrawable(loadImageFromURL(image));

        String url = "http://140.119.163.40:8080/Spring08/app/user/" + IndexActivity.userid;
        String userjson = "";

        try {
            userjson = Httpconnect.httpget(url);

        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        int votes = 0;

        try {
            JSONArray userlist = new JSONArray(userjson);
            votes = userlist.getJSONObject(0).getInt("votes");
            username = userlist.getJSONObject(0).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView textview = (TextView) findViewById(R.id.patrolvalue);
        textview.setText("馬納值:" + votes);


        final TextView myTextView6 = (TextView) findViewById(R.id.fight);
        myTextView6.setText("淨化");
        myTextView6.setTextSize(20);
        myTextView6.setTextColor(Color.WHITE);
        myTextView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                myTextView6.setTextColor(Color.RED);

                String stelejson = "";
                String stelename = "";
                String userjson = "";
                String username = "";
                try {
                    stelejson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/stele/" + placeid);
                    userjson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/user/" + IndexActivity.userid);
                    JSONArray stelelist = new JSONArray(stelejson);
                    JSONArray userlist = new JSONArray(userjson);
                    username = userlist.getJSONObject(0).getString("name");
                    stelename = stelelist.getJSONObject(0).getString("name");

                    if (stelename.equals(username)) {
                        Toast toast = Toast.makeText(PlaceSecActivity.this, "幹嘛打自己啦@@", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        String att = "";
                        String api = "";
                        try {
                            api = "http://140.119.163.40:8080/Spring08/app/stele/attack/" + placeid + "/" + IndexActivity.userid;
                            att = Httpconnect.httpget(api);

                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }

                        Toast toast = Toast.makeText(PlaceSecActivity.this, att, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        final TextView myTextView8 = (TextView) findViewById(R.id.patrol);
        myTextView8.setText("巡邏");
        myTextView8.setTextSize(20);
        myTextView8.setTextColor(Color.WHITE);
        myTextView8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                myTextView8.setTextColor(Color.RED);
                String re = "";
                try {
                    re = Httpconnect.httpost2("http://140.119.163.40:8080/Spring08/app/checkinList/" + IndexActivity.userid, "id=0&placeid=" + placeid + "&longitude=121.2&latitude=223.5");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast toast2 = Toast.makeText(PlaceSecActivity.this, re, Toast.LENGTH_SHORT);
                toast2.show();
            }
        });


        handler.postDelayed(runnable, 2000);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private ImageView thirdImage;

    private void findViews() {
        thirdImage = (ImageView) findViewById(R.id.placeimg);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Drawable loadImageFromURL(String image) {
        try {
            byte[] data = Base64.decode(image, Base64.DEFAULT);
            Drawable draw = new BitmapDrawable(BitmapFactory.decodeByteArray(data, 0, data.length));
            return draw;
        } catch (Exception e) {
            //TODO handle error
            Log.i("loadingImg", e.toString());
            return null;
        }
    }


    private void refresh() {
        String placejson = "";
        String userjson = "";
        String stelejson = "";
        try {
            placejson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/place/" + placeid);
            userjson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/user/" + IndexActivity.userid);
            stelejson = Httpconnect.httpget("http://140.119.163.40:8080/Spring08/app/stele/" + placeid);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        String placename = "";
        int vote = 0;
        String hp = "";
        String camp = "";
        String name = "";

        try {
            JSONArray placelist = new JSONArray(placejson);
            JSONArray userlist = new JSONArray(userjson);
            JSONArray stelelist = new JSONArray(stelejson);

            placename = placelist.getJSONObject(0).getString("name");
            vote = userlist.getJSONObject(0).getInt("votes");
            hp = stelelist.getJSONObject(0).getString("hp");
            camp = stelelist.getJSONObject(0).getString("camp");
            name = stelelist.getJSONObject(0).getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView textview = (TextView) findViewById(R.id.patrolvalue);
        textview.setText("馬納值:" + vote);

        TextView myTextView5 = (TextView) findViewById(R.id.locationname);
        myTextView5.setText(placename);
        myTextView5.setTextSize(30);
        myTextView5.setTextColor(Color.BLACK);

        TextView tv = (TextView) findViewById(R.id.belong);
        switch (name) {
            case "Ruyen":
                tv.setText("屬於:" + camp);
                break;
            default:
                tv.setText("屬於:" + StoryActivity.party + camp);
                break;
        }
        tv.setTextSize(15);
        tv.setTextColor(Color.BLACK);

        TextView myTextView0 = (TextView) findViewById(R.id.lordname);
        myTextView0.setText("石碑守護者:" + name);
        myTextView0.setTextSize(15);
        myTextView0.setTextColor(Color.BLACK);

        TextView myTextView1 = (TextView) findViewById(R.id.blood);
        switch (name) {
            case "Ruyen":
                myTextView1.setText("敵方的生命值:" + hp);
                break;
            default:
                myTextView1.setText("您的生命值:" + hp);
                break;
        }
        myTextView1.setTextSize(15);
        myTextView1.setTextColor(Color.BLACK);


        myProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        myProgressBar.setProgress(Integer.parseInt(hp) * 5);
        Resources res = getResources();
        switch (name) {
            case "Ruyen":
                myProgressBar.setProgressDrawable(res.getDrawable(R.drawable.black_progressbar));
                break;
            default: {
                if (StoryActivity.party == "Sinae") {
                    myProgressBar.setProgressDrawable(res.getDrawable(R.drawable.green_progressbar));
                    break;
                } else {
                    myProgressBar.setProgressDrawable(res.getDrawable(R.drawable.red_progressbar));
                    break;
                }
            }
        }
//        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//        ratingBar.setRating((Float.parseFloat(hp)) / 5);
//        switch (name) {
//            case "Ruyen": ratingBar.setProgressDrawable(getResources().getDrawable(R.drawable.ratingbar_color));break;
//            default: {
//                if(StoryActivity.party == "Sinae") ratingBar.setProgressDrawable(getResources().getDrawable(R.drawable.ratingbar_color_s));
//                else ratingBar.setProgressDrawable(getResources().getDrawable(R.drawable.ratingbar_color_a)); break;
//            }
//        }
//        Toast.makeText(PlaceSecActivity.this,
//                String.valueOf((Float.parseFloat(hp))/5), Toast.LENGTH_SHORT).show();
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            refresh();
            handler.postDelayed(this, 2000);
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "PlaceSec Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.louise.test/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "PlaceSec Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.louise.test/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}