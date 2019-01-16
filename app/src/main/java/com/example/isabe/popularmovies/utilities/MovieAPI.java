package com.example.isabe.popularmovies.utilities;

import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.objects.Trailer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.isabe.popularmovies.utilities.NetworkUtils.apiKey;

public interface MovieAPI {

    @GET("http://api.themoviedb.org/3/movie/{}?api_key=" + apiKey)
    Call<List<Movie>> loadMovies(@Path("type") String string);


    @GET("http://img.youtube.com/vi/{}/0.jpg")
    Call<List<Trailer>> loadTrailers(@Path("key") String string);

    }
