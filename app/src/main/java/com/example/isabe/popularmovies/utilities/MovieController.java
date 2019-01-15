package com.example.isabe.popularmovies.utilities;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.isabe.popularmovies.objects.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.security.auth.callback.Callback;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MovieController implements MovieAPI {
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

        Call<List<MovieFromCall>> callPopular = movieAPI.loadMovies("popular");
        Call<List<MovieFromCall>> callTopRate = movieAPI.loadMovies("top_rated");
        callPopular.enqueue((retrofit2.Callback<List<MovieFromCall>>) this);
        callTopRate.enqueue((retrofit2.Callback<List<MovieFromCall>>) this);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void  onResponse(Call<List<MovieFromCall>> call, Response<List<MovieFromCall>> response){
        if (response.isSuccessful()) {
            List<MovieFromCall> movieFromCalls = response.body();
            movieFromCalls.forEach(MovieFromCall ->
                    System.out.print(MovieFromCall.movie));
        }else {
            System.out.print(response.errorBody());
        }
    };

    //this has to be checked
    @Override
    public Call<List<MovieFromCall>> loadMovies(String string) {
        return null;
    }
}
