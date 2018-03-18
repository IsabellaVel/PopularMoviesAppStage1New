package com.example.isabe.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by isabe on 3/17/2018.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = MovieDbHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABSE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieContract.MovieEntry.TABLE_MOVIES + "(" + MovieContract.MovieEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.DB_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.DB_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.DB_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.DB_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.DB_SYNOPSIS + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.DB_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.DB_VOTE_ABVERAGE + " TEXT NOT NULL, " +
                " UNIQUE (" + MovieContract.MovieEntry.DB_MOVIE_ID + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    // Upgrade database when version is changed.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgarding database from version " + oldVersion + " to " + newVersion +
                ". OLD DATA WILL BE DESTROYED");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_MOVIES);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                MovieContract.MovieEntry.TABLE_MOVIES + "'");
        onCreate(sqLiteDatabase);
    }
}
