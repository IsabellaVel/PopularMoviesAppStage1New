package com.example.isabe.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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

    public MovieAdapter(@NonNull Activity context, List<Movie> movieItems) {
        super(context, 0, movieItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie currentMovie = getItem(position);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);

        }
        ImageView imageViewMovie = (ImageView) listItemView.findViewById(R.id.movie_image);
        Picasso.with(getContext()).
                load(Uri.parse(currentMovie.getmImageThumbnail()))
                .into(imageViewMovie);

        return listItemView;
    }

}
