package com.example.isabe.popularmovies;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import static com.example.isabe.popularmovies.MainActivityFragment.DEFAULT_POPULAR_MOVIE_DB_URL;
import static com.example.isabe.popularmovies.MainActivityFragment.MOVIE_DB_URL_TOP_RATED;

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

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return onOptionsItemSelected(item);
    }

    public static class MoviePreferenceFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        private static final String LOG_TAG = SettingsActivity.class.getSimpleName();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference orderBy = findPreference(getString(R.string.order_by_key));

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String orderKey) {
            Activity activity = getActivity();

            switch (orderKey) {
                case "rating":
                    MainActivityFragment.movieDisplayStyleLink = MOVIE_DB_URL_TOP_RATED;
                    activity.getLoaderManager().restartLoader(0, null, (LoaderManager.LoaderCallbacks<Object>) this);
                    Log.e(LOG_TAG, activity.getString(R.string.log_top_rated_settings));
                case "famous":
                    MainActivityFragment.movieDisplayStyleLink = DEFAULT_POPULAR_MOVIE_DB_URL;
                    activity.getLoaderManager().restartLoader(MainActivityFragment.LOADER_ID,
                            null, (LoaderManager.LoaderCallbacks<Object>) this);

            }
        }
    }

}
