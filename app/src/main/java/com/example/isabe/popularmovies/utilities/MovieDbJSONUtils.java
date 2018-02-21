package com.example.isabe.popularmovies.utilities;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SyncStats;
import android.text.TextUtils;
import android.util.Log;

import com.example.isabe.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * Created by isabe on 2/17/2018.
 */

public class MovieDbJSONUtils {
    private static final String MD_LIST = "results";
    private static final String MD_ORIGINAL_TITLE = "original_title";
    private static final String MD_POSTER_IMAGE_THUMBNAIL = "poster_path";
    private static final String MD_OVERVIEW = "overview";
    private static final String MD_VOTE_AVERAGE = "vote_average";
    private static final String MD_RELEASE_DATE = "release_date";

    public static List<Movie> getMovieDetailsFromJson(String movieJsonString)
            throws JSONException {
        if (TextUtils.isEmpty(movieJsonString)) {
            return null;
        }

        List<Movie> moviesList = new ArrayList<Movie>();

        try {
            JSONObject baseMoviesJson = new JSONObject(movieJsonString);
            JSONArray moviesJsonArray = baseMoviesJson.getJSONArray(MD_LIST);

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject currentMovie = moviesJsonArray.getJSONObject(i);
                String originalTitle = currentMovie.optString(MD_ORIGINAL_TITLE);
                Double voteAverage = currentMovie.getDouble(MD_VOTE_AVERAGE);
                String movieOverview = currentMovie.optString(MD_OVERVIEW);
                String movieReleasedOn = currentMovie.optString(MD_RELEASE_DATE);
                String moviePosterImageThumbnail = currentMovie.getString(MD_POSTER_IMAGE_THUMBNAIL);

                Movie movieItem = new Movie(originalTitle,
                        movieReleasedOn, movieOverview, moviePosterImageThumbnail, voteAverage);

                moviesList.add(movieItem);

                if (baseMoviesJson.has(MD_ORIGINAL_TITLE)) {
                    int errorCode = baseMoviesJson.getInt(MD_ORIGINAL_TITLE);

                    switch (errorCode) {
                        case HttpURLConnection.HTTP_OK:
                            break;
                        case HttpURLConnection.HTTP_NOT_FOUND:
                            return null;
                        default:
                            return null;
                    }

                }
            }
        } catch (JSONException e) {
            Log.e("MoviesUtils", "Problem parsing movies JSON results", e);
        }

        return moviesList;


    }
}
