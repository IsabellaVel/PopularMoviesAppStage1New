package com.example.isabe.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.isabe.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by isabe on 2/17/2018.
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String DYNAMIC_MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/popular?api_key=";

    private static final String MOVIE_BASE_URL = DYNAMIC_MOVIE_DB_URL;
    private static final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final int numMovies = 15;
    private static final String apiKey = BuildConfig.API_KEY;
    private static final String file_path_image = "string.jpg";
    private static final String image_size = "w185";


    final static String API_KEY_QUERY = "key";
    final static String MOVIES_PARAM = "cnt";
    final static String IMAGE_FILE_PATH_PARAM = "image";
    final static String IMAGE_SIZE_PARAM = "size";


    public static URL buildUrl(String searchQuery) {
        Uri movieUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_QUERY, apiKey)
                .appendQueryParameter(MOVIES_PARAM, Integer.toString(numMovies))
                .build();

        URL url = null;
        try {
            url = new URL(movieUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static URL buildImageUrl(String imageQuery) {
        Uri movieUri = Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                .appendQueryParameter(IMAGE_SIZE_PARAM, image_size)
                //.appendQueryParameter(IMAGE_FILE_PATH_PARAM, file_path_image)
                .build();

        URL urlImage = null;
        try {
            urlImage = new URL(movieUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + urlImage);
        return urlImage;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
