package com.education.pfohl.popularmovies2.MovieList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.education.pfohl.popularmovies2.MovieDetails.DetailActivity;
import com.education.pfohl.popularmovies2.NetworkUtils;
import com.education.pfohl.popularmovies2.R;
import com.education.pfohl.popularmovies2.Repository.MovieRepoContract;
import com.education.pfohl.popularmovies2.Repository.Repository;
import com.education.pfohl.popularmovies2.Setting.SettingsActivity;
import com.education.pfohl.popularmovies2.models.Movie;
import com.education.pfohl.popularmovies2.models.MoviePage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    private MovieImageAdapter movieAdapter;
    private List<Movie> movies;
    private GridView gridView;
    private Parcelable mGridState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        this.gridView = findViewById(R.id.gridView);
        this.movieAdapter = new MovieImageAdapter(this, R.layout.movie_list_item, new ArrayList<Movie>());
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Movie movie = movieAdapter.getItem(i);

                        if (movie != null) {
                            Intent openDetail = new Intent(getApplicationContext(), DetailActivity.class)
                                    .putExtra(getString(R.string.movie_object), movie.getId());

                            startActivity(openDetail);
                        } else {
                            //in case of failure do not start activity but invite to retry
                            Toast.makeText(getApplicationContext(), "Cannot show movie details right now, try again soon", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
       Parcelable state =  this.gridView.onSaveInstanceState();
        savedInstanceState.putParcelable("gridview", state);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mGridState = savedInstanceState.getParcelable("gridview");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mGridState != null) {
            gridView.onRestoreInstanceState(mGridState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("getting movies", "");

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);

        String query = preference.getString(getString(R.string.pref_query_key), getString(R.string.pref_query_default));

        if(query.equals(getString(R.string.pref_query_default)) || query.equals(getString(R.string.pref_query_favorite))){
            NetworkUtils.getPopularMovies(this, new Callback<MoviePage>() {
                @Override
                public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                    Log.d("got popular movies", "wtf");
                    Repository.addMovies(getApplicationContext(), response.body().getResults());
                }

                @Override
                public void onFailure(Call<MoviePage> call, Throwable t) {
                    Log.d("failed getting movies", "popular movies failed");

                }
            });
        }
        else if (query.equals(getString(R.string.pref_query_top_rated))){

            NetworkUtils.getTopRatedMovies(this, new Callback<MoviePage>() {
                @Override
                public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                    Log.d("got top rated movies", "wtf");
                    Repository.addMovies(getApplicationContext(), response.body().getResults());
                }

                @Override
                public void onFailure(Call<MoviePage> call, Throwable t) {
                    Log.d("failed getting movies", "top rated movies failed");

                }
            });
        }

        getContentResolver().registerContentObserver(
                MovieRepoContract.MovieEntry.CONTENT_URI, false, new ContentObserver(null) {

                    @Override
                    public void onChange(boolean selfChange) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String query = preference.getString(getString(R.string.pref_query_key), getString(R.string.pref_query_default));
                                movieAdapter.clear();

                                if(query.equals(getString(R.string.pref_query_default))){
                                    movieAdapter.addAll( Repository.getPopularMovies(getApplicationContext()));
                                }
                                else if (query.equals(getString(R.string.pref_query_top_rated))){
                                    movieAdapter.addAll( Repository.getTopRatedMovies(getApplicationContext()));
                                }
                                else if (query.equals(getString(R.string.pref_query_favorite))) {
                                    movieAdapter.addAll( Repository.getFavoriteMovies(getApplicationContext()));

                                }

                                if(mGridState != null)
                                    gridView.onRestoreInstanceState(mGridState);

                            }
                        });
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
