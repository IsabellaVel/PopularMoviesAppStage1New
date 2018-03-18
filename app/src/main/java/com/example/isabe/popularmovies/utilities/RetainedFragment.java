package com.example.isabe.popularmovies.utilities;

import android.app.Fragment;
import android.os.Bundle;

import com.example.isabe.popularmovies.Movie;

/**
 * Created by isabe on 2/24/2018.
 */

public class RetainedFragment extends Fragment {

    private Movie data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(Movie data) {
        this.data = data;
    }

    public Movie getData() {
        return data;
    }
}
