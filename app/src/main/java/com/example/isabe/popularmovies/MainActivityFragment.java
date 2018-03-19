package com.example.isabe.popularmovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.isabe.popularmovies.data.MovieContract;
import com.example.isabe.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.isabe.popularmovies.utilities.NetworkUtils.API_KEY_QUERY;
import static com.example.isabe.popularmovies.utilities.NetworkUtils.apiKey;

public class MainActivityFragment extends Fragment {

    public static final int LOADER_ID = 11;
    public static final int LOADER_CURSOR_ID = 14;

    public static final String DEFAULT_POPULAR_MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/popular?";
    public static final String MOVIE_DB_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated?";
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    public static String movieDisplayStyleLink = DEFAULT_POPULAR_MOVIE_DB_URL;
    private MovieAdapter mMovieAdapter;
    private List<Movie> movieList = new ArrayList<>();

    public static final String[] FAVORITES_PROJECTION = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.DB_MOVIE_ID,
            MovieContract.MovieEntry.DB_TITLE,
            MovieContract.MovieEntry.DB_POSTER_PATH,
            MovieContract.MovieEntry.DB_BACKDROP_PATH,
            MovieContract.MovieEntry.DB_SYNOPSIS,
            MovieContract.MovieEntry.DB_RELEASE_DATE,
            MovieContract.MovieEntry.DB_VOTE_ABVERAGE
    };

    private android.support.v4.app.LoaderManager.LoaderCallbacks mListMovieLoader =
            new LoaderManager.LoaderCallbacks<List<Movie>>() {

                @Override
                public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
                    Uri movieUri = Uri.parse(movieDisplayStyleLink).buildUpon()
                            .appendQueryParameter(API_KEY_QUERY, apiKey)
                            .build();
                    URL movieUrl = NetworkUtils.createUrl((movieUri).toString());
                    return new MovieLoader(getActivity(), (movieUrl).toString());
                }

                @Override
                public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movieList) {
                    mMovieAdapter.clear();
                    if (movieList != null && !movieList.isEmpty()) {
                        mMovieAdapter.addAll(movieList);
                        Log.e(LOG_TAG, "Successful LoadFinished.");
                    }
                }

                @Override
                public void onLoaderReset(Loader<List<Movie>> loader) {
                    mMovieAdapter.clear();
                }
            };

    private android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> mLoaderCursor =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                    return new CursorLoader(getActivity(),
                            MovieContract.MovieEntry.CONTENT_URI,
                            FAVORITES_PROJECTION,
                            null,
                            null,
                            null);
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    mMovieAdapter.swapCursor(cursor);
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    mMovieAdapter.swapCursor(null);
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList("movieData");
        }
    }

    public MainActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movieData", (ArrayList<? extends Parcelable>) movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        mMovieAdapter = new MovieAdapter(getActivity(), movieList);

        GridView gridView = rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int item, long l) {
                Movie thisMovie = mMovieAdapter.getItem(item);
                assert thisMovie != null;
                assert thisMovie != null;
                String originalTitle = thisMovie.getmOriginalTitle();
                String releaseDate = thisMovie.getmReleaseDate();
                String voteAverage = thisMovie.getmVoteAverage();
                String moviePoster = thisMovie.getmImageThumbnail();
                String movieBackdrop = thisMovie.getmBackdropPath();
                String movieSynopsis = thisMovie.getmOverviewMovie();
                int movieID = thisMovie.getmMovieTMDBId();

                Intent showDetailsIntent = new Intent(getActivity(), DetailsActivity.class);

                Bundle bundleExtra = new Bundle();
                bundleExtra.putString(getString(R.string.original_title), originalTitle);
                bundleExtra.putString(getString(R.string.image_path_string), moviePoster);
                bundleExtra.putString(getString(R.string.string_date_release), releaseDate);
                bundleExtra.putString(getString(R.string.vote_string), voteAverage);
                bundleExtra.putString(getString(R.string.movie_summary), movieSynopsis);
                bundleExtra.putInt(getString(R.string.movie_string_id), movieID);
                bundleExtra.putString(getString(R.string.backdrop_string_path), movieBackdrop);
                showDetailsIntent.putExtras(bundleExtra);
                startActivity(showDetailsIntent);

            }
        });
        android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, mListMovieLoader);
        return rootView;

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            /**case R.id.action_settings:
             Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
             startActivity(settingsIntent);
             return true;
             **/
            case R.id.top_rated:
                movieDisplayStyleLink = MOVIE_DB_URL_TOP_RATED;
                getLoaderManager().restartLoader(0, null, mListMovieLoader);
                Log.e(LOG_TAG, getString(R.string.log_top_rated_menu));
            case R.id.most_popular:
                movieDisplayStyleLink = DEFAULT_POPULAR_MOVIE_DB_URL;
                getLoaderManager().restartLoader(LOADER_ID, null, mListMovieLoader);
            case R.id.favorites:
                getLoaderManager().initLoader(LOADER_CURSOR_ID, null, mLoaderCursor);

        }
        return super.onOptionsItemSelected(item);
    }
}

