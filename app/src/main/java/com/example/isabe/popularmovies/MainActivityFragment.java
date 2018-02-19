package com.example.isabe.popularmovies;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.isabe.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private MovieAdapter mMovieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(mMovieAdapter);

        return rootView;
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        URL movieUrl = NetworkUtils.buildUrl(NetworkUtils.apiKey);
        return new MovieLoader(getActivity(), (movieUrl).toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movieList) {
        mMovieAdapter.clear();
        if (movieList != null && !movieList.isEmpty()) {
            mMovieAdapter.addAll(movieList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mMovieAdapter.clear();
    }
}
