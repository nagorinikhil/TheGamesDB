/*
Homework 05
Game.java
Hozefa Haveliwala / Nikhil Nagori - Group 29
 */

package com.thegamesdb.thegamesdb;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nikhil on 16/02/2017.
 */

public class Game implements Serializable {
    String id;
    String title;
    String overview;
    String imageUrl;
    String genre;
    String publisher;
    String trailerUrl;
    String releaseDate;
    String platform;
    ArrayList<String> similarIdList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<String> getSimilarIdList() {
        return similarIdList;
    }

    public void setSimilarIdList(ArrayList<String> similarIdList) {
        this.similarIdList = similarIdList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }


    @Override
    public String toString() {
        return title +
                ", Released In " + releaseDate +
                ", Platform: " + platform;
    }
}
