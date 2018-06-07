package com.education.pfohl.popularmovies2.MovieDetails;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.education.pfohl.popularmovies2.MovieList.MovieImageAdapter;
import com.education.pfohl.popularmovies2.NetworkUtils;
import com.education.pfohl.popularmovies2.R;
import com.education.pfohl.popularmovies2.Repository.Repository;
import com.education.pfohl.popularmovies2.models.Movie;
import com.education.pfohl.popularmovies2.models.TrailerPage;
import com.education.pfohl.popularmovies2.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    Movie movie;
    List<Trailer> videoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        int id = intent.getIntExtra(getString(R.string.movie_object), -1);
        movie = Repository.getMovie(this, id );
        NetworkUtils.getVideoTrailers(this, new Callback<TrailerPage>() {
            @Override
            public void onResponse(Call<TrailerPage> call, Response<TrailerPage> response) {
                videoList = response.body().getResults();
            }

            @Override
            public void onFailure(Call<TrailerPage> call, Throwable t) {
                System.out.println(call.request().body() );
                t.printStackTrace();
            }
        }, String.valueOf(id));

        MovieTrailerAdapter trailerAdapter = new MovieTrailerAdapter(this, R.layout.trailer_list_item, new ArrayList<Trailer>());
        RecyclerView trailers = findViewById(R.id.trailer_list);
        trailers.setAdapter(trailerAdapter);

        // todo set up view to look pretty
        // todo make network calls for trailers and link them to play
        // todo create db to keep track of voted procedures.
        // todo update db instead of always adding
        // todo find more todos
        refreshUI();
    }

    private void refreshUI(){
        ((TextView)findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView)findViewById(R.id.releaseDate)).setText(movie.getRelease_date());
        ((TextView)findViewById(R.id.vote_average)).setText(String.valueOf(movie.getVote_average()));
        ((TextView)findViewById(R.id.plot)).setText(movie.getOverview());


        final String MOVIE_BASE_URL = getString(R.string.tmdb_base_url);
        Uri buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(getString(R.string.image_Size))
                .appendPath(movie.getPoster_path().substring(1)).build();

        String url = buildUri.toString();
        Picasso.get().load(url).into((ImageView) findViewById(R.id.poster));
    }


}
