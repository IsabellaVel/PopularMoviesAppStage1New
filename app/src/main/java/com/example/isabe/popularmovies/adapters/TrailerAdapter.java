package com.example.isabe.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.isabe.popularmovies.R;
import com.example.isabe.popularmovies.objects.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by isabe on 3/26/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private List<Trailer> mMovieListTrailer;
    private Context mContext;

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        mMovieListTrailer = trailerList;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_trailers_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {
        final Trailer trailer = mMovieListTrailer.get(position);
        String trailerImagePath = trailer.getmKeySearchVideo();

        Picasso.with(mContext)
                .load(trailerImagePath)
                .placeholder(R.drawable.movie_icon)
                .into(holder.mMovieTrailerImage);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private ImageView mMovieTrailerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mMovieTrailerImage = (ImageView) itemView.findViewById(R.id.trailer_video);
        }
    }


}
