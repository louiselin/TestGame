package com.example.louise.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class FirstpageActivity extends AppCompatActivity {


    private int test = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);
        if (Build.VERSION.SDK_INT >= 23) { // platform version android 6
            test = runStreamWrapper();

        } else {
            test = 1;


        }
        final Intent intent = new Intent();

        try {
            File file = new File("sdcard/profile.txt");
//            File file2 = new File("sdcard/output.txt");
            if (!file.exists()) {
                //Do action
                intent.setClass(FirstpageActivity.this, IndexActivity.class);
            } else {
                intent.setClass(FirstpageActivity.this, MainActivity.class);
            }
        } catch (Exception e) {
               Toast.makeText(FirstpageActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

//        intent.setClass(FirstpageActivity.this, IndexActivity.class);
        final Button loginbtn = (Button) findViewById(R.id.first);
        loginbtn.setTextColor(0xffffffff);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

//        Timer timer=new Timer();
//        TimerTask task=new TimerTask()
//        {
//            @Override
//            public void run(){
//                startActivity(intent);
////
//            }
//
//        };
//        timer.schedule(task, 2000);




//        Button bartest = (Button) findViewById(R.id.bartest);
//        bartest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(FirstpageActivity.this, BartestActivity.class);
//                startActivity(intent);
//            }
//        });


    }
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
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
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) permissionsNeeded.add("WRITE");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)) permissionsNeeded.add("READ");
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//            Toast toast = Toast.makeText(MainActivity.this, "show package manager!" + PackageManager.PERMISSION_GRANTED, Toast.LENGTH_SHORT);
//            toast.show();

        } else {
//            switch (condition) {
//                case "setting_coin": {
//                    Intent intent = new Intent();
//                    intent.setClass(MainActivity.this, SettingActivity.class);
//                    startActivity(intent);
//                } break;
//                case "getcoin": {
//                    Intent intent = new Intent();
//                    intent.setClass(MainActivity.this, CoinListActivity.class);
//                    startActivity(intent);
//                } break;
//                default: {
//                    Intent intent = new Intent();
//                    intent.setClass(MainActivity.this, MapsActivity.class);
//                    startActivity(intent);
//                } break;
//            }
        }
//        else {
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, MapsActivity.class);
//            startActivity(intent);
//        }
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
//        Toast.makeText(this, "Stream is running", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                for(int i = 0; i < permissions.length; i ++)
                    perms.put(permissions[i], grantResults[i]);

                if(perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    runStream();
//                    switch (condition) {
//                        case "setting_coin": {
//                            Intent intent = new Intent();
//                            intent.setClass(MainActivity.this, SettingActivity.class);
//                            startActivity(intent);
//                        } break;
//                        case "getcoin": {
//                            Intent intent = new Intent();
//                            intent.setClass(MainActivity.this, CoinListActivity.class);
//                            startActivity(intent);
//                        } break;
//                        default: {
//                            Intent intent = new Intent();
//                            intent.setClass(MainActivity.this, MapsActivity.class);
//                            startActivity(intent);
//                        } break;
//                    }
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
        AlertDialog.Builder ad=new AlertDialog.Builder(FirstpageActivity.this);
        ad.setTitle("退出確認");
        ad.setMessage("您確定要退出了嗎？");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                finish();//關閉activity
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
