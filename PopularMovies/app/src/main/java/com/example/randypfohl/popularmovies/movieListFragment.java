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
import android.widget.ArrayAdapter;
import android.widget.GridView;

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
import java.util.Date;
import java.util.GregorianCalendar;

public class movieListFragment extends Fragment {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private JSONArray movieArray;
    public ArrayAdapter<String> movieAdapter;

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


        movieAdapter =
                new movieImageAdapter(
                        getActivity(),
                        R.layout.list_item_movie,
                        //R.id.list_item_movie_imageView,
                        new String[0]);

        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String forecast = movieAdapter.getItem(i);
                Log.e(LOG_TAG, forecast);
                // Intent openDetail = new Intent(getActivity(), DetailActivity.class)
                //        .putExtra(Intent.EXTRA_TEXT, forecast);
                // startActivity(openDetail);

            }
        });

        //  Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into((ImageView)rootView.findViewById(R.id.list_item_movie_imageView));
        return rootView;


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //// TODO: 9/2/16 Create Menu folder and menu options for popular, refresh, top rated
        //inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //if (id == R.id.action_refresh) {
        //    updateMovies();
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    public void updateMovies() {
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        //String location = preference.getString(getString(R.string.pref_location_key)
        //   ,getString(R.string.pref_location_default));
        FetchMoviesTask fetchMovies = new FetchMoviesTask();
        fetchMovies.execute("popular");
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }


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
                        .appendPath(getString(R.string.popularEndpoint))
                        .appendQueryParameter(getString(R.string.api_key), getString(R.string.apiKey))
                        .build();

                Log.d("build uri", buildUri.toString());

                URL url = new URL(buildUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
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
                //Log.e(LOG_TAG, "Forecast json string: " + movieJsonStr);
                System.out.println(movieJsonStr);

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

            // These are the names objects that need to be extracted.
            final String MDB_RESULTS = "results";
            final String MDB_BACKDROP = "backdrop_path";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            String[] backdropArray = new String[movieJson.length()];


            movieArray = movieJson.getJSONArray(MDB_RESULTS);
            System.out.println(movieArray.length());

            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);
                backdropArray[i] = movie.getString(MDB_BACKDROP);
            }

            return backdropArray;
        }

        public void onPostExecute(String[] result) {

            if (result != null) {
                movieAdapter.clear();
                movieAdapter.addAll(result);
                // New data is back from the server.  Hooray!
            }
        }

    }
}
