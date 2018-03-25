package com.example.isabe.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by isabe on 3/24/2018.
 */

public class Review implements Parcelable {
    private String mReviewUrl;
    private String mReviewAuthor;
    private String mReviewContent;

    public Review(String author, String content, String mReviewUrl) {
        mReviewAuthor = author;
        mReviewContent = content;
    }

    protected Review(Parcel in) {
        mReviewUrl = in.readString();
        mReviewAuthor = in.readString();
        mReviewContent = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mReviewUrl);
        parcel.writeString(mReviewAuthor);
        parcel.writeString(mReviewContent);
    }

    public String getmReviewUrl() {
        return mReviewUrl;
    }

    public String getmReviewAuthor() {
        return mReviewAuthor;
    }

    public String getmReviewContent() {
        return mReviewContent;
    }

}
