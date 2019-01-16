package com.example.isabe.popularmovies.utilities;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.isabe.popularmovies.objects.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MovieController implements Callback<List<Movie>> {
    static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

    public void start(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<List<Movie>> callPopular = movieAPI.loadMovies("popular");
        //Call<List<Movie>> callTopRate = movieAPI.loadMovies("top_rated");
        callPopular.enqueue((retrofit2.Callback<List<Movie>>) this);
        //callTopRate.enqueue((retrofit2.Callback<List<Movie>>) this);
    }


    public void startTopRated(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<List<Movie>> callTopRate = movieAPI.loadMovies("top_rated");
        callTopRate.enqueue((retrofit2.Callback<List<Movie>>) this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
        if (response.isSuccessful()) {
            List<Movie> movieFromCalls = response.body();
            movieFromCalls.forEach(Movie ->
                    System.out.print(Movie.getmOriginalTitle()));
        }else {
            System.out.print(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Movie>> call, Throwable t) {
    t.printStackTrace();
    }
}
