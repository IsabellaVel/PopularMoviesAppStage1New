package com.example.isabe.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.isabe.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by isabe on 2/17/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private Context mContext;

    public MovieAdapter(@NonNull Activity context, List<Movie> movieItems) {
        super(context, 0, movieItems);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewItems = convertView;
        if (gridViewItems == null) {
            gridViewItems = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);

        }
        Movie movieItem = getItem(position);
        ImageView imageViewMovie = (ImageView) gridViewItems.findViewById(R.id.movie_image);
        String moviePosterUniquePath = movieItem.getmImageThumbnail();
        String moviePosterFullPath = "https://image.tmdb.org/t/p/w185";
        moviePosterFullPath = moviePosterFullPath+moviePosterUniquePath;

        Log.e(LOG_TAG, "Context" + mContext);

        Picasso.with(mContext).
                load(moviePosterFullPath)
                .into(imageViewMovie);

        return gridViewItems;
    }

}
