package com.example.isabe.popularmovies.utilities;

import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.objects.MovieList;
import com.example.isabe.popularmovies.objects.Trailer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("popular")
    Call<MovieList> loadPopularMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieList> loadTopRatedMovies(@Query("api_key") String apiKey);

    @GET("http://img.youtube.com/vi/{key}/0.jpg")
    Call<List<Trailer>> loadTrailers(@Path("key") String string);

    }
