package com.example.standard.popularmoviesstageone;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by vince on 05.06.2017.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mUrl;

    public MovieLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        List<Movie> movies = Utils.fetchMovieData(getContext(), mUrl);
        return movies;
    }
}
