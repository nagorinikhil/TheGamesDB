/*
Homework 05
GameTrailerActivity.java
Hozefa Haveliwala / Nikhil Nagori - Group 29
 */


package com.thegamesdb.thegamesdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GameTrailerActivity extends AppCompatActivity {
    String trailerURL;
    WebView wV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_trailer);

        trailerURL = getIntent().getStringExtra(getResources().getString(R.string.Trailer));
        wV = (WebView) findViewById(R.id.webView1);
        wV.getSettings().setLoadWithOverviewMode(true);
        wV.getSettings().setJavaScriptEnabled(true);
        wV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return false;
            }
        });
        wV.loadUrl(trailerURL);

    }
}
