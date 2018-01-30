/*
Homework 05
GameListUtility.java
Hozefa Haveliwala / Nikhil Nagori - Group 29
 */


package com.thegamesdb.thegamesdb;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Nikhil on 16/02/2017.
 */

public class GameListUtility {

    static public class GameListPullParser{
        static ArrayList<Game> parseGameList(InputStream in) throws XmlPullParserException, IOException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF-8");
            Game item = null;
            ArrayList<Game> itemList = new ArrayList<Game>();
            int event = parser.getEventType();
            String temp;
            while (event != XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("Game")){
                            item = new Game();
                        } else if(parser.getName().equals("id")){
                            item.setId(parser.nextText().trim());
                        } else if(parser.getName().equals("GameTitle")){
                            item.setTitle(parser.nextText().trim());
                        } else if(parser.getName().equals("ReleaseDate")){
                            temp = parser.nextText().trim();
                            if(temp.length() != 0){
                                item.setReleaseDate(temp.substring(temp.length()-4));
                            }
                        } else if(parser.getName().equals("Platform")){
                            item.setPlatform(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("Game")){
                            itemList.add(item);
                            item = null;
                        }
                        break;
                }
                event = parser.next();
            }
            return itemList;
        }
    }
}
