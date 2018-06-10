package com.education.pfohl.popularmovies2.Repository;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieRepoContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.education.pfohl.popularmovies2";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES = "movies";


    /* TaskEntry is an inner class that defines the contents of the task table */
        public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


        // Task table and column names
            public static final String TABLE_NAME = "Movies";

            // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
            // "_ID" column in addition to the two below
            public static final String COLUMN_ID = "id";
            public static final String COLUMN_VOTE_COUNT = "vote_count";
            public static final String COLUMN_VIDEO_FLAG = "video_boolean";
            public static final String COLUMN_VOTE_AVERAGE = "vote_average";
            public static final String COLUMN_TITLE = "title";
            public static final String COLUMN_POPULARITY = "popularity";
            public static final String COLUMN_POSTER_PATH = "poster_path";
            public static final String COLUMN_ORIG_LANGUAGE = "orig_language";
            public static final String COLUMN_ORIG_TITLE = "orig_title";
            public static final String COLUMN_GENRE_IDS = "genre_ids";
            public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
            public static final String COLUMN_ADULT = "adult";
            public static final String COLUMN_OVERVIEW = "overview";
            public static final String COLUMN_RELEASE_DATE = "release_date";
            public static final String COLUMN_FAVORITE_FLAG = "favorite";

        }
}
