package com.example.isabe.popularmovies.objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    @SerializedName("results")
    private List<Movie> movieList;

    public List<Movie> getMovieList(){
        return movieList;
    }
    }
