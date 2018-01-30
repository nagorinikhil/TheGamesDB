/*
Homework 05
SimilarGamesActivity.java
Hozefa Haveliwala / Nikhil Nagori - Group 29
 */

package com.thegamesdb.thegamesdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SimilarGamesActivity extends AppCompatActivity {

    ArrayList<Game> gameObj;
    TextView textView_listText, textView_similiarTitle;
    LinearLayout linearLayout_listLayout;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_games);
        linearLayout_listLayout = (LinearLayout) findViewById(R.id.linearLayout_similarList);
        textView_similiarTitle = (TextView) findViewById(R.id.textViewSimilar);
        gameObj = new ArrayList<Game>();
        gameObj = (ArrayList<Game>) getIntent().getSerializableExtra(getResources().getString(R.string.Game));
        title = (String) getIntent().getStringExtra("GameTitle");
        textView_similiarTitle.setText("Similar games to " + title);
        Log.d("Similar game size", String.valueOf(gameObj.size()));
        Log.d("Similar Game", gameObj.toString());

        for (int i = 0; i < gameObj.size(); i++) {
            setGameList(gameObj.get(i).toString());
        }

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setGameList(String data) {
        textView_listText = new TextView(SimilarGamesActivity.this);
        textView_listText.setText(data);
        textView_listText.setTextSize(16);
        textView_listText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView_listText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView_listText.setBackground(getDrawable(R.drawable.border_bottom));
        textView_listText.setPadding(5, 10, 5, 10);
        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,0);
        textView_listText.setLayoutParams(params);*/
        linearLayout_listLayout.addView(textView_listText);
    }
}