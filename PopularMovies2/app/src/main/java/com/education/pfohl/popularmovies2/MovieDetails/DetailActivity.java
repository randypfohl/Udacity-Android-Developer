package com.education.pfohl.popularmovies2.MovieDetails;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.education.pfohl.popularmovies2.NetworkUtils;
import com.education.pfohl.popularmovies2.R;
import com.education.pfohl.popularmovies2.Repository.MovieRepoContract;
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

    int id;
    Movie movie;
    List<Trailer> videoList;
    List<Review> reviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        id = intent.getIntExtra(getString(R.string.movie_object), -1);
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

        ((ImageButton) findViewById(R.id.favorite_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.setFavorite(getApplicationContext(), id, !movie.isFavorite());
            }
        });



        // todo set up view to look pretty
        // todo make movies parceable because you hate yourself
        // todo after movies are made parceable you can apply the savedinstancestate to the details page, store the movie list on rotation, check lifecycles, add flags

        refreshUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable("movie", this.movie);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.movie = savedInstanceState.getParcelable("movie");
    }

    @Override
    protected void onStart() {
        super.onStart();

        getContentResolver().registerContentObserver(
                MovieRepoContract.MovieEntry.CONTENT_URI, false, new ContentObserver(null) {

                    @Override
                    public void onChange(boolean selfChange) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    movie = Repository.getMovie(getApplicationContext(), id );
                                    if(movie.isFavorite()) {
                                        ((ImageButton) findViewById(R.id.favorite_button)).setImageResource(R.drawable.ic_baseline_favorite_24px);
                                    }
                                    else {
                                        ((ImageButton) findViewById(R.id.favorite_button)).setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                                    }
                                }
                            });
                    }
                });
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
        if(movie.isFavorite()) {
            ((ImageButton) findViewById(R.id.favorite_button)).setImageResource(R.drawable.ic_baseline_favorite_24px);
        }

        final String MOVIE_BASE_URL = getString(R.string.tmdb_base_url);
        Uri buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(getString(R.string.image_Size))
                .appendPath(movie.getPoster_path().substring(1)).build();

        String url = buildUri.toString();
        Picasso.get().load(url).into((ImageView) findViewById(R.id.poster));

    }


}
