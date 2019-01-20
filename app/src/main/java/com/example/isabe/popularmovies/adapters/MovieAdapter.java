package com.example.isabe.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.isabe.popularmovies.R;
import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.objects.MovieList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isabe on 2/17/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private final Context mContext;
    private Cursor mCursor;
    private MovieList mMovieListPOJO = new MovieList();
    private List<Movie> moviesListForAdapter;

    public MovieAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, 0, (List<Movie>) movies);
        mContext = context;
        moviesListForAdapter = movies;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View gridViewItems = convertView;
        if (gridViewItems == null) {
            gridViewItems = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);

        }
       // movieList = getItem(position);
        ImageView imageViewMovie = gridViewItems.findViewById(R.id.movie_image);

        assert mMovieListPOJO != null;
        Log.i(LOG_TAG, "MovieListObject is " + mMovieListPOJO.toString());

        Log.i(LOG_TAG, "List of movies is " + moviesListForAdapter.get(1).getmOriginalTitle().toString());
            Movie movie = moviesListForAdapter.get(position);

            assert movie != null;
            String moviePosterUniquePath = movie.getmImageThumbnail();
            String moviePosterFullPath = "https://image.tmdb.org/t/p/w185";
            moviePosterFullPath = moviePosterFullPath + moviePosterUniquePath;

            Log.e(LOG_TAG, "Context" + mContext);

            Picasso.get().
                    load(moviePosterFullPath)
                    .into(imageViewMovie);

        return gridViewItems;
    }

    public ArrayList setMovie(ArrayList<Movie> movieArrayList) {

        return movieArrayList;
       }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

}
