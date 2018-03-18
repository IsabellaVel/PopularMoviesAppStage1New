package com.example.isabe.popularmovies;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.graphics.*;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isabe.popularmovies.data.MovieContract;
import com.example.isabe.popularmovies.utilities.RetainedFragment;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_movie);

        mMovieDetails = getIntent().getExtras().getParcelable("DETAILS_MOVIE");
        ImageView mMoviePoster = findViewById(R.id.detail_image);
        TextView mOriginalTitle = findViewById(R.id.tv_movie_title);
        TextView mReleaseDate = findViewById(R.id.tv_releaseDate);
        TextView mSynopsis = findViewById(R.id.tv_movie_synopsis);
        TextView mVoteAverage = findViewById(R.id.tv_vote_average);

        Bundle bundleGetExtras = getIntent().getExtras();
        if (bundleGetExtras != null) {
            String originalTitle = bundleGetExtras.getString(getString(R.string.original_title));
            String releaseDate = bundleGetExtras.getString(getString(R.string.string_date_release));
            String imagePath = bundleGetExtras.getString(getString(R.string.image_path_string));
            String movieSynopsis = bundleGetExtras.getString(getString(R.string.movie_summary));
            String voteAverage = bundleGetExtras.getString(getString(R.string.vote_string));
            voteAverage = voteAverage + " " + getString(R.string.votePercent);
            String fullImagePosterLink = "https://image.tmdb.org/t/p/w185" + imagePath;

            Picasso.with(this).load(fullImagePosterLink).into(mMoviePoster);
            mOriginalTitle.setText(originalTitle);
            mReleaseDate.setText(convertDateFormat(releaseDate));
            Log.i(LOG_TAG, getString(R.string.date_log));
            mSynopsis.setText(movieSynopsis);
            mVoteAverage.setText(voteAverage);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }

            public void insertData() {
                ContentValues movieValues = new ContentValues();
                movieValues.put(MovieContract.MovieEntry.DB_MOVIE_ID, mMovieDetails.getmMovieId());
                movieValues.put(MovieContract.MovieEntry.DB_TITLE, mMovieDetails.getmOriginalTitle());
                movieValues.put(MovieContract.MovieEntry.DB_BACKDROP_PATH, mMovieDetails.getmBackdropPath());
                movieValues.put(MovieContract.MovieEntry.DB_POSTER_PATH, mMovieDetails.getmImageThumbnail());
                movieValues.put(MovieContract.MovieEntry.DB_RELEASE_DATE, mMovieDetails.getmReleaseDate());
                movieValues.put(MovieContract.MovieEntry.DB_VOTE_ABVERAGE, mMovieDetails.getmVoteAverage());
            }
        });
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
