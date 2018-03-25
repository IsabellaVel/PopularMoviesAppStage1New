package com.example.isabe.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isabe on 3/24/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context mContext;
    private List<Review> mMovieListReviews;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView mReviewAuthorTv;
        private TextView mReviewContentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mReviewAuthorTv = itemView.findViewById(R.id.review_author_layout);
            mReviewContentTv = itemView.findViewById(R.id.review_content_layout);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mReviewContentTv.getText();
        }
    }

    public ReviewAdapter(Context context, List<Review> reviewItems) {
        mMovieListReviews = reviewItems;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_reviews_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        final Review review = mMovieListReviews.get(position);

        holder.mReviewContentTv.setText(review.getmReviewContent());
        holder.mReviewAuthorTv.setText(review.getmReviewAuthor());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReview = new Intent(Intent.ACTION_VIEW);
                intentReview.setData(Uri.parse(review.getmReviewUrl()));
                mContext.startActivity(intentReview);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieListReviews.size();
    }
}
