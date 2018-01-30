/*
Homework 05
GameUtility.java
Hozefa Haveliwala / Nikhil Nagori - Group 29
 */


package com.thegamesdb.thegamesdb;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Nikhil on 16/02/2017.
 */

public class GameUtility {
    static public class GamePullParser {
        static Game parseGame(InputStream in) throws XmlPullParserException, IOException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Game game = null;
            //ArrayList<Game> gameList = new ArrayList<Game>();
            int event = parser.getEventType();
            ArrayList<String> similarIdList = new ArrayList<String>();
            int flag_id = 0, flag_image = 0;

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("Data")) {
                            game = new Game();
                            flag_id = 0;
                            flag_image = 0;
                        } else if (parser.getName().equals("GameTitle")) {
                            game.setTitle(parser.nextText().trim());
                        } else if (parser.getName().equals("Overview")) {
                            game.setOverview(parser.nextText().trim());
                        } else if (parser.getName().equals("genre")) {
                            game.setGenre(parser.nextText().trim());
                        } else if (parser.getName().equals("Youtube")) {
                            game.setTrailerUrl(parser.nextText().trim());
                        } else if (parser.getName().equals("Publisher")) {
                            game.setPublisher(parser.nextText().trim());
                        } else if (parser.getName().equals("id")) {
                            flag_id++;
                            if (flag_id > 1) {
                                similarIdList.add(parser.nextText().trim());
                            }
                        } else if (parser.getName().equals("Images")) {
                            if(flag_image==0){
                                event = parser.next();
//                                Log.d("demo", parser.getName());
                                event = parser.next();
                               // Log.d("demo", parser.getName());
                                if(event == XmlPullParser.START_TAG){
                                   if(parser.getName().equals("fanart") || parser.getName().equals("screeshot")){
                                       event = parser.next();
                                       if(event == XmlPullParser.START_TAG){
                                           if(parser.getName().equals("original")){
                                               game.setImageUrl(parser.nextText().trim());
                                           }
                                           //   game.setImageUrl(parser.nextText().trim());
                                       }
                                   } else if(parser.getName().equals("boxart") ||parser.getName().equals("clearlogo") ||parser.getName().equals("banner")){
                                       game.setImageUrl(parser.nextText().trim());
                                   }
                                }
                                flag_image++;
                            }
                        } else if (parser.getName().equals("ReleaseDate")) {
                            game.setReleaseDate(parser.nextText().trim().substring(6, 10));
                        } else if (parser.getName().equals("Platform")) {
                            game.setPlatform(parser.nextText().trim());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("Similar")) {
                            game.setSimilarIdList(similarIdList);
                        }
                        break;
                }
                event = parser.next();
            }
            return game;
        }
    }
}
