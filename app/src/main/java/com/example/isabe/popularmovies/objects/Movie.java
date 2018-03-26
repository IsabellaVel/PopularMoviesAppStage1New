package com.example.isabe.popularmovies.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by isabe on 2/17/2018.
 */

public class Movie implements Parcelable {
    private final String mOriginalTitle;
    private String mReleaseDate;
    private final String mVoteAverage;
    private final String mOverviewMovie;
    private final String mImageThumbnail;
    private String mBackdropPath;
    private Boolean mTrailerPath;
    private final int mMovieTMDBId;

    public Movie(String originalTitle, String releasedOnDate, String overview, String imageThumbnail, String averageVote,
                 String backdropPath, Boolean movieTrailer, int movieId) {
        this.mReleaseDate = releasedOnDate;
        this.mOriginalTitle = originalTitle;
        this.mVoteAverage = averageVote;
        this.mOverviewMovie = overview;
        this.mImageThumbnail = imageThumbnail;
        this.mBackdropPath = backdropPath;
        this.mTrailerPath = movieTrailer;
        this.mMovieTMDBId = movieId;
    }

    public Movie(String imageThumbnail, String overview, String title, int movieId, String vote) {
        this.mOriginalTitle = title;
        this.mVoteAverage = vote;
        this.mOverviewMovie = overview;
        this.mImageThumbnail = imageThumbnail;
        this.mMovieTMDBId = movieId;
    }

    private Movie(Parcel in) {
        mOriginalTitle = in.readString();
        mReleaseDate = in.readString();
        mOverviewMovie = in.readString();
        mImageThumbnail = in.readString();
        mVoteAverage = in.readString();
        mBackdropPath = in.readString();
        mTrailerPath = in.readByte() !=0;
        mMovieTMDBId = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return mOriginalTitle + "--" + mReleaseDate + "--"
                + mVoteAverage + "--" + mOverviewMovie + "--" + mImageThumbnail + "--" + mBackdropPath
                + "--" + mTrailerPath
                + "--" + mMovieTMDBId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mVoteAverage);
        parcel.writeString(mOverviewMovie);
        parcel.writeString(mImageThumbnail);
        parcel.writeString(mBackdropPath);
        parcel.writeByte((byte) (mTrailerPath ? 1 : 0));
        parcel.writeInt(mMovieTMDBId);
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public Boolean getmTrailerPath() {
        return mTrailerPath;
    }

    public int getmMovieTMDBId() {
        return mMovieTMDBId;
    }
}
