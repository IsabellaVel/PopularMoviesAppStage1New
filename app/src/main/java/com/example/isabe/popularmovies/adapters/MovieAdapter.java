package com.example.isabe.popularmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.isabe.popularmovies.R;
import com.example.isabe.popularmovies.objects.Movie;
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

    public MovieAdapter(@NonNull Activity context, List<Movie> movieItems) {
        super(context, 0, movieItems);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View gridViewItems = convertView;
        if (gridViewItems == null) {
            gridViewItems = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);

        }
        Movie movieItem = getItem(position);
        ImageView imageViewMovie = gridViewItems.findViewById(R.id.movie_image);
        assert movieItem != null;
        String moviePosterUniquePath = movieItem.getmImageThumbnail();
        String moviePosterFullPath = "https://image.tmdb.org/t/p/w185";
        moviePosterFullPath = moviePosterFullPath + moviePosterUniquePath;

        Log.e(LOG_TAG, "Context" + mContext);

        Picasso.with(mContext).
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
