package com.example.isabe.popularmovies.loaders;

import android.support.v4.content.AsyncTaskLoader;

import android.content.Context;

import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.util.List;

/**
 * Created by isabe on 2/19/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    private final String mUrl;

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
