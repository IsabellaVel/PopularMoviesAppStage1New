package com.example.isabe.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by isabe on 3/17/2018.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.isabe.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_MOVIES = "movies";

        //columns
        public static final String _ID = "_id";
        public static final String DB_MOVIE_ID = "movie_id";
        public static final String DB_TITLE = "title";
        public static final String DB_POSTER_PATH = "poster_path";
        public static final String DB_BACKDROP_PATH = "backdrop_path";
        public static final String DB_SYNOPSIS = "overview";
        public static final String DB_RELEASE_DATE = "release_date";
        public static final String DB_VOTE_AVERAGE = "vote_average";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(PATH_MOVIES).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_MOVIES_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;
        // create cursor of base type item for single entry
        public static final String CONTENT_MOVIE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        // for building URIs on insertion
        public static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
