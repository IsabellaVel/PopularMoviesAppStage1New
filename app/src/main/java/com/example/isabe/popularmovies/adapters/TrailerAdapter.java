package com.example.isabe.popularmovies.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.isabe.popularmovies.DetailsActivity;
import com.example.isabe.popularmovies.R;
import com.example.isabe.popularmovies.objects.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.isabe.popularmovies.DetailsActivity.YOUTUBE_LINK_VIDEO;

/**
 * Created by isabe on 3/26/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private List<Trailer> mMovieListTrailer;
    private String LOG_TAG = TrailerAdapter.class.getSimpleName();

    private Context mContext;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        mContext = context;
        mMovieListTrailer = trailerList;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_trailers_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, final int position) {
        final Trailer trailer = mMovieListTrailer.get(position);
        final String trailerImagePath = trailer.getmKeySearchImage();

        Picasso.with(mContext)
                .load(trailerImagePath)
                .placeholder(R.drawable.movie_icon)
                .into(holder.mMovieTrailerImage);
        Log.e(LOG_TAG, "Movie image loaded from YouTube.");
        holder.mPlayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
                Intent play_browser = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_LINK_VIDEO + trailer.getmKeySearchVideo()));
                Intent playYouTubeApp = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getmKeySearchVideo()));
                try {
                    mContext.startActivity(playYouTubeApp);
                } catch (ActivityNotFoundException e) {
                    mContext.startActivity(play_browser);
                }
            }
        });
    }

    ;

    @Override
    public int getItemCount() {
        return mMovieListTrailer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private ImageView mMovieTrailerImage;
        private ImageView mPlayIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mMovieTrailerImage = itemView.findViewById(R.id.trailer_video);
            mPlayIcon = itemView.findViewById(R.id.play_icon);
        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
