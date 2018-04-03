package com.example.isabe.popularmovies.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.objects.Review;
import com.example.isabe.popularmovies.objects.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by isabe on 2/17/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
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
    private static final String MD_AUTHOR_REVIEW = "author";
    private static final String MD_CONTENT_REVIEW = "content";
    private static final String MD_URL_REVIEW = "url";
    private static final String MD_SITE_VIDEO = "site";
    private static final String MD_SIZE_VIDEO = "size";
    private static final String MD_NAME_VIDEO = "name";
    private static final String MD_VIDEO_SEARCH_KEY = "key";

    private static final String LOG_TAG = MovieDbJSONUtils.class.getSimpleName();

    public static List<Movie> getMovieDetailsFromJson(String movieJsonString) {
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
                        movieBackdropPath, movieTMDBId);

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

    public static List<Review> getReviewFromJSON(String movieJSONString) {

        if (TextUtils.isEmpty(movieJSONString)) {
            return null;
        }

        List<Review> reviewsMovies = new ArrayList<>();
        try {
            JSONObject baseJSONResponse = new JSONObject(movieJSONString);
            JSONArray movieReviewArray = baseJSONResponse.getJSONArray(MD_LIST);

            for (int iR = 0; iR < movieReviewArray.length(); iR++) {
                JSONObject currentMovieReview = movieReviewArray.getJSONObject(iR);

                String reviewContent = currentMovieReview.getString(MD_CONTENT_REVIEW);
                String reviewAuthor = currentMovieReview.getString(MD_AUTHOR_REVIEW);
                String reviewUrl = currentMovieReview.getString(MD_URL_REVIEW);

                Review movieReview = new Review(reviewAuthor, reviewContent, reviewUrl);
                reviewsMovies.add(movieReview);
            }
            Log.d(LOG_TAG, "Reviews delivered.");
        } catch (
                JSONException e)

        {
            Log.e(LOG_TAG, "Problem parsing reviews.", e);
            e.printStackTrace();
        }
        return reviewsMovies;

    }

    public static List<Trailer> getTrailerFromJSON(String movieJSONString) {

        if (TextUtils.isEmpty(movieJSONString)) {
            return null;
        }
        List<Trailer> trailerMovies = new ArrayList<>();

        try {
            JSONObject baseJSONResponse = new JSONObject(movieJSONString);
            JSONArray movieTrailerArray = baseJSONResponse.getJSONArray(MD_LIST);

            for (int iT = 0; iT < movieTrailerArray.length();
                 iT++) {
                JSONObject currentMovieTrailer = movieTrailerArray.getJSONObject(iT);

                String trailerName = currentMovieTrailer.getString(MD_NAME_VIDEO);
                String trailerSize = currentMovieTrailer.getString(MD_SIZE_VIDEO);
                String trailerSite = currentMovieTrailer.getString(MD_SITE_VIDEO);
                String trailerKey = currentMovieTrailer.getString(MD_VIDEO_SEARCH_KEY);

                Trailer movieTrailer = new Trailer(trailerName, trailerSize, trailerSite, trailerKey);
                trailerMovies.add(movieTrailer);
            }
            Log.d(LOG_TAG, "Trailers delivered.");
        } catch (
                JSONException e)

        {
            Log.e(LOG_TAG, "Problem parsing trailers.", e);
            e.printStackTrace();
        }
        return trailerMovies;

    }
}
