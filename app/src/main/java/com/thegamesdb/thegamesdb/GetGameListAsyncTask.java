/*
Homework 05
GetGameListAsyncTask.java
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

public class GetGameListAsyncTask extends AsyncTask<String, Void, ArrayList<Game>> {

    changeButtons cB;

    public GetGameListAsyncTask(changeButtons cB) {
        this.cB = cB;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        cB.showDialog();
    }

    @Override
    protected ArrayList<Game> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                return GameListUtility.GameListPullParser.parseGameList(in);
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
    protected void onPostExecute(ArrayList<Game> result) {
        if (result != null) {
            Log.d("Game List", result.toString());
            cB.enableNavigation();
        }
        cB.setItemArrayList(result);
        cB.dismissDialog();
        // /cB.enableNavigation();
        //cB.setItemArrayList(result);
        super.onPostExecute(result);
    }

    static public interface changeButtons {
        public void enableNavigation();
        public void showDialog();
        public void dismissDialog();
        public void setItemArrayList(ArrayList<Game> result);
    }
}
