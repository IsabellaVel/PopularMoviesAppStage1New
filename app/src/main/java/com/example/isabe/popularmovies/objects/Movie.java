package com.example.isabe.popularmovies.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by isabe on 2/17/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Movie implements Parcelable {
    private final String mOriginalTitle;
    private String mReleaseDate;
    private String mVoteAverage;
    private String mOverviewMovie;
    private String mImageThumbnail;
    private String mBackdropPath;
    private final int mMovieTMDBId;

    public Movie(String originalTitle, String releasedOnDate, String overview, String imageThumbnail, String averageVote,
                 String backdropPath, int movieId) {
        this.mOriginalTitle = originalTitle;
        this.mReleaseDate = releasedOnDate;
        this.mOverviewMovie = overview;
        this.mImageThumbnail = imageThumbnail;
        this.mVoteAverage = averageVote;
        this.mBackdropPath = backdropPath;
        this.mMovieTMDBId = movieId;
    }

    public Movie(String imageBackdrop, String overview, String title, int movieId, String date, String vote) {
        this.mOriginalTitle = title;
        this.mReleaseDate = date;
        this.mVoteAverage = vote;
        this.mOverviewMovie = overview;
        this.mImageThumbnail = imageBackdrop;
        this.mMovieTMDBId = movieId;
    }

    private Movie(Parcel in) {
        mOriginalTitle = in.readString();
        mReleaseDate = in.readString();
        mOverviewMovie = in.readString();
        mVoteAverage = in.readString();
        mImageThumbnail = in.readString();
        mBackdropPath = in.readString();
        mMovieTMDBId = in.readInt();

    }

        public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return mOriginalTitle + "--" + mReleaseDate + "--"
                + mVoteAverage + "--" + mOverviewMovie + "--" + mImageThumbnail + "--" + mBackdropPath
                + "--" + mMovieTMDBId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mOverviewMovie);
        parcel.writeString(mVoteAverage);
        parcel.writeString(mImageThumbnail);
        parcel.writeString(mBackdropPath);
        parcel.writeInt(mMovieTMDBId);
    }

    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    public String getmOverviewMovie() {
        return mOverviewMovie;
    }

    public String getmImageThumbnail() {
        return mImageThumbnail;
    }

    public String getmBackdropPath() {
        return mBackdropPath;
    }

    public int getmMovieTMDBId() {
        return mMovieTMDBId;
    }
}
