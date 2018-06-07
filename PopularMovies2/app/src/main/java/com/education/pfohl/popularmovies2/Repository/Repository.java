package com.education.pfohl.popularmovies2.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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


    public static List<Movie> getMovies(Context context){
       Cursor cursor = context.getContentResolver().query(MovieRepoContract.MovieEntry.CONTENT_URI, null, null, null, null);

       List<Movie> movies = new ArrayList<Movie>();
       while(cursor.moveToNext()){
        movies.add(packageMovie(cursor));
       }
        return movies;
    }

    public static Movie getMovie(Context context, int id){
        String selection = MovieRepoContract.MovieEntry.COLUMN_ID+"=?";
        String[] seletion_args={String.valueOf(id)};
        Cursor cursor = context.getContentResolver().query(MovieRepoContract.MovieEntry.CONTENT_URI, null, selection, seletion_args, null);

        return cursor.moveToFirst()?  packageMovie(cursor): null;
    }


    private static Movie packageMovie(Cursor cursor){
        Movie movie = new Movie();

        movie.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_BACKDROP_PATH)));
        movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_POSTER_PATH)));
        movie.setAdult(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_ADULT))));

        String[] genre = cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_GENRE_IDS)).split(",");
        int[] genres = new int[genre.length];

        for(int j = 0 ; j < genre.length; j++){
            genres[j] = Integer.valueOf(genre[j]);
        }
        movie.setGenre_ids(genres);
        movie.setId(cursor.getInt(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_ID)));
        movie.setOriginal_language(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_ORIG_LANGUAGE)));
        movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_ORIG_TITLE)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_OVERVIEW)));
        movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_POPULARITY)));
        movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_RELEASE_DATE)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_TITLE)));
        movie.setVideo(cursor.getString(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_VIDEO_FLAG)).equals("true"));
        movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
        movie.setVote_count(cursor.getInt(cursor.getColumnIndex(MovieRepoContract.MovieEntry.COLUMN_VOTE_COUNT)));
        return  movie;

    }
}
