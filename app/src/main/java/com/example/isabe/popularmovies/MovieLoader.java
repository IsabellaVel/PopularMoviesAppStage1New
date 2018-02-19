package com.example.isabe.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.isabe.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.util.List;

/**
 * Created by isabe on 2/19/2018.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    private static final String LOG_TAG = MovieLoader.class.getSimpleName();
    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Movie> movieList = null;
        try {
            movieList = NetworkUtils.fetchMovieData(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;

    }
}
