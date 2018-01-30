/*
Homework 05
GetGameDetailAsyncTask.java
Hozefa Haveliwala / Nikhil Nagori - Group 29
 */


package com.thegamesdb.thegamesdb;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nikhil on 16/02/2017.
 */

public class GetGameDetailAsyncTask extends AsyncTask<String, Void, Game> {

    setGame sG;

    public GetGameDetailAsyncTask(setGame sG) {
        this.sG = sG;
    }

    @Override
    protected void onPreExecute() {
        sG.showDialog();
        super.onPreExecute();
    }

    @Override
    protected Game doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                return GameUtility.GamePullParser.parseGame(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Game result) {
        if (result != null) {
            Log.d("GameDetails", result.toString());
        }
        sG.setGameDetails(result);
        sG.dismissDialog();
        super.onPostExecute(result);

    }

    static public interface setGame {
        public void showDialog();
        public void dismissDialog();
        public void setGameDetails(Game result);
    }
}
