package com.example.standard.popularmoviesstageone;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vince on 04.06.2017.
 */

public class Movie implements Parcelable {
    private String mPoster, mTitle, mOverview, mDate, mRating;

    public Movie(String mPoster) {
        this.mPoster = mPoster;
    }

    public Movie(String mPoster, String mTitle, String mOverview, String mDate, String mRating) {
        this.mPoster = mPoster;
        this.mTitle = mTitle;
        this.mOverview = mOverview;
        this.mDate = mDate;
        this.mRating = mRating;
    }

    public Movie(Parcel parcel) {
        this.mPoster = parcel.readString();
        this.mTitle = parcel.readString();
        this.mOverview = parcel.readString();
        this.mDate = parcel.readString();
        this.mRating = parcel.readString();
    }

    public Movie (){

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

    public String getmRating() {
        return mRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        /*
        * private String mPoster, mTitle, mOverview, mDate;
          private double mRating;
        */
        parcel.writeString(mPoster);
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
        parcel.writeString(mDate);
        parcel.writeString(mRating);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
