package com.education.pfohl.popularmovies2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.education.pfohl.popularmovies2.Repository.Repository;
import com.education.pfohl.popularmovies2.models.Movie;

public class DetailActivity extends AppCompatActivity {

    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        int id = intent.getIntExtra(getString(R.string.movie_object), -1);
        movie = Repository.getMovie(this, id );

        //todo view model this data and set it correctly
        // todo set up view to look pretty
        // todo make network calls for trailers and link them to play
        // todo create db to keep track of voted procedures.
        // todo update db instead of always adding
        // todo find more todos
        ((TextView)findViewById(R.id.title)).setText(movie.getTitle());
    }


}
