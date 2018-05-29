package com.education.pfohl.popularmovies2.Repository;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.education.pfohl.popularmovies2.Repository.MovieRepoContract.MovieEntry;

public class MovieDBHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "movieDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID                  + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_ID            + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_VOTE_COUNT    + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_VIDEO_FLAG    + " TEXT NOT NULL," +
                MovieEntry.COLUMN_VOTE_AVERAGE  + " REAL NOT NULL, " +
                MovieEntry.COLUMN_TITLE         + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POPULARITY    + " REAL NOT NULL, " +
                MovieEntry.COLUMN_POSTER_PATH   + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_ORIG_LANGUAGE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_ORIG_TITLE    + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_GENRE_IDS     + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_ADULT         + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW      + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE  + " TEXT NOT NULL" +
                ");";

        db.execSQL(CREATE_TABLE);
    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}