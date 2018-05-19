package com.education.pfohl.popularmovies2;

import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.education.pfohl.popularmovies2.models.Movie;
import com.education.pfohl.popularmovies2.models.MoviePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    private MovieImageAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        GridView gridView = findViewById(R.id.gridView);
        this.movieAdapter = new MovieImageAdapter(this, R.layout.movie_list_item, new ArrayList<Movie>());
        gridView.setAdapter(movieAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        NetworkUtils.getPopularMovies(this, new Callback<MoviePage>() {
            @Override
            public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                movieAdapter.addAll(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MoviePage> call, Throwable t) {

            }
        });
    }

}
