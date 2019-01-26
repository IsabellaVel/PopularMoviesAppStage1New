package com.example.isabe.popularmovies.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews {
    @SerializedName("results")
    public List<Review> reviewList;

    public List<Review> getReviewList(){
        return reviewList;
    }
}
