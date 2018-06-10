package com.education.pfohl.popularmovies2.Repository;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

// COMPLETED (1) Verify that TaskContentProvider extends from ContentProvider and implements required methods
    public class MovieContentProvider extends ContentProvider {

        // Member variable for a TaskDbHelper that's initialized in the onCreate() method
        private MovieDBHelper mMovieDbHelper;

        // Define final integer constants for the directory of tasks and a single item.
        // It's convention to use 100, 200, 300, etc for directories,
        // and related ints (101, 102, ..) for items in that directory.
        public static final int MOVIES = 100;
        public static final int MOVIE_WITH_ID = 101;

        // CDeclare a static variable for the Uri matcher that you construct
        private static final UriMatcher sUriMatcher = buildUriMatcher();



        @Override
        public boolean onCreate() {
            // COMPLETED (2) Complete onCreate() and initialize a TaskDbhelper on startup
            // [Hint] Declare the DbHelper as a global variable

            Context context = getContext();
            mMovieDbHelper = new MovieDBHelper(context);
            return true;
        }


        public static UriMatcher buildUriMatcher() {

            // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
            UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
            uriMatcher.addURI(MovieRepoContract.AUTHORITY, MovieRepoContract.PATH_MOVIES, MOVIES);
            uriMatcher.addURI(MovieRepoContract.AUTHORITY, MovieRepoContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);

            return uriMatcher;
        }

        @Override
        public Uri insert(@NonNull Uri uri, ContentValues values) {
            final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

            int match = sUriMatcher.match(uri);
            Uri returnUri;

            switch (match) {
                case MOVIES:

                    int result = (int) db.insertWithOnConflict(MovieRepoContract.MovieEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                    if (result == -1) {
                        String id = values.getAsString(MovieRepoContract.MovieEntry.COLUMN_ID);
                        result = db.update(MovieRepoContract.MovieEntry.TABLE_NAME, values, MovieRepoContract.MovieEntry.COLUMN_ID+"=?", new String[] {id});
                    }
                    if ( result > 0 ) {
                        returnUri = ContentUris.withAppendedId(MovieRepoContract.MovieEntry.CONTENT_URI, result);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            // COMPLETED (5) Notify the resolver if the uri has been changed, and return the newly inserted URI
            getContext().getContentResolver().notifyChange(uri, null);

            // Return constructed uri (this points to the newly inserted row of data)
            return returnUri;
            }


        @Override
    public int bulkInsert(@NonNull Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int result = 0;
        switch (match) {
            case MOVIES:

                db.beginTransaction();
                try {
                        for(ContentValues value : values){
                            int id = (int) db.insertWithOnConflict(MovieRepoContract.MovieEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_IGNORE);
                            if (id == -1) {
                                id = db.update(MovieRepoContract.MovieEntry.TABLE_NAME, value, MovieRepoContract.MovieEntry.COLUMN_ID+"=?", new String[] {value.getAsString(MovieRepoContract.MovieEntry.COLUMN_ID)});
                            }
                        if ( id <= 0 ) {
                            throw new android.database.SQLException("Failed to insert row into " + uri + " " + id);
                        }
                    }
                    db.setTransactionSuccessful();
                    result = 1;
                } finally {
                    db.endTransaction();
                }

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return result;
    }


        @Override
        public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                String[] selectionArgs, String sortOrder) {

            // Get access to underlying database (read-only for query)
            final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();

            // Write URI match code and set a variable to return a Cursor
            int match = sUriMatcher.match(uri);
            Cursor retCursor;

            // Query for the tasks directory and write a default case
            switch (match) {
                // Query for the tasks directory
                case MOVIES:
                    retCursor =  db.query(MovieRepoContract.MovieEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                    break;
                // Default exception
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            // Set a notification URI on the Cursor and return that Cursor
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);

            // Return the desired Cursor
            return retCursor;
        }



        @Override
        public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

            throw new UnsupportedOperationException("Not yet implemented");
        }


        @Override
        public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

            int match = sUriMatcher.match(uri);
            int result;

            switch (match) {
                case MOVIES:
                    result = db.update(MovieRepoContract.MovieEntry.TABLE_NAME,  values, selection, selectionArgs);

                    if ( result < 1){
                        throw new android.database.SQLException("Failed to update row in " + uri);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);

            }

            if(result > 0)
                 getContext().getContentResolver().notifyChange(uri, null);
            return result;
        }


        @Override
        public String getType(@NonNull Uri uri) {

            throw new UnsupportedOperationException("Not yet implemented");
        }

    }
