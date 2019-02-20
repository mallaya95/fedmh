package com.first.msql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class adminhome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        WebView mywebview = (WebView) findViewById(R.id.webView);


        mywebview.loadUrl("http://192.168.20.96/people/main/admin/adminHome.php");
    }
}
