package com.example.isabe.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isabe.popularmovies.data.MovieContract;
import com.example.isabe.popularmovies.data.MovieContract.MovieEntry;
import com.example.isabe.popularmovies.data.MovieDbHelper;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by isabe on 2/23/2018.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();
    static Movie mMovieDetails;
    int movieIdFromTMDB;
    String originalTitle;
    String releaseDate;
    String imagePath;
    String voteAverage;
    String backdropImage;
    String movieSynopsis;
    public Uri mNewMovieAddedToDB;

    private MovieDbHelper movieDbHelper;
    SQLiteDatabase db;
    Cursor mCursor;
    TextView displayView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_movie);

        mMovieDetails = getIntent().getExtras().getParcelable("MOVIE_DETAILS");
        ImageView mMoviePoster = findViewById(R.id.detail_image);
        TextView mOriginalTitle = findViewById(R.id.tv_movie_title);
        TextView mReleaseDate = findViewById(R.id.tv_releaseDate);
        TextView mSynopsis = findViewById(R.id.tv_movie_synopsis);
        TextView mVoteAverage = findViewById(R.id.tv_vote_average);

        Bundle bundleGetExtras = getIntent().getExtras();
        if (bundleGetExtras != null) {
            originalTitle = bundleGetExtras.getString(getString(R.string.original_title));
            releaseDate = bundleGetExtras.getString(getString(R.string.string_date_release));
            imagePath = bundleGetExtras.getString(getString(R.string.image_path_string));
            movieSynopsis = bundleGetExtras.getString(getString(R.string.movie_summary));
            voteAverage = bundleGetExtras.getString(getString(R.string.vote_string));
            voteAverage = voteAverage + " " + getString(R.string.votePercent);
            movieIdFromTMDB = bundleGetExtras.getInt(getString(R.string.movie_string_id));
            String fullImagePosterLink = "https://image.tmdb.org/t/p/w185" + imagePath;
            backdropImage = bundleGetExtras.getString(getString(R.string.backdrop_string_path));
            String fullImageBackdropPath = "https://image.tmdb.org/t/p/w185" + backdropImage;

            Picasso.with(this).load(fullImageBackdropPath).into(mMoviePoster);
            mOriginalTitle.setText(originalTitle);
            mReleaseDate.setText(convertDateFormat(releaseDate));
            Log.i(LOG_TAG, getString(R.string.date_log));
            mSynopsis.setText(movieSynopsis);
            mVoteAverage.setText(voteAverage);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                readDataCheckMethod();
            }
        });
    }

    public void insertData() {

        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieEntry.DB_MOVIE_ID, movieIdFromTMDB);
        movieValues.put(MovieEntry.DB_TITLE, originalTitle);
        movieValues.put(MovieEntry.DB_POSTER_PATH, imagePath);
        movieValues.put(MovieEntry.DB_BACKDROP_PATH, backdropImage);
        movieValues.put(MovieEntry.DB_SYNOPSIS, movieSynopsis);
        movieValues.put(MovieEntry.DB_RELEASE_DATE, releaseDate);
        movieValues.put(MovieEntry.DB_VOTE_AVERAGE, voteAverage);

        getContentResolver()
                .insert(MovieEntry.CONTENT_URI, movieValues);
        Toast.makeText(getApplicationContext(), "Added " + movieValues.size() + "items.", Toast.LENGTH_LONG).show();

    }

    private Cursor readDataCheckMethod() {
        db = movieDbHelper.getReadableDatabase();
        String[] FAVORITES_PROJECTION = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.DB_MOVIE_ID,
                MovieContract.MovieEntry.DB_TITLE,
                MovieContract.MovieEntry.DB_BACKDROP_PATH,
                MovieContract.MovieEntry.DB_SYNOPSIS,
                MovieContract.MovieEntry.DB_RELEASE_DATE,
                MovieContract.MovieEntry.DB_VOTE_AVERAGE
        };
        mCursor = db.query(MovieEntry.TABLE_MOVIES, FAVORITES_PROJECTION,
                null, null, null, null, null);

        displayView.setText("The movies DB contains " + mCursor.getCount() + " items.\n\n");
        return mCursor;
    }

    private String convertDateFormat(String dateString) {
        if (dateString != null && !dateString.isEmpty()) {
            dateString = dateString.substring(0, dateString.length() - 1);
            String originalFormat = "yyyy-mm-dd";
            String newFormat = "dd-mm-yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(originalFormat);
            SimpleDateFormat outputFormat = new SimpleDateFormat(newFormat);

            Date date;
            String dateStringFinal = "dd-mm-yyyy";

            try {
                date = inputFormat.parse(dateString);
                dateStringFinal = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateStringFinal;
        }
        return null;
    }
}
