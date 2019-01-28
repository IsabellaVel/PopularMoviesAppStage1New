package com.example.isabe.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
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

import com.example.isabe.popularmovies.adapters.MovieAdapter;
import com.example.isabe.popularmovies.adapters.ReviewAdapter;
import com.example.isabe.popularmovies.adapters.TrailerAdapter;
import com.example.isabe.popularmovies.data.MovieContract.MovieEntry;
import com.example.isabe.popularmovies.loaders.ReviewLoader;
import com.example.isabe.popularmovies.loaders.TrailerLoader;
import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.objects.MovieList;
import com.example.isabe.popularmovies.objects.Review;
import com.example.isabe.popularmovies.objects.Reviews;
import com.example.isabe.popularmovies.objects.Trailer;
import com.example.isabe.popularmovies.objects.Trailers;
import com.example.isabe.popularmovies.utilities.MovieAPI;
import com.example.isabe.popularmovies.utilities.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.isabe.popularmovies.MainActivityFragment.BASE_URL;
import static com.example.isabe.popularmovies.utilities.NetworkUtils.API_KEY_QUERY;
import static com.example.isabe.popularmovies.utilities.NetworkUtils.apiKey;

/**
 * Created by isabe on 2/23/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DetailsActivity extends AppCompatActivity implements TrailerAdapter.OnItemClicked {
    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();
    private static final int LOADER_ID = 1;
    private static final int LOADER_VIDEO_ID = 2;
    private static Movie mMovieDetails;
    private int movieIdFromTMDB;
    private int booleanToInt;
    private Boolean isFavorite;
    private int mPosition;


    private List<Review> movieReviews = new ArrayList<Review>();
    private List<Trailer> movieTrailers = new ArrayList<>();
    private Trailers movieTrailersPOJO = new Trailers();
    private Reviews movieReviewsPOJO = new Reviews();
    private RecyclerView mRecyclerReviews;
    private RecyclerView mRecyclerTrailers;
    private TrailerAdapter mTrailerAdapter;
    private RecyclerView.Adapter mReviewAdapter;

    private static final String DEFAULT_REVIEW_MD_LINK = "http://api.themoviedb.org/3/movie/";
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
                        setupRecyclerView(mRecyclerReviews);
                        Log.e(LOG_TAG, "Successful Review LoadFinished.");
                    }
                }

                @Override
                public void onLoaderReset(Loader<List<Review>> loader) {
                    movieReviews.clear();
                }
            };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_movie);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState != null) {
            mMovieDetails = (Movie) savedInstanceState.getParcelable("MOVIE_DETAILS");
        }
        mMovieDetails = getIntent().getExtras().getParcelable("MOVIE_DETAILS");

        ImageView mMoviePoster = findViewById(R.id.detail_backdrop_image);
        TextView mOriginalTitle = findViewById(R.id.tv_movie_title);
        TextView mReleaseDate = findViewById(R.id.tv_releaseDate);
        TextView mSynopsis = findViewById(R.id.tv_movie_synopsis);
        TextView mVoteAverage = findViewById(R.id.tv_vote_average);

        mOriginalTitle.setText(mMovieDetails.getmOriginalTitle());
        mReleaseDate.setText(convertDateFormat(mMovieDetails.getmReleaseDate()));
        Log.i(LOG_TAG, getString(R.string.date_log));
        mSynopsis.setText(mMovieDetails.getmOverviewMovie());
        String voteFormat = mMovieDetails.getmVoteAverage().concat("/10");
        mVoteAverage.setText(voteFormat);
        String fullImageBackdropPath = "https://image.tmdb.org/t/p/w185" + mMovieDetails.getmBackdropPath();
        Picasso.get().load(fullImageBackdropPath).into(mMoviePoster);
        movieIdFromTMDB = mMovieDetails.getmMovieTMDBId();
        //}

        final FloatingActionButton fab = findViewById(R.id.fab);
        isFavorite = true;
        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                booleanToInt = 0;
                if (isFavorite) booleanToInt |= 0x1;

                switch (booleanToInt) {
                    case 0:
                        fab.setImageResource(R.drawable.if_heart_1055045);
                        insertData();
                        Log.i(LOG_TAG, "Added to favs.");
                        isFavorite = true;
                        break;
                    case 1:
                        fab.setImageResource(R.drawable.favorite_heart_button);
                        deleteItem();
                        Log.i(LOG_TAG, "Deleted from favs.");
                        break;
                }
            }
            //trackFavorites();
        });
        provideReview();
        startTrailer();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("MOVIE_DETAILS", mMovieDetails);
        savedInstanceState.putInt("Scroll position", mPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(LOG_TAG, "Inside on RestoreInstanceState");
        mPosition = (int) savedInstanceState.getInt("Scroll position");
        mMovieDetails = (Movie) savedInstanceState.getParcelable("MOVIE_DETAILS");
    }

    private void trackFavorites() {
        final FloatingActionButton fab = findViewById(R.id.fab);
        isFavorite = false;

        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (isFavorite(movieIdFromTMDB)) {
                    booleanToInt = 1;
                    fab.setImageResource(R.drawable.if_heart_1055045);
                } else {
                    booleanToInt = 0;
                    fab.setImageResource(R.drawable.favorite_heart_button);
                }

                if (isFavorite) booleanToInt |= 0x1;

                switch (booleanToInt) {
                    case 0:
                        fab.setImageResource(R.drawable.if_heart_1055045);
                        insertData();
                        Log.i(LOG_TAG, "Added to favs.");
                        isFavorite = true;
                        break;
                    case 1:
                        fab.setImageResource(R.drawable.favorite_heart_button);
                        deleteItem();
                        Log.i(LOG_TAG, "Deleted from favs.");
                        break;
                }

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        trackFavorites();
    }
    private void insertData() {
        String dateFormatted = convertDateFormat(mMovieDetails.getmReleaseDate());

        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieEntry.DB_TITLE, mMovieDetails.getmOriginalTitle());
        movieValues.put(MovieEntry.DB_RELEASE_DATE, dateFormatted);
        movieValues.put(MovieEntry.DB_SYNOPSIS, mMovieDetails.getmOverviewMovie());
        movieValues.put(MovieEntry.DB_VOTE_AVERAGE, mMovieDetails.getmVoteAverage());
        movieValues.put(MovieEntry.DB_POSTER_PATH, mMovieDetails.getmImageThumbnail());
        movieValues.put(MovieEntry.DB_BACKDROP_PATH, mMovieDetails.getmBackdropPath());
        movieValues.put(MovieEntry.DB_MOVIE_ID, movieIdFromTMDB);

        getContentResolver()
                .insert(MovieEntry.CONTENT_URI, movieValues);
        Toast.makeText(getApplicationContext(), "Added " + movieValues.size() + "items.", Toast.LENGTH_LONG).show();

    }


    private void deleteItem() {
        int numDeleted = getContentResolver()
                .delete(MovieEntry.CONTENT_URI,
                        (MovieEntry.DB_MOVIE_ID + "=" + movieIdFromTMDB), null);
        Toast.makeText(this, "Entry deleted: " + numDeleted + " items.", Toast.LENGTH_LONG).show();

    }


    private void showReview() {

        android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, mListReviewsLoader);

        movieReviews = new ArrayList<>();
        setupRecyclerView(mRecyclerReviews);
    }

    private void setupRecyclerTrailersView(RecyclerView recyclerView) {
        TrailerAdapter mTrailerAdapter = new TrailerAdapter(this, movieTrailers);
        mRecyclerTrailers = findViewById(R.id.trailer_movie);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerTrailers.setLayoutManager(linearLayoutManager);

        mRecyclerTrailers.setAdapter(mTrailerAdapter);
        mTrailerAdapter.setOnClick(this);
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
            dateString = dateString.substring(0, dateString.length());
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

    private boolean isFavorite(int movieSelected) {
        Cursor savedMovie = getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                MovieEntry.DB_MOVIE_ID + "=" + movieSelected,
                null,
                null);
        assert savedMovie != null;
        if (savedMovie.moveToNext()) {
            savedMovie.close();
            return true;
        } else {
            savedMovie.close();
            return false;
        }
    }

    @Override
    public void onItemClick(int position) {
    mPosition = position;
    }

    public void startTrailer(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<Trailers> callTrailer = movieAPI.callTrailer(movieIdFromTMDB, apiKey);
        callTrailer.enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                Log.i(LOG_TAG, "Movie trailer is started " + BASE_URL + movieIdFromTMDB + "/videos?api_key=" + apiKey);
                movieTrailersPOJO = response.body();
                assert movieTrailersPOJO != null;
                movieTrailers = movieTrailersPOJO.getTrailers();
                Log.i(LOG_TAG, "Movie trailer image is " +
                        movieTrailers.get(0).getmKeySearchImage());

                TrailerAdapter mTrailerAdapter = new TrailerAdapter(DetailsActivity.this, movieTrailers);
                mRecyclerTrailers = findViewById(R.id.trailer_movie);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerTrailers.setLayoutManager(linearLayoutManager);

                mRecyclerTrailers.setAdapter(mTrailerAdapter);
                mTrailerAdapter.setOnClick(DetailsActivity.this);
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void provideReview(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<Reviews> callReview = movieAPI.callReview(movieIdFromTMDB, apiKey);
        callReview.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                Log.i(LOG_TAG, "Movie review is presented " + BASE_URL + movieIdFromTMDB + "/reviews?api_key=" + apiKey);
                movieReviewsPOJO = response.body();
                assert movieReviewsPOJO != null;
                movieReviews = movieReviewsPOJO.getReviewList();

                if(!movieReviews.isEmpty()) {
                    Log.i(LOG_TAG, "Movie review url is " +
                            movieReviews.get(0).getmReviewUrl());
                }else{
                    Log.i(LOG_TAG, "No movie review url.");
                }
                ReviewAdapter  mReviewAdapter = new ReviewAdapter(DetailsActivity.this, movieReviews);
                mRecyclerReviews = findViewById(R.id.review_content);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerReviews.setLayoutManager(linearLayoutManager);

                mRecyclerReviews.setAdapter(mReviewAdapter);
              }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
