package com.example.isabe.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ProxyInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by isabe on 3/17/2018.
 */

public class MoviesProvider extends ContentProvider {
    private static final String LOG_TAG = MoviesProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenMoviesHelper;

    // Codes for the UriMatcher //////
    private static final int MOVIE_MATCHER = 100;
    private static final int MOVIE_WITH_ID = 200;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.MovieEntry.TABLE_MOVIES, MOVIE_MATCHER);
        matcher.addURI(authority, MovieContract.MovieEntry.TABLE_MOVIES + "/#", MOVIE_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenMoviesHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_MATCHER: {
                retCursor = mOpenMoviesHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case MOVIE_WITH_ID: {
                retCursor = mOpenMoviesHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        MovieContract.MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE_MATCHER: {
                return MovieContract.MovieEntry.CONTENT_MOVIES_DIR_TYPE;
            }
            case MOVIE_WITH_ID: {
                return MovieContract.MovieEntry.CONTENT_MOVIE_ITEM_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mOpenMoviesHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIE_MATCHER: {
                long _id = db.insert(MovieContract.MovieEntry.TABLE_MOVIES, null, contentValues);
                if (_id > 0) {
                    returnUri = MovieContract.MovieEntry.buildMoviesUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenMoviesHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch (match) {
            case MOVIE_MATCHER:
                numDeleted = db.delete(
                        MovieContract.MovieEntry.TABLE_MOVIES, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        MovieContract.MovieEntry.TABLE_MOVIES + "'");
                break;
            case MOVIE_WITH_ID:
                numDeleted = db.delete(MovieContract.MovieEntry.TABLE_MOVIES,
                        MovieContract.MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                //reset _ID
                db.execSQL("DELETE FROM SQL_SEQUENCE WHERE NAME = '" +
                        MovieContract.MovieEntry.TABLE_MOVIES + "'");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return numDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mOpenMoviesHelper.getWritableDatabase();
        int numUpdated = 0;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values.");
        }
        switch (sUriMatcher.match(uri)) {
            case MOVIE_MATCHER: {
                numUpdated = db.update(MovieContract.MovieEntry.TABLE_MOVIES,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case MOVIE_WITH_ID: {
                numUpdated = db.update(MovieContract.MovieEntry.TABLE_MOVIES,
                        contentValues,
                        MovieContract.MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numUpdated;
    }
}
