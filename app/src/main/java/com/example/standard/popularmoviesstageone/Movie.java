package com.example.standard.popularmoviesstageone;

/**
 * Created by vince on 04.06.2017.
 */

public class Movie {
    private String mPoster, mTitle, mOverview, mDate;
    private double mRating;

    public Movie(String mPoster) {
        this.mPoster = mPoster;
    }

    public Movie(String mPoster, String mTitle, String mOverview, String mDate, double mRating) {
        this.mPoster = mPoster;
        this.mTitle = mTitle;
        this.mOverview = mOverview;
        this.mDate = mDate;
        this.mRating = mRating;
    }

    public String getmPoster() {
        return mPoster;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmDate() {
        return mDate;
    }

    public double getmRating() {
        return mRating;
    }
}
