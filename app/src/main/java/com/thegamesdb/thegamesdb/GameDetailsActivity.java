/*
Homework 05
GameDetailsActivity.java
Hozefa Haveliwala / Nikhil Nagori - Group 29
 */


package com.thegamesdb.thegamesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GameDetailsActivity extends AppCompatActivity implements GetGameDetailAsyncTask.setGame {
    Game gameObj;
    TextView textViewGenre, textViewPublish, textViewDetails, textViewName;
    ProgressDialog pd;
    ArrayList<Game> similarGameList;
    ArrayList<String> similarIdList;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        textViewName = (TextView) findViewById(R.id.textViewGameName);
        textViewDetails = (TextView) findViewById(R.id.textViewDetails);
        textViewGenre = (TextView) findViewById(R.id.textViewGenre);
        textViewPublish = (TextView) findViewById(R.id.textViewPub);
        imageView = (ImageView) findViewById(R.id.imageView);
        similarGameList = new ArrayList<Game>();
        similarIdList = new ArrayList<String>();
        pd = new ProgressDialog(this);

        gameObj = new Game();
        gameObj = (Game) getIntent().getSerializableExtra(getResources().getString(R.string.Game));
        similarIdList = gameObj.getSimilarIdList();
//        Log.d("Image URL",gameObj.getImageUrl());
        Picasso.with(this)
                .load(getResources().getString(R.string.baseURLImage) + gameObj.getImageUrl())
                .resize(200,100)
                .into(imageView);
        textViewName.setText(gameObj.getTitle());
        textViewDetails.setText(gameObj.getOverview());
        if (gameObj.getGenre() != null) {
            textViewGenre.setText("Genre: " + gameObj.getGenre());
        } else {
            textViewGenre.setText("Genre: ");
        }
        if (gameObj.getPublisher() != null) {
            textViewPublish.setText("Publisher: " + gameObj.getPublisher());
        } else {
            textViewPublish.setText("Publisher: ");
        }


        findViewById(R.id.buttonTrailer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameObj.getTrailerUrl() != null) {
                    Intent intent = new Intent(GameDetailsActivity.this, GameTrailerActivity.class);
                    intent.putExtra(getResources().getString(R.string.Trailer), gameObj.getTrailerUrl());
                    startActivity(intent);
                } else {
                    Toast.makeText(GameDetailsActivity.this, "No Trailer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.buttonSimilarGames).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < similarIdList.size(); i++) {
                    new GetGameDetailAsyncTask(GameDetailsActivity.this).execute(getResources().getString(R.string.baseURlGetGame) + similarIdList.get(i));
                }
            }
        });

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void showDialog() {
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    public void dismissDialog() {
        pd.dismiss();
    }

    @Override
    public void setGameDetails(Game result) {
        if (result != null) {
            similarGameList.add(result);
        }
        if (similarIdList.size() == similarGameList.size()) {
            Log.d("Similar List size", String.valueOf(similarGameList.size()));
            Intent intent = new Intent(GameDetailsActivity.this, SimilarGamesActivity.class);
            intent.putExtra(getResources().getString(R.string.Game), similarGameList);
            intent.putExtra("GameTitle", gameObj.getTitle());
            startActivity(intent);
        }
    }
}
