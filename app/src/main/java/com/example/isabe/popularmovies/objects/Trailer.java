package com.example.isabe.popularmovies.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by isabe on 3/24/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Trailer implements Parcelable {
    @SerializedName("name")
    private String mNameTrailer;
    @SerializedName("size")
    private String mTrailerSize;
    @SerializedName("site")
    private String mTrailerSite;
    @SerializedName("key")
    public String mKeySearchVideo;

    public Trailer(String name, String size, String site, String key) {
        mNameTrailer = name;
        mTrailerSize = size;
        mTrailerSite = site;
        mKeySearchVideo = key;
    }

    private Trailer(Parcel in) {
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

    public String getmKeySearchImage() {
        String mYoutubeImageUrl = "http://img.youtube.com/vi/";
        return mYoutubeImageUrl + mKeySearchVideo + "/0.jpg";
    }

    public String getmKeySearchVideo() {
        return mKeySearchVideo;
    }
}
