package com.example.isabe.popularmovies.utilities;

import com.example.isabe.popularmovies.objects.Movie;
import com.example.isabe.popularmovies.objects.MovieList;
import com.example.isabe.popularmovies.objects.Reviews;
import com.example.isabe.popularmovies.objects.Trailer;
import com.example.isabe.popularmovies.objects.Trailers;

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
    Call<List<Trailer>> loadTrailersImage(@Path("key") String string);

    @GET("{id}/videos")
    Call<Trailers> callTrailer(@Path("id") int intId,@Query("api_key") String string);

    @GET("{id}/reviews")
    Call<Reviews> callReview(@Path("id") int movieIdFromTMDB, @Query("api_key") String apiKey);
}
