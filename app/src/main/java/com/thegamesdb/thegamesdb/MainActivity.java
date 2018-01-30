/*
Homework 05
MainActivity.java
Hozefa Haveliwala / Nikhil Nagori - Group 29
 */


package com.thegamesdb.thegamesdb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.R.color.holo_blue_light;

public class MainActivity extends AppCompatActivity implements GetGameListAsyncTask.changeButtons, GetGameDetailAsyncTask.setGame {
    EditText editTextInput;
    RadioGroup radioGroupChoice;
    ArrayList<Game> gameList;
    Game gameObj;
    Button buttonGo;
    ProgressDialog pD1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextInput = (EditText) findViewById(R.id.editTextInput);
        radioGroupChoice = (RadioGroup) findViewById(R.id.radioGroupGL);
        buttonGo = (Button) findViewById(R.id.buttonGo);

        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected() == true) {
                    if (editTextInput.length() == 0) {
                        Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            new GetGameListAsyncTask(MainActivity.this).execute(getResources().getString(R.string.baseURLGetGameList) + URLEncoder.encode(editTextInput.getText().toString(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No Internet connection!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroupChoice.getCheckedRadioButtonId() != -1) {
                    new GetGameDetailAsyncTask(MainActivity.this).execute(getResources().getString(R.string.baseURlGetGame) + gameList.get(radioGroupChoice.getCheckedRadioButtonId() - 1).getId());
                }


            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager cM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cM.getActiveNetworkInfo();
        if ((networkInfo != null) && (networkInfo.isConnected() == true)) {
            return true;
        }
        return false;
    }

    public void addChoice(String choice, int id) {
        RadioButton rb = new RadioButton(MainActivity.this);
        rb.setText(choice);
        // rb.setTypeface(Typeface.DEFAULT_BOLD);
        rb.setTextSize(16);
        rb.setId(id);
        rb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rb.setBackground(getDrawable(R.drawable.border_bottom));
        rb.setPadding(5, 15, 5, 15);
        radioGroupChoice.addView(rb);
    }

    @Override
    public void enableNavigation() {
        buttonGo.setEnabled(true);
        buttonGo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(holo_blue_light)));
        buttonGo.setTextColor(Color.WHITE);
        //buttonGo.setBackgroundResource(getColor(R.color.colorPrimary));
    }

    @Override
    public void showDialog() {
        pD1 = new ProgressDialog(MainActivity.this);
        pD1.setCancelable(false);
        pD1.show();

    }

    @Override
    public void dismissDialog() {
        pD1.dismiss();
    }

    @Override
    public void setGameDetails(Game result) {
        if (result == null) {
            Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            gameObj = new Game();
            gameObj = result;
            Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
            intent.putExtra(getResources().getString(R.string.Game), gameObj);
            startActivity(intent);
        }
    }

    @Override
    public void setItemArrayList(ArrayList<Game> result) {
        if (result == null) {
            Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            gameList = new ArrayList<>();
            gameList = result;
            for (int i = 0; i < gameList.size(); i++) {
                addChoice(gameList.get(i).toString(), i + 1);
            }
        }
    }
}
