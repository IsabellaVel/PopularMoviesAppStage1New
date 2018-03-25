package com.example.isabe.popularmovies;

import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by isabe on 3/24/2018.
 */

public class Trailer implements Parcelable {
    private String mNameTrailer;
    private String mTrailerSize;
    private String mTrailerSite;

    public Trailer(String name, String size, String site) {
        mNameTrailer = name;
        mTrailerSize = size;
        mTrailerSite = site;
    }

    protected Trailer(Parcel in) {
        mNameTrailer = in.readString();
        mTrailerSize = in.readString();
        mTrailerSite = in.readString();
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
    }

    public String getmNameTrailer() {
        return mNameTrailer;
    }

    public String getmTrailerSize() {
        return mTrailerSize;
    }

    public String getmTrailerSite() {
        return mTrailerSite;
    }
}
