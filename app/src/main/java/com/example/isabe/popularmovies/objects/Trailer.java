package com.example.isabe.popularmovies.objects;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.isabe.popularmovies.DetailsActivity;

/**
 * Created by isabe on 3/24/2018.
 */

public class Trailer implements Parcelable {
    public String mNameTrailer;
    public String mTrailerSize;
    public String mTrailerSite;
    public String mYoutubeImageUrl = "http://img.youtube.com/vi/";
    public String mKeySearchVideo;

    public Trailer(String name, String size, String site, String key) {
        mNameTrailer = name;
        mTrailerSize = size;
        mTrailerSite = site;
        mKeySearchVideo = key;
    }

    protected Trailer(Parcel in) {
        mNameTrailer = in.readString();
        mTrailerSize = in.readString();
        mTrailerSite = in.readString();
        mKeySearchVideo = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mNameTrailer);
        parcel.writeString(mTrailerSize);
        parcel.writeString(mTrailerSite);
        parcel.writeString(mKeySearchVideo);
    }

    public String getmTrailerSize() {
        return mTrailerSize;
    }

    public String getmTrailerSite() {
        return mTrailerSite;
    }

    public String getmKeySearchImage() {
        return mYoutubeImageUrl + mKeySearchVideo + "/0.jpg";
    }

    public String getmKeySearchVideo() {
        return mKeySearchVideo;
    }
}
