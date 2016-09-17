package com.example.randypfohl.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class movieListFragment extends Fragment {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public movieImageAdapter movieAdapter;

    //saving JSONArray to reduce network calls for details
    private JSONArray movieArray;

    public movieListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //custom adapter to handle multiple picasso requests
        movieAdapter =
                new movieImageAdapter(
                        getActivity(),
                        R.layout.list_item_movie,
                        new ArrayList<String>());

        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String moviePosterPath= movieAdapter.getItem(i);

                //helper method getMovieDetails returns array of details from JSON object to package into intent.

                String[] information = getMovieDetails(i);
                if (information != null) {
                    Intent openDetail = new Intent(getActivity(), detailActivity.class)
                            .putExtra(getString(R.string.detail_movie_title), information[0])
                            .putExtra(getString(R.string.detail_movie_release), information[1])
                            .putExtra(getString(R.string.detail_movie_vote_ave), information[2])
                            .putExtra(getString(R.string.detail_movie_plot), information[3])
                            .putExtra(getString(R.string.detail_movie_poster), moviePosterPath);

                    startActivity(openDetail);
                }
                else {
                    //in case of failure do not start activity but invite to retry
                    Toast.makeText(getActivity(), "Cannot show movie details right now, try again soon", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


    public String[] getMovieDetails(int i){

        String[] information = new String[4];

        final String MDB_TITLE = "original_title";
        final String MDB_RELEASE = "release_date";
        final String MDB_VOTEAVE = "vote_average";
        final String MDB_PLOT = "overview";

        try {
            JSONObject movie = movieArray.getJSONObject(i);
             information[0] = movie.getString(MDB_TITLE);
             information[1] = getReadableDateString(movie.getString(MDB_RELEASE));
             information[2] = movie.getString(MDB_VOTEAVE) + "/10";
             information[3] = movie.getString(MDB_PLOT);

            return information;
        }
        catch (JSONException e){
            Log.e(LOG_TAG, "json exception during get movie title");
        }
        return information;
    }


    //TODO
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateMovies() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String query = preference.getString(getString(R.string.pref_query_key)
           ,getString(R.string.pref_query_default));
        FetchMoviesTask fetchMovies = new FetchMoviesTask();
        fetchMovies.execute(query);
    }

    //Return just the year of date we want to use, would it be beneficial to add new format to strings?
    private String getReadableDateString(String time){
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = shortenedDateFormat.parse(time);
            shortenedDateFormat.applyPattern("yyyy");
            return shortenedDateFormat.format(date);
        }
        catch (java.text.ParseException e){
            Log.e(LOG_TAG, "cannot parse date returned");
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }


    // Async task designed to handle request and create a json object that we can access easily.
    public class FetchMoviesTask extends AsyncTask<String, Void, String[]> {
        public final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;

            try {
                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";

                Uri buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(strings[0])
                        .appendQueryParameter(getString(R.string.api_key), getString(R.string.api_value))
                        .build();

                URL url = new URL(buildUri.toString());

                // Create the request to Movie DB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do;
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, e.toString());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ForecastFragment", "Error closing stream", e);
                    }
                }

                // will take new json object and create a array of poster urls so we may pass it to
                // custom array adapter. These will be appended to request and downloaded using picasso api
                // if the json isn't formatted correctly it will return an error and stack trace.
                try {
                    return getBackdropDataFromJson(movieJsonStr);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                return null;
            }
        }


        public String[] getBackdropDataFromJson(String movieJsonStr)
                throws JSONException {

            System.out.println(movieJsonStr);

            // These are the names objects that need to be extracted.
            final String MDB_RESULTS = "results";
            final String MDB_BACKDROP = "poster_path";

            JSONObject movieJson = new JSONObject(movieJsonStr);

            movieArray = movieJson.getJSONArray(MDB_RESULTS);
            String[] backdropArray = new String[movieArray.length()];

            System.out.println(movieArray.length());

            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);
                backdropArray[i] = movie.getString(MDB_BACKDROP);
                System.out.println(backdropArray[i]);
            }

            return backdropArray;
        }

        public void onPostExecute(String[] result) {

            if (result != null) {

                if(movieAdapter != null) {
                    movieAdapter.clear();
                }

                movieAdapter.addAll(result);
            }
        }

    }
}
