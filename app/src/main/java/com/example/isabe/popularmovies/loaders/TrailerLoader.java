package com.example.isabe.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.isabe.popularmovies.objects.Trailer;
import com.example.isabe.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.util.List;

/**
 * Created by isabe on 3/26/2018.
 */

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>> {
    private static final String LOG_TAG = TrailerLoader.class.getSimpleName();
    private final String mUrl;

    public TrailerLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Trailer> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Trailer> trailerList = null;
        try {
            trailerList = NetworkUtils.fetchMovieTrailer(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailerList;
    }
}
