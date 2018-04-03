package com.example.isabe.popularmovies.loaders;

import android.content.Context;

import java.util.List;

import android.support.v4.content.AsyncTaskLoader;

import com.example.isabe.popularmovies.objects.Review;
import com.example.isabe.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

/**
 * Created by isabe on 3/25/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    private final String mUrl;

    public ReviewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Review> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Review> reviewList = null;

        try {
            reviewList = NetworkUtils.fetchMovieReview(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewList;
    }
}
