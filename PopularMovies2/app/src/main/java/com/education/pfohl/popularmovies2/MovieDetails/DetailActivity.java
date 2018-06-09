package com.education.pfohl.popularmovies2.MovieDetails;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.pfohl.popularmovies2.NetworkUtils;
import com.education.pfohl.popularmovies2.R;
import com.education.pfohl.popularmovies2.Repository.Repository;
import com.education.pfohl.popularmovies2.models.Movie;
import com.education.pfohl.popularmovies2.models.Review;
import com.education.pfohl.popularmovies2.models.ReviewPage;
import com.education.pfohl.popularmovies2.models.TrailerPage;
import com.education.pfohl.popularmovies2.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    Movie movie;
    List<Trailer> videoList;
    List<Review> reviews;


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
                setAdapter();
            }

            @Override
            public void onFailure(Call<TrailerPage> call, Throwable t) {
                System.out.println(call.request().body() );
                t.printStackTrace();
            }
        }, String.valueOf(id));

        NetworkUtils.getMovieReviews(this, new Callback<ReviewPage>() {
            @Override
            public void onResponse(Call<ReviewPage> call, Response<ReviewPage> response) {
                reviews = response.body().getResults();
                setReview();
            }

            @Override
            public void onFailure(Call<ReviewPage> call, Throwable t) {
                System.out.println(call.request().body() );
                t.printStackTrace();
            }
        }, String.valueOf(id));





        // todo set up view to look pretty
        // todo create db to keep track of voted procedures.
        // todo update db instead of always adding
        // todo find more todos
        refreshUI();
    }

    private void setAdapter(){
        MovieTrailerAdapter trailerAdapter = new MovieTrailerAdapter(this, videoList);
        RecyclerView trailers = findViewById(R.id.trailer_list);
        trailers.setLayoutManager(new LinearLayoutManager(this));
        trailers.setAdapter(trailerAdapter);
    }

    private void setReview(){
        if(reviews != null && reviews.size() > 1) {
            Review review = reviews.get(0);
            ((TextView) findViewById(R.id.review_title)).setText("Review by: " + review.getAuthor());
            ((TextView) findViewById(R.id.review)).setText(review.getContent());
        }
        else {
            ((TextView) findViewById(R.id.review)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.review_title)).setVisibility(View.GONE);
        }

    }

    private void refreshUI(){
        ((TextView)findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView)findViewById(R.id.releaseDate)).setText(movie.getRelease_date());
        ((TextView)findViewById(R.id.vote_average)).setText(String.valueOf(movie.getVote_average()) + " / 10");
        ((TextView)findViewById(R.id.plot)).setText(movie.getOverview());


        final String MOVIE_BASE_URL = getString(R.string.tmdb_base_url);
        Uri buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(getString(R.string.image_Size))
                .appendPath(movie.getPoster_path().substring(1)).build();

        String url = buildUri.toString();
        Picasso.get().load(url).into((ImageView) findViewById(R.id.poster));
    }


}
