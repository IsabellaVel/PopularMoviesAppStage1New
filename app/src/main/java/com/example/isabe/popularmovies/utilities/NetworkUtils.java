package com.example.isabe.popularmovies.utilities;

import android.util.Log;

import com.example.isabe.popularmovies.BuildConfig;
import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.objects.Review;
import com.example.isabe.popularmovies.objects.Trailer;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by isabe on 2/17/2018.
 */

public final class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // private static final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    public static final String apiKey = BuildConfig.API_KEY;


    public final static String API_KEY_QUERY = "api_key";
    //final static String IMAGE_SIZE_PARAM = "size";

    private NetworkUtils() {

    }

    public static List<Movie> fetchMovieData(String requestUrl) throws JSONException {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Movie> movieList = MovieDbJSONUtils.getMovieDetailsFromJson(jsonResponse);
        return movieList;
    }

    public static List<Review> fetchMovieReview(String requestUrl) throws JSONException {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Review> movieReviewsList = null;
        try {
            movieReviewsList = MovieDbJSONUtils.getReviewFromJSON(jsonResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movieReviewsList;
    }

    public static List<Trailer> fetchMovieTrailer(String requestUrl) throws JSONException {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Trailer> movieTrailerList = null;
        try {
            movieTrailerList = MovieDbJSONUtils.getTrailerFromJSON(jsonResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movieTrailerList;
    }

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error with creating URL., e");
        }
        Log.v(LOG_TAG, "Built URI " + url);
        return url;
    }

    /**
     * public static URL buildImageUrl(String imageQuery) {
     * Uri movieUri = Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
     * .appendQueryParameter(IMAGE_SIZE_PARAM, image_size)
     * //.appendQueryParameter(IMAGE_FILE_PATH_PARAM, file_path_image)
     * .build();
     * <p>
     * URL urlImage = null;
     * try {
     * urlImage = new URL(movieUri.toString());
     * } catch (MalformedURLException e) {
     * e.printStackTrace();
     * }
     * Log.v(LOG_TAG, "Built URI " + urlImage);
     * return urlImage;
     * }
     **/

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            urlConnection.getResponseCode();
        } catch (IOException e) {
            int responseCode = urlConnection.getResponseCode();
        }
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
