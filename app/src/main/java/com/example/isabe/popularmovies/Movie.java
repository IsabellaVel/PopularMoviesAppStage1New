package com.example.isabe.popularmovies;

import java.util.SimpleTimeZone;

/**
 * Created by isabe on 2/17/2018.
 */

public class Movie {
    private String mOriginalTitle;
    private String mReleaseDate;
    private Double mVoteAverage;
    private String mOverviewMovie;
    private String mImageThumbnail;

    public Movie(String originalTitle, String releasedOnDate, String overview, String imageThumbnail, Double averageVote) {
        mReleaseDate = releasedOnDate;
        mOriginalTitle = originalTitle;
        mVoteAverage = averageVote;
        mOverviewMovie = overview;
        mImageThumbnail = imageThumbnail;
    }

    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public Double getmVoteAverage() {
        return mVoteAverage;
    }

    public String getmOverviewMovie() {
        return mOverviewMovie;
    }

    public String getmImageThumbnail() {
        return mImageThumbnail;
    }

}
