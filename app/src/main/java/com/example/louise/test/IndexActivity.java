package com.example.louise.test;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.ProtocolException;



public class IndexActivity extends AppCompatActivity {
    private WebView myBrowser;
    public String htmlSourceCode = "";
    public  String myURL = "http://140.119.163.40:8080/Spring08/app/login";
    public static String url;
    public static   String userid;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        myBrowser = (WebView) findViewById(R.id.loginview);

        myBrowser.getSettings().setJavaScriptEnabled(true);// 支持javascript
        myBrowser.addJavascriptInterface(new Handler(),"handler");  // 相當于在網頁的js中增加一個handler類，實現java與WebView的js交互


        myBrowser.loadUrl(myURL);
        myBrowser.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url2, Bitmap favicon) {
                if (url2.length() > 54) {
                    if (url2.substring(0, 52).equals("http://140.119.163.40:8080/Spring08/app/authenticate")) {

                        String json = "";
                        try {
                            json = Httpconnect.httpget(url2);

                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }

                        try {
                            userid = new JSONArray(json).getJSONObject(0).getString("userid");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (userid.equalsIgnoreCase("35")) {
                            Intent intent = new Intent();
                            intent.setClass(IndexActivity.this, IndexActivity.class);
                            IndexActivity.this.finish();
                            startActivity(intent);
//                            AlertDialog.Builder dialog = new AlertDialog.Builder(IndexActivity.this);
//                            dialog.setMessage("登入失敗，再試一次！");
//                            dialog.show();
                            Toast toast = Toast.makeText(IndexActivity.this, "網路連線失敗，再試一次！", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Intent intent = new Intent();
                            url = url2;
                            intent.setClass(IndexActivity.this, StoryActivity.class);
                            IndexActivity.this.finish();
                            startActivity(intent);
                        }


                    }
                }
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                // 通過內部類定義的方法獲取html頁面加載的內容，這個需要添加在webview加載完成後的回調中
                // view.loadUrl("javascript:window.handler.show(document.body.innerHTML);");


                //view.loadUrl("javascript:window.handler.show('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                // super.onPageFinished(view, url);
            }
        });
    }


    final class Handler {

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void show(String data) {

            htmlSourceCode = data;
            // Toast.makeText(IndexActivity.this, "htmlSourceCode", Toast.LENGTH_SHORT).show();
            //  new AlertDialog.Builder(IndexActivity.this).setMessage(data).create().show();
        }
    }
}