package com.example.louise.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
                        int test = 0;
                        if (Build.VERSION.SDK_INT >= 23) { // platform version android 6
                            // Marshmallow+
                            test = runStreamWrapper();
                            Toast toast = Toast.makeText(MainActivity.this, "API 23", Toast.LENGTH_SHORT);
                            toast.show();

                        } else {
                            // Pre-Marshmallow
                            int version = Build.VERSION.SDK_INT;
                            test = 1;
                            Toast toast = Toast.makeText(MainActivity.this, "API " + version, Toast.LENGTH_SHORT);
                            toast.show();
                            runStream();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
//                        if(test==10) {
//                            Intent intent = new Intent();
//                            intent.setClass(MainActivity.this, MapsActivity.class);
//                            startActivity(intent);
//                        }
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

    private static final String TAG = MainActivity.class.getSimpleName();

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 0;

    @TargetApi(Build.VERSION_CODES.M)
    private int runStreamWrapper () {

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        int tt = PackageManager.PERMISSION_GRANTED;
//        Toast toast2 = Toast.makeText(MainActivity.this, "test1: "+ tt, Toast.LENGTH_SHORT);
//        toast2.show();

        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION)) permissionsNeeded.add("GPS");
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//            Toast toast = Toast.makeText(MainActivity.this, "show package manager!" + PackageManager.PERMISSION_GRANTED, Toast.LENGTH_SHORT);
//            toast.show();
        } else {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//            int ee = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//            Toast toast0 = Toast.makeText(MainActivity.this, "test2: " + ee, Toast.LENGTH_SHORT);
//            toast0.show();
//
//        }
//        else {
//            int ss = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//            Toast toast0 = Toast.makeText(MainActivity.this, "test3: " + ss, Toast.LENGTH_SHORT);
//            toast0.show();
//        }
//        int ss = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//        int ee = PackageManager.PERMISSION_GRANTED;
//            Toast toast0 = Toast.makeText(MainActivity.this, "test3: " + ss + " ee " + ee, Toast.LENGTH_SHORT);
//            toast0.show();
//        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
//            permissionsNeeded.add("microphone");
//        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
//            permissionsNeeded.add("camera");

//        if (permissionsList.size() > 0) {
//            if (permissionsNeeded.size() > 0) {
//                String message = "You need to grant access to the " + permissionsNeeded.get(0);
//                Toast toast1 = Toast.makeText(MainActivity.this, "test1: "+permissionsNeeded.get(0), Toast.LENGTH_SHORT);
//                toast1.show();
//                for (int i = 1; i < permissionsNeeded.size(); i++)
//                    message = message + ", " + permissionsNeeded.get(i);
//                showMessageOKCancel(message, new DialogInterface.OnClickListener() {
//                    @TargetApi(Build.VERSION_CODES.M)
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//                    }
//                });
//            }
//            Toast toast2 = Toast.makeText(MainActivity.this, "test2: "+ permissionsList.size(), Toast.LENGTH_SHORT);
//            toast2.show();
//            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//        }

//        runStream();
        return tt;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission (List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if(!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel (String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void runStream() {
        Toast.makeText(this, "Stream is running", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
//                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.CAMERA,PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                for(int i = 0; i < permissions.length; i ++)
                    perms.put(permissions[i], grantResults[i]);

                if(perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    runStream();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Some required permissions have been denied", Toast.LENGTH_LONG).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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