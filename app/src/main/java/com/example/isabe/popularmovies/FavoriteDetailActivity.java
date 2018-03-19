package com.example.isabe.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;

/**
 * Created by isabe on 3/19/2018.
 */

public class FavoriteDetailActivity extends AppCompatActivity {

    Movie mMovieDetails;
    int movieIdText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_detail);

        mMovieDetails = getIntent().getExtras().getParcelable("MOVIE_DETAILS");
        String movieUriString = getIntent().getExtras().getString("MOVIE_URI");

        Uri mMovieUri = Uri.parse(movieUriString);

        TextView titleMovie = findViewById(R.id.text_title_fav);
        TextView synopsisMovie = findViewById(R.id.tv_synopsis_fav);
        TextView rateMovie = findViewById(R.id.tv_rate_fav);
        TextView releaseDateMovie = findViewById(R.id.tv_releaseDate_fav);
        ImageView imageBackdrop = findViewById(R.id.image_backdrop_fav);
        int movieId;

        Bundle bundleGetExtras = getIntent().getExtras();
        if (bundleGetExtras != null) {
            String originalTitle = bundleGetExtras.getString(getString(R.string.original_title));
            String releaseDate = bundleGetExtras.getString(getString(R.string.string_date_release));
            String imagePath = bundleGetExtras.getString(getString(R.string.image_path_string));
            String movieSynopsis = bundleGetExtras.getString(getString(R.string.movie_summary));
            String voteAverage = bundleGetExtras.getString(getString(R.string.vote_string));
            voteAverage = voteAverage + " " + getString(R.string.votePercent);
            movieIdText = bundleGetExtras.getInt(getString(R.string.movie_string_id));
            String fullImagePosterLink = "https://image.tmdb.org/t/p/w185" + imagePath;
            String backdropImage = bundleGetExtras.getString(getString(R.string.backdrop_string_path));
            String fullImageBackdropPath = "https://image.tmdb.org/t/p/w185" + backdropImage;


            titleMovie.setText(originalTitle);
            synopsisMovie.setText(movieSynopsis);
            rateMovie.setText(voteAverage);
            releaseDateMovie.setText(releaseDate);

            Picasso.with(this)
                    .load(new File(fullImageBackdropPath))
                    .into(imageBackdrop);

        }
    }
}
