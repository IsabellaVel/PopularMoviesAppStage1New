package com.example.isabe.popularmovies.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {
    @SerializedName("results")
    List<Trailer> trailers;

    public List<Trailer> getTrailers(){return trailers;}
}
