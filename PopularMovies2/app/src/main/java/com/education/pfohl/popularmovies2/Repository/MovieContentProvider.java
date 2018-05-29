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

import com.education.pfohl.popularmovies2.models.Movie;

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

            // COMPLETED (2) Write URI matching code to identify the match for the tasks directory
            int match = sUriMatcher.match(uri);
            Uri returnUri; // URI to be returned

            switch (match) {
                case MOVIES:
                    // COMPLETED (3) Insert new values into the database
                    // Inserting values into tasks table
                    long id = db.insert(MovieRepoContract.MovieEntry.TABLE_NAME, null, values);
                    if ( id > 0 ) {
                        returnUri = ContentUris.withAppendedId(MovieRepoContract.MovieEntry.CONTENT_URI, id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                    break;
                // COMPLETED (4) Set the value for the returnedUri and write the default case for unknown URI's
                // Default case throws an UnsupportedOperationException
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            // COMPLETED (5) Notify the resolver if the uri has been changed, and return the newly inserted URI
            getContext().getContentResolver().notifyChange(uri, null);

            // Return constructed uri (this points to the newly inserted row of data)
            return returnUri;
            }


        @Override
        public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                            String[] selectionArgs, String sortOrder) {

            throw new UnsupportedOperationException("Not yet implemented");
        }


        @Override
        public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

            throw new UnsupportedOperationException("Not yet implemented");
        }


        @Override
        public int update(@NonNull Uri uri, ContentValues values, String selection,
                          String[] selectionArgs) {

            throw new UnsupportedOperationException("Not yet implemented");
        }


        @Override
        public String getType(@NonNull Uri uri) {

            throw new UnsupportedOperationException("Not yet implemented");
        }

    }
