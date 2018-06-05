package com.education.pfohl.popularmovies2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.pfohl.popularmovies2.Repository.Repository;
import com.education.pfohl.popularmovies2.models.Movie;
import com.education.pfohl.popularmovies2.models.Trailers;
import com.education.pfohl.popularmovies2.models.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    Movie movie;
    List<Video> videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        int id = intent.getIntExtra(getString(R.string.movie_object), -1);
        movie = Repository.getMovie(this, id );
        NetworkUtils.getVideoTrailers(this, new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                videoList = response.body().getResults();
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {
                System.out.println(call.request().body() );
                t.printStackTrace();
            }
        }, String.valueOf(id));

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
