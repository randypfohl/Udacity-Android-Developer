package com.education.pfohl.popularmovies2;

import android.content.Context;

import com.education.pfohl.popularmovies2.models.Movie;
import com.education.pfohl.popularmovies2.models.MoviePage;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class NetworkUtils {

    public interface MovieDBService {

        String API_KEY_QUERY = "api_key";
        String LANGUAGE_KEY_QUERY = "language";

        @GET("popular")
        Call<MoviePage> listPopularMovies(@Query(API_KEY_QUERY) String api_key, @Query(LANGUAGE_KEY_QUERY) String language);
    }


    public static void getPopularMovies(Context context, Callback<MoviePage> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDBService service = retrofit.create(MovieDBService.class);
        Call<MoviePage> popularMovies = service.listPopularMovies(context.getString(R.string.api_value), Locale.getDefault().toString());
        popularMovies.enqueue(callback);
    }

}
