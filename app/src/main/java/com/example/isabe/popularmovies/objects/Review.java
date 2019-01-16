package com.example.isabe.popularmovies.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by isabe on 3/24/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Review implements Parcelable {
    @SerializedName("url")
    private String mReviewUrl;
    @SerializedName("author")
    private String mReviewAuthor;
    @SerializedName("content")
    private String mReviewContent;

    public Review(String author, String content, String mReviewUrl) {
        mReviewAuthor = author;
        mReviewContent = content;
    }

    private Review(Parcel in) {
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
