package com.example.isabe.popularmovies.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.isabe.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isabe on 2/17/2018.
 */

class MovieDbJSONUtils {
    private static final String MD_LIST = "results";
    private static final String MD_ORIGINAL_TITLE = "original_title";
    private static final String MD_POSTER_IMAGE_THUMBNAIL = "poster_path";
    private static final String MD_OVERVIEW = "overview";
    private static final String MD_VOTE_AVERAGE = "vote_average";
    private static final String MD_RELEASE_DATE = "release_date";
    private static final String MD_MOVIE_ID = "id";
    private static final String MD_TRAILER = "video";
    private static final String MD_BACKDROP_PATH = "backdrop_path";

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
                String voteAverage = currentMovie.getString(MD_VOTE_AVERAGE);
                String movieOverview = currentMovie.optString(MD_OVERVIEW);
                String movieReleasedOn = currentMovie.optString(MD_RELEASE_DATE);
                String moviePosterImageThumbnail = currentMovie.getString(MD_POSTER_IMAGE_THUMBNAIL);
                String movieBackdropPath = currentMovie.getString(MD_BACKDROP_PATH);
                int movieTMDBId = currentMovie.getInt(MD_MOVIE_ID);
                Boolean movieTrailer = currentMovie.getBoolean(MD_TRAILER);

                Movie movieItem = new Movie(originalTitle,
                        movieReleasedOn, movieOverview, moviePosterImageThumbnail, voteAverage,
                        movieBackdropPath, movieTrailer, movieTMDBId);

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
