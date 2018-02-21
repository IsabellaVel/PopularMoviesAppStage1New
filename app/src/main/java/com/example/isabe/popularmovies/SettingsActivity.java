package com.example.isabe.popularmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.example.isabe.popularmovies.utilities.NetworkUtils;

/**
 * Created by isabe on 2/21/2018.
 */

public class SettingsActivity extends AppCompatActivity {
    private String orderKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class MoviePreferenceFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference orderBy = findPreference(getString(R.string.order_by_key));

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String orderKey) {
            Activity activity = getActivity();
            if (orderKey.equals("famous")) {
                NetworkUtils.MOVIE_BASE_URL = NetworkUtils.DYNAMIC_MOVIE_DB_URL;
            } else if (orderKey.equals("rating")) {
                NetworkUtils.MOVIE_BASE_URL = NetworkUtils.MOVIE_DB_URL_TOP_RATED;
            }

        }
    }
}
