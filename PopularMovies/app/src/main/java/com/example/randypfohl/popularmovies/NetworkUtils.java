package com.example.randypfohl.popularmovies;

import android.content.Context;
import android.net.Uri;


public class NetworkUtils {

    public static Uri buildTrailerURI(Context context, String movieId){
        String MOVIE_BASE_URL = context.getString(R.string.posterURI);
        Uri buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath(movieId)
                .appendPath("videos")
                .build();

        return buildUri;
    }
}
