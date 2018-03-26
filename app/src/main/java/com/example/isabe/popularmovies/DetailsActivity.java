package com.example.isabe.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isabe.popularmovies.adapters.ReviewAdapter;
import com.example.isabe.popularmovies.adapters.TrailerAdapter;
import com.example.isabe.popularmovies.data.MovieContract.MovieEntry;
import com.example.isabe.popularmovies.data.MovieDbHelper;
import com.example.isabe.popularmovies.loaders.ReviewLoader;
import com.example.isabe.popularmovies.loaders.TrailerLoader;
import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.objects.Review;
import com.example.isabe.popularmovies.objects.Trailer;
import com.example.isabe.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.isabe.popularmovies.utilities.NetworkUtils.API_KEY_QUERY;
import static com.example.isabe.popularmovies.utilities.NetworkUtils.apiKey;

/**
 * Created by isabe on 2/23/2018.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();
    private static final int LOADER_ID = 1;
    private static final int LOADER_VIDEO_ID = 2;
    private static Movie mMovieDetails;
    int movieIdFromTMDB;
    String originalTitle;
    String releaseDate;
    String imagePath;
    String voteAverage;
    String backdropImage;
    String movieSynopsis;
    public Uri mNewMovieAddedToDB;
    private List<Review> movieReviews = new ArrayList<Review>();
    private List<Trailer> movieTrailers = new ArrayList<>();
    private RecyclerView mRecyclerReviews;
    private RecyclerView mRecyclerTrailers;

    private MovieDbHelper movieDbHelper = new MovieDbHelper(this);
    SQLiteDatabase db;
    Cursor mCursor;
    TextView displayView;
    private RecyclerView.Adapter mReviewAdapter;

    public static final String DEFAULT_REVIEW_MD_LINK = "http://api.themoviedb.org/3/movie/";
    public static final String YOUTUBE_LINK_VIDEO = "https://www.youtube.com/watch?v=";


    private android.support.v4.app.LoaderManager.LoaderCallbacks mListReviewsLoader =
            new LoaderManager.LoaderCallbacks<List<Review>>() {

                @Override
                public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
                    Uri movieReviewUri = Uri.parse(DEFAULT_REVIEW_MD_LINK).buildUpon()
                            .appendPath(String.valueOf(movieIdFromTMDB))
                            .appendPath("reviews")
                            .appendQueryParameter(API_KEY_QUERY, apiKey)
                            .build();
                    URL movieReviewUrl = NetworkUtils.createUrl((movieReviewUri).toString());
                    return new ReviewLoader(DetailsActivity.this, (movieReviewUrl).toString());
                }

                @Override
                public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviewData) {
                    if (reviewData != null && !reviewData.isEmpty()) {
                        movieReviews.addAll(reviewData);
                        Log.e(LOG_TAG, "Successful Review LoadFinished.");
                    }
                }

                @Override
                public void onLoaderReset(Loader<List<Review>> loader) {
                    movieReviews.clear();
                }
            };

    private android.support.v4.app.LoaderManager.LoaderCallbacks mListMovieTrailers =
            new LoaderManager.LoaderCallbacks<List<Trailer>>() {

                @Override
                public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
                    Uri movieTrailerUri = Uri.parse(DEFAULT_REVIEW_MD_LINK).buildUpon()
                            .appendPath(String.valueOf(movieIdFromTMDB))
                            .appendPath("videos")
                            .appendQueryParameter(API_KEY_QUERY, apiKey)
                            .build();
                    URL movieTrailerUrl = NetworkUtils.createUrl((movieTrailerUri).toString());

                    return new TrailerLoader(DetailsActivity.this, (movieTrailerUrl).toString());
                }

                @Override
                public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> trailerList) {
                    if (trailerList != null && !trailerList.isEmpty()) {
                        movieTrailers.addAll(trailerList);
                        Log.e(LOG_TAG, "Successful TrailerLoadFinished.");
                    }
                }

                @Override
                public void onLoaderReset(Loader<List<Trailer>> loader) {
                    movieTrailers.clear();
                }
            };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_movie);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
            }
        });

        showReview();
        showTrailers();
    }

  /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerReviews = (RecyclerView) inflater.inflate(
                R.layout.recycler_view_reviews_list, container, false);
        setupRecyclerView(mRecyclerReviews);
        return mRecyclerReviews;
    }
    */


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

    private void showReview() {

        android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, mListReviewsLoader);

        movieReviews = new ArrayList<>();
        setupRecyclerView(mRecyclerReviews);
    }

    private void showTrailers() {
        android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_VIDEO_ID, null, mListMovieTrailers);

        movieTrailers = new ArrayList<>();
        setupRecyclerTrailersView(mRecyclerTrailers);
    }

    private void setupRecyclerTrailersView(RecyclerView recyclerView) {
        TrailerAdapter mTrailerAdapter = new TrailerAdapter(this, movieTrailers);
        mRecyclerTrailers = findViewById(R.id.trailer_movie);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerTrailers.setLayoutManager(linearLayoutManager);

        mRecyclerTrailers.setAdapter(mTrailerAdapter);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ReviewAdapter mReviewAdapter = new ReviewAdapter(this, movieReviews);
        mRecyclerReviews = findViewById(R.id.review_content);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerReviews.setLayoutManager(linearLayoutManager);

        mRecyclerReviews.setAdapter(mReviewAdapter);
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
