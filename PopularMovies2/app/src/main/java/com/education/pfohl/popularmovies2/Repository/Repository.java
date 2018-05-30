package com.education.pfohl.popularmovies2.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.education.pfohl.popularmovies2.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class Repository {



    public static void addMovies(Context context, List<Movie> movies){

        ContentValues[] values = new ContentValues[movies.size()];

        for(int i = 0; i < movies.size() ; i++){
            Movie movie = movies.get(i);
            ContentValues contentValues = new ContentValues();
            // Put the task description and selected mPriority into the ContentValues
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVote_count() );
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_ID, movie.getId() );
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_VIDEO_FLAG, movie.isVideo());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVote_average());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPoster_path());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_ORIG_LANGUAGE, movie.getOriginal_language());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_ORIG_TITLE, movie.getOriginal_title());

            String genres = "";
            for(int j = 0 ; j < movie.getGenre_ids().length - 1 ; j++){
                genres += "" + movie.getGenre_ids()[j] + ",";
            }
            genres += movie.getGenre_ids()[movie.getGenre_ids().length -1];
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_GENRE_IDS, genres);

            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdrop_path());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_ADULT, movie.isAdult());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
            contentValues.put(MovieRepoContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());

            values[i] = contentValues;
        }
        int i = context.getContentResolver().bulkInsert(MovieRepoContract.MovieEntry.CONTENT_URI, values);
        System.out.println(i);
    }


    //todo finish reading cursor
    public static List<Movie> getMovies(Context context){
       Cursor cursor = context.getContentResolver().query(MovieRepoContract.MovieEntry.CONTENT_URI, null, null, null, null);

       List<Movie> movies = new ArrayList<Movie>();
       while(cursor.moveToNext()){
           Movie movie = new Movie();

           movie.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_BACKDROP_PATH)));
           movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_POSTER_PATH)));
           movie.setAdult(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_ADULT))));

           System.out.println(movie.isAdult() + " " + movie.getPoster_path());
           movies.add(movie);

       }
        return movies;
    }
}
